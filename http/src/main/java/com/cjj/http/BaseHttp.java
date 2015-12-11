package com.cjj.http;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cjj.listener.CallbackListener;
import com.cjj.util.GenericsUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by cjj on 2015/8/25.
 */
public class BaseHttp<T>{
    private static final String TAG = "CJJ-BaseHttp";
    private Gson mGson;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private static BaseHttp mBaseHttp;

    protected BaseHttp()
    {
        mGson = new Gson();
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
    }

    protected static BaseHttp getInstance()
    {
        if(mBaseHttp == null)
        {
            mBaseHttp = new BaseHttp();
        }
        return  mBaseHttp;
    }

    protected void baseGet(String url,CallbackListener<T> listener)
    {
        Request request = getBaseRequest(url);
        doRequest(request, listener);
    }

    protected OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    protected void basePost(String url, Map<String, String> params, CallbackListener<T> listener)
    {
        if (params == null) {
           baseGet(url,listener);return;
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .tag(url)
                .build();
        doRequest(request, listener);
    }

    protected void baseDownload(final String url, final String savePath, final CallbackListener<T> listener)
    {
        Request request = getBaseRequest(url);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (isListenerNotNull(listener)) listener.onError(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = response.body().byteStream();
                startDownload(url, savePath, is, listener);
            }
        });
    }

    protected void baseDisplayImage(T t,ImageView imageView,String imgUrl,int placeholderId,int errorId)
    {
        if(t instanceof Activity) {
            Glide.with((Activity) t).load(imgUrl).placeholder(placeholderId).error(errorId).into(imageView);
        }else if(t instanceof Fragment)
        {
            Glide.with((Fragment) t).load(imgUrl).placeholder(placeholderId).error(errorId).into(imageView);
        }else if(t instanceof Context)
        {
            Glide.with((Context) t).load(imgUrl).placeholder(placeholderId).error(errorId).into(imageView);
        }else
        {
            throw new RuntimeException("the t must be context or activity or fragment");
        }
    }

    private void startDownload(String urlPath,String savePath,InputStream is,CallbackListener<T> listener) {
        FileOutputStream fos = null;
        try {
            // 判断SD卡是否存在，并且是否具有读写权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获得存储卡的路径
//                String sdpath = Environment.getExternalStorageDirectory().toString();
                System.out.println("savePath-------->" + savePath);
                File file = new File(savePath,getFileNameByUrl(urlPath));
                // 判断文件目录是否存在
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                // 缓存
                byte buf[] = new byte[1024*2];
                int len = 0;
                int progress = 0;
                while ((len = is.read(buf)) != -1)
                {
                    progress += len;
                    if(isListenerNotNull(listener)) listener.onDownloadProgress(progress);
                    fos.write(buf, 0, len);
                }
                if(isListenerNotNull(listener)) listener.onDownloadFinish(file.getAbsolutePath());
                fos.flush();
            } else {
                Log.i(TAG, "no find sdcard or sdcard no permission");
            }
        } catch (MalformedURLException e) {
            if(isListenerNotNull(listener))listener.onError(e);
        } catch (IOException e) {
            if(isListenerNotNull(listener))listener.onError(e);
        }finally
        {
            try
            {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Url名称
     * @param strUrl
     * @return
     */
    public static String getFileNameByUrl(String strUrl) {
        if (!TextUtils.isEmpty(strUrl)) {
            try {
                return strUrl.substring(strUrl.lastIndexOf("/") + 1, strUrl.length());
            } catch (IndexOutOfBoundsException e) {
                return strUrl;
            }
        } else {
            return strUrl;
        }
    }

    protected Request getBaseRequest(String url)
    {
        Request request = new Request.Builder().url(url).tag(url).build();
        return  request;
    }

    protected void doRequest(Request request, final CallbackListener<T> listener)
    {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(e);
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String result = response.body().string();
                Log.i(TAG, "结果：" + result);
                if(isListenerNotNull(listener)) listener.onStringResult(result);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Class<T> clazz = GenericsUtils.getSuperClassGenricType(listener.getClass());
                            if (clazz == String.class) {        //字符串
                                if(isListenerNotNull(listener))
                                listener.onSuccess((T) result);
                            } else {//Object
                                if(isListenerNotNull(listener))
                                listener.onSuccess(mGson.fromJson(result, clazz));
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "出错", e);
                            if(isListenerNotNull(listener))
                            listener.onError(e);
                        }
                    }
                });
            }
        });
    }

    public Boolean isListenerNotNull(CallbackListener<T> listener)
    {
        if(listener!=null)
        {
            return true;
        }
        return false;
    }


}
