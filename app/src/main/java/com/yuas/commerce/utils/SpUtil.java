package com.yuas.commerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yuas.commerce.application.VsdApplication;


/**
 * Created by liqing on 18/8/13.
 */

public class SpUtil {

    private static final String SPARK_RA_URL = "spark_ra_url";
    public static final String APP_KEY = "appkey";
    public static final String APP_SECRET = "appsecret";
    public static final String TEMPLATE = "template";
    private static final String SP_NAME = "cn.unitid.shared.preference";
    private SharedPreferences sp;
    private static volatile SpUtil instance;

    private SpUtil() {
        sp = VsdApplication.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static final SpUtil getInstance() {
        if (instance == null) {
            synchronized (SpUtil.class) {
                if (instance == null) {
                    instance = new SpUtil();
                }
            }
        }
        return instance;
    }

    public void saveSparkRaUrl(String url){
        if (url != null){
            sp.edit().putString(SPARK_RA_URL,url).apply();
        }
    }

    public String getSparkRaUrl(){
        return sp.getString(SPARK_RA_URL,null);
    }

    public void saveKey(String appKey) {
        if (appKey != null) {
            sp.edit().putString(APP_KEY, appKey).apply();
        }
    }

    public String getAppKey(){
        return sp.getString(APP_KEY,null);
    }

    public void saveAppSecret(String appSecret){
        if (appSecret != null) {
            sp.edit().putString(APP_SECRET, appSecret).apply();
        }
    }

    public String getAppSecret(){
        return sp.getString(APP_SECRET,null);
    }

    public void saveTemplateId(String templateId){
        if (templateId != null){
            sp.edit().putString(TEMPLATE,templateId).apply();
        }
    }

    public String getTemplate(){
        return sp.getString(TEMPLATE,null);
    }
}