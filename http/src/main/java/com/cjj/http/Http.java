package com.cjj.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cjj.listener.CallbackListener;

import java.util.Map;

/**
 /**
 * Http
 * @author cjj
 * @category A frame for web
 * @version 1.0
 * @date 2015/8/26
 */
public class Http extends BaseHttp{
    /**
     * get 请求
     * @param url
     * @param listener
     */
    public static void get(String url,CallbackListener<?> listener)
    {
        getInstance().baseGet(url, listener);
    }

    /**
     * post 请求
     * @param url
     * @param params
     * @param listener
     */
    public static void post(String url,Map<String,String>params,CallbackListener<?> listener)
    {
        getInstance().basePost(url, params, listener);
    }

    /**
     * 无参post 请求
     * @param url
     * @param listener
     */
    public static void postNotParams(String url,CallbackListener<?> listener)
    {
        getInstance().basePost(url, null, listener);
    }

    /**
     * 下载
     * @param url 下载的url
     * @param savePath 保存的路径
     * @param listener 回调
     */
    public static void download(String url,String savePath,CallbackListener<?> listener)
    {
        getInstance().baseDownload(url, savePath, listener);
    }

    /**
     * 取消request
     */
    public static void cancel(String url)
    {
        getInstance().getOkHttpClient().cancel(url);
    }


}
