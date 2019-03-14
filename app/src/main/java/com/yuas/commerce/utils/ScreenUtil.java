package com.yuas.commerce.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.yuas.commerce.application.VsdApplication;


/**
 * Created by liqing on 18/5/10.
 */

public class ScreenUtil {

    private int displayWidth;
    private int displayHeight;
    private Context mContext;

    private ScreenUtil() {
        DisplayMetrics metric = new DisplayMetrics();//获取系统屏幕信息
        WindowManager windowManager = (WindowManager) VsdApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        this.displayWidth = metric.widthPixels;     // 屏幕宽度（像素）
        this.displayHeight = metric.heightPixels;   // 屏幕高度（像素）
    }

    private static class ScreenHolder {
        private final static ScreenUtil screenUtil = new ScreenUtil();
    }

    public static ScreenUtil getInstance() {
        return ScreenHolder.screenUtil;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    /**
     * 获取登录设备mac地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }
    public static float getDensity(Context context){
        return context.getResources ().getDisplayMetrics ().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = getDensity(VsdApplication.getInstance ());
        return (int) (dpValue * scale + 0.5f);
    }



    public static int dp2px(Context context, int dp ){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
    public static int sp2px(Context context, int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }
    public static int px2dp(Context context, float px ){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(px/scale+0.5f);
    }

    /**
     * @return 返回屏幕的宽度
     */
    public int getScreenW() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

}
