package com.yuas.commerce.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.yuas.commerce.R;
import com.yuas.commerce.application.VsdApplication;
import com.yuas.commerce.bean.BaseBean;
import com.yuas.commerce.bean.EnterpriseInfoBean;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.fragment.RxStyleDialogFragment;
import com.yuas.commerce.observer.DialogObserverHolder;
import com.yuas.commerce.utils.Loger;


import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Victor on 10/31/2017.
 * activity的基类
 */

public abstract class BaseActivity extends RxFragmentActivity implements DialogObserverHolder, View.OnClickListener {

    /**
     * Disposable类:
     *
     * dispose():主动解除订阅
     * isDisposed():查询是否解除订阅 true 代表 已经解除订阅
     * CompositeDisposable 可以快速解除所有添加的Disposable类 每当我们得到一个Disposable
     * 时就调用CompositeDisposable.add()将它添加到容器中,
     * 在退出的时候, 调用CompositeDisposable.clear() 即可快速解除.
     */
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Subscription> compositeSubscription = new ArrayList<>();
    protected Unbinder unBinder;

    // Request code for user select video file.
    protected static final int REQUEST_CODE_SELECT_VIDEO_FILE = 101;
    // Request code for require android READ_EXTERNAL_PERMISSION.
    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 102;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求OtherModifyInfo
    private static final int REQUEST_CODE_OTHER_MODIFY_INFO = 113;


    RxStyleDialogFragment rxStyleDialogFragment;


    protected final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VsdApplication.getInstance().addActivity(this);
//        //apply for cert init
//        SparkApplicationContext.init(VsdApplication.getInstance());
        synchronized (mActivities) {
            mActivities.add(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(BaseActivity.this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unBinder = ButterKnife.bind(this);

    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            return;
        }
        compositeSubscription.add(subscription);
    }

    @Override
    public void removeDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            return;
        }
        compositeDisposable.remove(disposable);
    }

    @Override
    protected void onDestroy() {
        VsdApplication.getInstance().removeActivity(this);
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
        clearWorkOnDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    protected void exit() {
            for (int i = 0; i < mActivities.size(); i++) {
                mActivities.get(i).finish();
            }
    }

    /**
     * onDestroy时调用此方法
     * 切断此Activity中的观察者容器中包含的观察者
     */
    private void clearWorkOnDestroy() {
        //disposable clear
        compositeDisposable.clear();
        //subscription clear
        for (Subscription subscription : compositeSubscription) {
            if (subscription != null)
                subscription.cancel();
        }
        compositeSubscription.clear();
    }

    public void addFragmentNotToStack(int layoutId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment);
        fragmentTransaction.commit();
    }


    public void addFragmentByTagNotToStack(int layoutId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                Loger.e("---baseActivity--btnback-");
                finish();
                break;
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivity(Class<T> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    protected <T> void toActivityWithType(Class<T> clz, String key, String value, String key2, String value2) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(key, value);
        intent.putExtra(key2, value2);
        startActivity(intent);

    }

    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivityWithType(Class<T> clz, String intentTye) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(AppConstant.KEY_TYPE, intentTye);
        startActivity(intent);

    }

    protected <T> void toActivityWithTwoType(Class<T> clz, String intentTye, String type) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(AppConstant.KEY_TYPE, intentTye);
        intent.putExtra(AppConstant.KEY_TYPE_ANALYZE, type);
        startActivity(intent);

    }

    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivityWithParams(Class<T> clz, String key, String value) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(key, value);
        startActivity(intent);

    }



    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivityWithListParams(Class<T> clz, String key, List<String> value) {
        Intent intent = new Intent(this, clz);
        intent.putStringArrayListExtra(key, (ArrayList<String>) value);
        startActivity(intent);

    }



    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivityWithType(Class<T> clz, String intentTye, int requestCode, boolean needCallBack) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(AppConstant.KEY_TYPE, intentTye);
        if (needCallBack) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }


    }


    // 　底部Dialog


    //    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到扫描的类
