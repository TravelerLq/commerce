package com.yuas.commerce.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuas.commerce.R;
import com.yuas.commerce.bean.UserBean;
import com.yuas.commerce.bean.UserInfoBean;
import com.yuas.commerce.data.UserData;
import com.yuas.commerce.network.control.UserInfoApiControl;
import com.yuas.commerce.observer.CommonDialogObserver;
import com.yuas.commerce.observer.RxHelper;
import com.yuas.commerce.utils.Loger;


import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;

;

/**
 * Created by liqing on 18/7/3.
 */

public class UserInfoActivity extends BaseActivity {



    // weinxi ;implements IWXAPIEventHandler IWXAPIEventHandler
    private ArrayList<String> selectedPhotos;

    @BindView(R.id.project_master_head)
    ImageView headerImage;

    @BindView(R.id.ll_help)
    LinearLayout llHelp;

    @BindView(R.id.ll_feedback)
    LinearLayout llFeedBack;

    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @BindView(R.id.ll_cert_info)
    LinearLayout llCerInfo;

//    @BindView(R.id.ll_exit)
//    LinearLayout llExit;

    //退出登录
    @BindView(R.id.tv_sure)
    TextView tvLoginOut;
    //    LinearLayout llExit;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;

    @BindView(R.id.tv_depart)
    TextView tvDepartment;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    @BindView(R.id.button_back)
    ImageButton backBtn;

    //关联小程序

    @BindView(R.id.ll_mini_program)
    LinearLayout llMiniProgrm;

    private String bigUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_new);
        selectedPhotos = new ArrayList<>();
        UserBean userBean = UserData.getUserInfo();
        if (userBean != null) {
            tvRealName.setText(userBean.getRealName());
            tvDepartment.setText(userBean.getDepart());
            tvTel.setText(userBean.getPhone());

        }

        initViewEvent();

    }


    //获取feedbackInfo
    private void getData() {

        Observable<UserInfoBean> obervable = new UserInfoApiControl().getUserInfo();
        Observer<UserInfoBean> observer = new CommonDialogObserver<UserInfoBean>(UserInfoActivity.this) {
            @Override
            public void onNext(UserInfoBean userInfoBean) {
                super.onNext(userInfoBean);
                if (userInfoBean != null && userInfoBean.getHeadImgThumb() != null) {

                    Glide.with(UserInfoActivity.this)
                            .load(userInfoBean.getHeadImgThumb())

                            .into(headerImage);
                    bigUrl = userInfoBean.getHeadImg();
                    Loger.e("--userInfoBean.getHeadImgThumb()-" + userInfoBean.getHeadImgThumb());
                }
            }
        };
        RxHelper.bindOnUI(obervable, observer);
    }
/*
也需要从网络加载各种尺寸
If the thumbnail needs to load the same full-resolution image over the network,
 it might not be faster at all.
 */


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.project_master_head:
                toActivityWithParams(ViewBigImageActivity.class, "url", bigUrl);

                break;
//            case R.id.ll_help:
//                toActivity(HelpActivtiy.class);
//                break;
//
//            case R.id.ll_feedback:
//                toActivity(UsersFeedBackActivity.class);
//                break;
//            case R.id.ll_version:
//                toActivity(VersionInfoActivity.class);
//                break;
//            case R.id.ll_about:
//                break;
//            case R.id.ll_cert_info:
//                toActivity(CertDetailActivity.class);
//                break;
            case R.id.tv_sure:
                //退出
                warningDialog("确认退出登录？");
                break;

            //跳转到小程序
            case R.id.ll_mini_program:
                //goMiniProgram();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }


    //跳转到小程序
//    private void goMiniProgram() {
//
//        String appId = "wx76b1e1fa9c0b3bd6"; // 填应用AppId: wx76b1e1fa9c0b3bd6
//        IWXAPI api = WXAPIFactory.createWXAPI(UserInfoActivity.this, appId);
//
//        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//        req.userName = "wx2b0f005b62d7b3d5"; // 填小程序原始id
//        // req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
//        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
//        api.sendReq(req);
//
//    }




    @Override
    protected void initViewEvent() {
        headerImage.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llVersion.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llFeedBack.setOnClickListener(this);
        llCerInfo.setOnClickListener(this);
//        llExit.setOnClickListener(this);
        tvLoginOut.setOnClickListener(this);

        backBtn.setOnClickListener(this);

        //关联小程序

        llMiniProgrm.setOnClickListener(this);

    }

    @Override
    protected void warningDialog(String message) {
        new AlertDialog.Builder(UserInfoActivity.this)
                .setTitle(getResources().getString(R.string.notice))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginOut();
                        exit();
                        // toActivity(LoginActivity.class);

                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }

    private void loginOut() {
        Observable<String> observable = new LoginApiControl().loginOut();
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.e("--s=" + s);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, UserInfoActivity.this);
    }

    /*
     RecycleBin机制 缓存机制
     所有继承AbListview子类（listview和 gridview）都是这种机制
     */
    int mFirstActivePosition;
    View[] mActiveViews;

    public View getActiveViews(int pos) {
        int index = pos - mFirstActivePosition;

        final View[] activeViews = mActiveViews;
        if (index >= 0 && index < activeViews.length) {
            final View match = activeViews[index];
            return match;
        }

        return null;
    }


    //剪裁图片


    //关于唤醒微信小程序

//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
//            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
//            String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
//        }
//    }

//    View getActiveView(int position) {
//        int index = position - mFirstActivePosition;
//        final View[] activeViews = mActiveViews;
//        if (index >= 0 && index < activeViews.length) {
//            final View match = activeViews[index];
//            activeViews[index] = null;
//            return match;
//        }
//        return null;
//    }


}
