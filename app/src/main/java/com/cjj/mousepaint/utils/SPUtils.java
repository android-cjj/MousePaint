package com.cjj.mousepaint.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cjj.mousepaint.MosuePaintAppication;
import com.google.gson.Gson;

import java.lang.reflect.Type;


public class SPUtils {
    private static final Context context = MosuePaintAppication.appication;
    private static final String APP_ID = "fuck_cjj";   //相当于文件名
    private static final Gson gson = new Gson();   //获取Gson

     SPUtils()
    {
        throw new UnsupportedOperationException("禁止实例化该类！");
    }


	/**
	 * 缓存信息
	 * @param cacheName	存入名字
	 * @param obj	对象
	 */
	public static void saveObject(String cacheName,Object obj){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        String json =  gson.toJson(obj);
		e.putString(cacheName, json);
		e.commit();
	}

    /**
     * 读取String缓存信息
     * @param <T>
     * @param cacheName	缓存名
     * @param classes	对象类型
     */
    public static String getObject(String cacheName){
        return getObject(cacheName,String.class,null);
    }

	/**
	 * 读取缓存信息
	 * @param <T>
	 * @param cacheName	缓存名
	 * @param classes	对象类型
	 */
	public static <T> T getObject(String cacheName,Class<T> classes){
		return getObject(cacheName,classes,null);
	}


    /**
     * 读取缓存信息
     * @param <T>
     * @param cacheName	缓存名
     * @param classes	对象类型
     * @param defaultValue	默认参数
     */
    public static <T> T getObject(String cacheName,Class<T> classes,T defaultValue){
        SharedPreferences s = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        String jsonString = s.getString(cacheName, "");
        if(!"".equals(jsonString)){
            try {
                T o = (T)gson.fromJson(jsonString, classes);
                return o;
            }catch (Exception e){
                e.printStackTrace();
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 读取缓存信息
     * 读取List 等列表type要这么写 new TypeToken<ArrayList<GoodsCategory>>(){}.getType()
     * @param <T>
     * @param cacheName	缓存名
     * @param type	对象类型
     */
    public static <T> T getObject(String cacheName,Type type){
        return getObject(cacheName,type,null);
    }

    /**
     * 读取缓存信息
     * @param <T>
     * @param cacheName	缓存名
     * @param type	对象类型
     * @param defaultValue	默认参数
     */
    public static <T> T getObject(String cacheName,Type type,T defaultValue){
        SharedPreferences s = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        String jsonString = s.getString(cacheName, "");
        if(!"".equals(jsonString)){
            try {
                T o = (T)gson.fromJson(jsonString, type);
                return o;
            }catch (Exception e){
                e.printStackTrace();
                return defaultValue;
            }
        }
        return defaultValue;
    }


	
	/**
	 * 删除信息
	 * @param cacheName 缓存名
	 */
	public static void delObject(String cacheName){
		SharedPreferences s = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
		s.edit().remove(cacheName).commit();
	}

    /**
     * 清除所有数据
     */
    public static void clearAll()
    {
        SharedPreferences sp = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear().commit();
    }

}
