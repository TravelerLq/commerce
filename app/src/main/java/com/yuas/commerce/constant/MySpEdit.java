package com.yuas.commerce.constant;

import android.app.Activity;
import android.content.SharedPreferences;

import com.yuas.commerce.application.VsdApplication;


/**
 * Created by Yso on 2017/11/13.
 */

public class MySpEdit {
    private String name = "config";
    private static MySpEdit mySpEdit;

    private MySpEdit() {

    }

    public static MySpEdit getInstance() {
        if (mySpEdit == null) {
            mySpEdit = new MySpEdit();
        }
        return mySpEdit;
    }

    public boolean getAppEmv() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getBoolean("emv", false);
    }

    public void setAppEmv(boolean result) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putBoolean("emv", result).apply();

    }

    public void setUser(String userName) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("USER", userName).apply();
    }


    public String getUser() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("USER", "");
    }


    public void setRealName(String realName) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("REAL_NAME", realName).apply();
    }


    public String getUserId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("USER_ID", "");
    }


    public void setUserId(String userId) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("USER_ID", userId).apply();
    }

    public String getRealName() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("REAL_NAME", "");
    }

    public void setIDNo(String idNo) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("ID_NO", idNo).apply();
    }


    public String getIDNo() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("ID_NO", "");
    }

    public void setPhone(String phone) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("PNONE_NO", phone).apply();
    }


    public String getPhone() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("PNONE_NO", "");
    }

//last version

//last version

    public void setLastVersion(String version) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("VERSION", version).apply();
    }


    public String getLastVersion() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("VERSION", "");
    }


    //pin Code

    public void setPinCode(String userName) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("PIN", userName).apply();
    }


    public String getPinCode() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("PIN", "");
    }

    public void setRole(String role) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("ROLE", role).apply();
    }


    public String getRole() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("ROLE", "");
    }


    public void setPsw(String psw) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("PSW", psw).apply();
    }


    public String getPsw() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("PSW", "");
    }


    public void setCompanyId(String companyId) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("COMP_ID", companyId).apply();
    }


    public String getCompanyId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("COMP_ID", "");
    }

    public void setDepartmentId(String companyId) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("DEPART_ID", companyId).apply();
    }
//comerceId

    public String getCommerceId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("COMMCE_ID", "");
    }

    public void setCommerceId(String companyId) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("COMMCE_ID", companyId).apply();
    }


    public String getDepartmentId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("DEPART_ID", "");
    }

    public void setFormId(int id) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putInt("FORM_ID", id).apply();
    }


    public int getFormId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getInt("FORM_ID", -1);
    }


    public void setAuthor(String author) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("AUTHOR", author).apply();
    }

    public String getAuthor() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("AUTHOR", "");

    }

    //
   // commerce about


    public void setCommerceAuthor(String author) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("AUTHOR_", author).apply();
    }

    public String getCommerceAuthor() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("AUTHOR_", "");

    }

    public void setCommerceCompanyId(String id) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("COMPANY_ID", id).apply();
    }

    public String getCommerceCompanyId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("COMPANY_ID", "");

    }

    public void setApkUrl(String apkUrl) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("APK_URL", apkUrl).apply();
    }

    public String getApkUrl() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("APK_URL", "");
    }


}
