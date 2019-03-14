package com.yuas.commerce.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


import com.squareup.leakcanary.RefWatcher;
import com.yuas.commerce.bean.LoginResponseBean;
import com.yuas.commerce.utils.Loger;

import java.util.ArrayList;
import java.util.List;



//import com.anupcowkur.reservoir.Reservoir;


/**
 * Created by Victor on 10/31/2017.
 */

public class VsdApplication extends Application {
    private static VsdApplication globalVar = null;
    private LoginResponseBean loginResponseBean;

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    private static List<Activity> activityStack = new ArrayList<>();
    //待存物料号集合
    private List<String> deliveryList = new ArrayList<>();
    private RefWatcher refWatcher;


    public static RefWatcher getRefWatcher(Context context) {
        VsdApplication application = (VsdApplication) context.getApplicationContext();
        return application.refWatcher;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        globalVar = this;
        //出错保存机制
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());
//        refWatcher = RefWatcher.DISABLED;
//        refWatcher = MySpEdit.getInstance().getAppEmv()
//                ? RefWatcher.DISABLED
//                : LeakCanary.install(VsdApplication.this);

       // checkAuthority();


    }

    /**
     * 用户id，spark-cm-sdk 需要。这里为了演示，直接写死
     * @return
     */
    public static final String getUserId(){
        return "user123";
    }


    public static VsdApplication getInstance() {
        return globalVar;
    }

    private void initReservoir() {
        try {
         //   Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (Exception e) {
            //failure
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return getInstance().getBaseContext();
    }

    public static VsdApplication getGlobalVar() {
        return globalVar;
    }

    public static void setGlobalVar(VsdApplication globalVar) {
        VsdApplication.globalVar = globalVar;
    }



    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void removeAllActivity() {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity != null)
                    activity.finish();
                activity = null;
            }
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 降序销毁Activity
     */
    public void removeActivityOrderByDes() {
        if (activityStack != null) {
            for (int i = 0; i < activityStack.size(); i++) {
                Activity activity = activityStack.get(i);
                Loger.i("=======" + activity.getClass());
                if (activity != null)
                    activity.finish();
                activity = null;
            }
        }
    }


    public LoginResponseBean getLoginResponseBean() {
        return loginResponseBean;
    }

    public void setLoginResponseBean(LoginResponseBean loginResponseBean) {
        this.loginResponseBean = loginResponseBean;
    }
    public void exit() {
        removeAllActivity();
        System.exit(0);
    }

    public void restartApp(final Context context) {
        try {
            // closeInterface();
            /** 启动应用 */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent launch = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                    launch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(launch);
                    System.exit(0);
                }
            }, 100);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<Activity>();
        }
        activityStack.add(activity);
    }


    public List<String> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<String> deliveryList) {
        this.deliveryList = deliveryList;
    }

//    public List<StoreMaterialOrderResponseBean> getStoreMaterialOrderBeanList() {
//        return storeMaterialOrderBeanList;
//    }
//
//    public void setStoreMaterialOrderBeanList(List<StoreMaterialOrderResponseBean> storeMaterialOrderBeanList) {
//        this.storeMaterialOrderBeanList = storeMaterialOrderBeanList;
//    }
//
//    public List<StoreMaterialOrderDteailResposeBean> getStoreMaterialList() {
//        return storeMaterialList;
//    }
//
//    public void setStoreMaterialList(List<StoreMaterialOrderDteailResposeBean> storeMaterialList) {
//        this.storeMaterialList = storeMaterialList;
//    }
//

    /**
     * 清除delivery集合
     */
    public void clearDeleveryArray() {
        deliveryList.clear();
    }

//    public List<ProuductScreenResponse> getSendMaterialScreenList() {
//        return sendMaterialScreenList;
//    }
//
//    public void setSendMaterialScreenList(List<ProuductScreenResponse> sendMaterialScreenList) {
//        this.sendMaterialScreenList = sendMaterialScreenList;
//    }
//
//    public List<ProuductScreenResponse> getProuductLineTypeList() {
//        return prouductLineTypeList;
//    }
//
//    public void setProuductLineTypeList(List<ProuductScreenResponse> prouductLineTypeList) {
//        this.prouductLineTypeList = prouductLineTypeList;
//    }
//
//    public SendMaterialMergeMaterialResponseBean getMergeMaterialResponseBean() {
//        return mergeMaterialResponseBean;
//    }
//
//    public void setMergeMaterialResponseBean(SendMaterialMergeMaterialResponseBean mergeMaterialResponseBean) {
//        this.mergeMaterialResponseBean = mergeMaterialResponseBean;
//    }
//
//    public List<String> getCarList() {
//        return carList;
//    }
//
//    /**
//     * 小车类别集合
//     *
//     * @param carList
//     */
//    public void setCarList(List<String> carList) {
//        this.carList = carList;
//    }
//
//    public SendMaterialMergeMaterialRequestBean getMergeMaterialRequestBean() {
//        return mergeMaterialRequestBean;
//    }
//
//    public void setMergeMaterialRequestBean(SendMaterialMergeMaterialRequestBean mergeMaterialRequestBean) {
//        this.mergeMaterialRequestBean = mergeMaterialRequestBean;
//    }
//
//    /**
//     * 发料单筛选条件
//     */
//    public void setSendMaterialQueryRequestBean(SendMaterialQueryRequestBean queryRequestBean) {
//        this.queryRequestBean = queryRequestBean;
//    }
//
//    public SendMaterialQueryRequestBean getSendMaterialQueryRequestBean() {
//        return queryRequestBean;
//    }
}