//     * @param <T>
//     */
    protected <T> void toOtherInfoActivityWithParams(Class<T> clz, EnterpriseInfoBean enterpriseInfoBean, int requestCode, boolean needCallBack) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, enterpriseInfoBean);
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        if (needCallBack) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }
//
//    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到扫描的类
//     * @param <T>
//     */
//    protected <T> void toScanActivityWithParams(Class<T> clz,ScanTypeBean scanTypeBean,int requestCode,boolean needCallBack) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,scanTypeBean);
//        Intent intent = new Intent(this, clz);
//        intent.putExtras(bundle);
//        if(needCallBack){
//            startActivityForResult(intent,requestCode);
//        }else {
//            startActivity(intent);
//        }
//    }
//
//
//    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到扫描结果的类
//     * @param <T>
//     */
//    protected <T> void toScanResultActivityWithParams(Class<T> clz,ScanInfoResultBean scanTypeBean) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,scanTypeBean);
//        bundle.putString("deliverNo",VsdApplication.getInstance().getTokenBean().getAccessToken());
//        Intent intent = new Intent(this, clz);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

    protected <T> void toAtivityWithParams(Class<T> clz, BaseBean baseBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, baseBean);
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected <T> void toAtivityWithParamsAndBean(Class<T> clz, BaseBean baseBean, String type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, baseBean);
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        intent.putExtra(AppConstant.KEY_TYPE, type);
        startActivity(intent);
    }

    /**
     * view 初始化及事件控制
     */
    protected abstract void initViewEvent();




    @Override
    protected void onPause() {
        super.onPause();
       // MobclickAgent.onPause(BaseActivity.this);

    }


    protected void showProgress() {


        try {
            rxStyleDialogFragment = new RxStyleDialogFragment();
            rxStyleDialogFragment.setCancelable(false);
            rxStyleDialogFragment.show(this.getSupportFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void dimissProgress() {
        if (rxStyleDialogFragment != null && rxStyleDialogFragment.getDialog() != null && rxStyleDialogFragment.getDialog().isShowing()) {
            try {
                rxStyleDialogFragment.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            Loger.e("--dissmissDialog fail--");
        }
        rxStyleDialogFragment = null;
    }

    //glide加载图片

    protected void loadImage(Context mContext, ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
        Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);

    }

    //系统播放器 播放视频

    /**
     * 使用系统自带播放器播放视频
     *
     * @param url
     */
    protected void openSystemVideoPlayer(String url, final Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(url);
            intent.setDataAndType(uri, "video/*");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

            if (e.toString().contains("ActivityNotFoundException")) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("温馨提醒")
                        .setMessage("该手机没有播放视频的应用，请安装视频播放软件后再试")
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                gotoAppDetailSettingIntent(context);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialogBuilder.create().show();
            }
            e.printStackTrace();
        }
    }

    protected static void gotoAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            Log.e("LoginActivity", ".SDK_INT >= 9 ");
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));

        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
        //launchApp(context, localIntent);
    }

    //选择视频

    //选择本地视频
    protected void selectVedioFromLocal(Context context) {
        // Check whether user has granted read external storage permission to this activity.
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        // If not grant then require read external storage permission.
        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            String requirePermission[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) context, requirePermission, REQUEST_CODE_READ_EXTERNAL_PERMISSION);
        } else {
            selectVideoFile();
        }
    }

    private void selectVideoFile() {

        // Create an intent with action ACTION_GET_CONTENT.

//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }

        Intent selectVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);

        // Show video in the content browser.
        // Set selectVideoIntent.setType("*/*") to select all data
        // Intent for this action must set content type, otherwise you will encounter below exception : android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.GET_CONTENT }
        selectVideoIntent.setType("video/*");

        // Start android get content activity ( this is a android os built-in activity.) .
        startActivityForResult(selectVideoIntent, REQUEST_CODE_SELECT_VIDEO_FILE);
    }



    protected void setVideoThumbnail(File file, ImageView imgVedioThumbnail ) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(),
                MediaStore.Video.Thumbnails.MINI_KIND);
        //获取图片后，压缩成指定大小
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 200);

        } else {
            //加载一个默认图片
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_default);
        }
        imgVedioThumbnail.setImageBitmap(bitmap);

    }

    /**
     * 提示对话框
     *
     * @param message
     */
    protected void warningDialog(String message) {
        new AlertDialog.Builder(BaseActivity.this)
                .setTitle(getResources().getString(R.string.notice))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();

    }

}
