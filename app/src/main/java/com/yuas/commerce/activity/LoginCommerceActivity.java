package com.yuas.commerce.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.yuas.commerce.R;
import com.yuas.commerce.bean.BussinessVOBean;
import com.yuas.commerce.bean.LoginCommerceResponseBean;
import com.yuas.commerce.constant.MySpEdit;
import com.yuas.commerce.network.control.LoginApiControl;
import com.yuas.commerce.observer.CommonDialogObserver;
import com.yuas.commerce.observer.RxHelper;
import com.yuas.commerce.view.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 登录(商会通的登录)
 * Created by Victor on 10/31/2017.
 */

public class LoginCommerceActivity extends BaseActivity {


    private MySpEdit prefs;

    private Button btnSure;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.edt_acount)
    EditText edtAccount;
    @BindView(R.id.edt_psw)
    EditText edtPsw;

    @BindView(R.id.tv_commerce)
    TextView tvCommerce;


    private Context context;
    private List<BussinessVOBean> listCommers;


    String[] itemsCommerce;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        checkAuthority();
        listCommers = new ArrayList<>();

        tvRegister.setVisibility(View.GONE);
        context = LoginCommerceActivity.this;
        btnSure = (Button) findViewById(R.id.btn_sure);
        prefs = MySpEdit.getInstance();
        String numPhone = prefs.getUser();
        String psw = prefs.getPsw();

        Log.e("loginActivtiy-", "onCreate: ");
        if (!TextUtils.isEmpty(numPhone)) {
            edtAccount.setText(numPhone);
        }
        if (!TextUtils.isEmpty(psw)) {
            edtPsw.setText(psw);
        }

        initViewEvent();

        getCommerces();

        // initTest();

    }

    private void initTest() {
        BussinessVOBean b = new BussinessVOBean();
        b.setId("1");
        b.setCommerceName("南京滁州商会");
        listCommers.add(b);
        for (int i = 0; i < listCommers.size(); i++) {
            itemsCommerce[i] = listCommers.get(i).getCommerceName();
        }
        showSingleChoiceDialog();

    }

    @Override
    protected void initViewEvent() {
//        tvRegister.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        tvCommerce.setOnClickListener(this);
    }


    private void checkAuthority() {


        if (PermissionsUtil.hasPermission(LoginCommerceActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {


        } else {
            PermissionsUtil.requestPermission(LoginCommerceActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permission) {
                            Log.e("--", "permissionGranted: 用户授予了访问外部存储的权限");


                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permission) {
                            Log.e("--", "permissionDenied: 用户拒绝了访问外部存储的申请");
                            // needPermissionTips();

                        }
                    }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    /**
     * 用来测试RxJava的进度框的
     */

    private void login(final String userName, final String pwd, final String commerceId) {


        Observable<LoginCommerceResponseBean> observable = new LoginApiControl().loginCommerce(userName, pwd, commerceId);

        CommonDialogObserver<LoginCommerceResponseBean> observer = new CommonDialogObserver<LoginCommerceResponseBean>(this) {

            @Override
            public void onNext(LoginCommerceResponseBean responseBean) {
                super.onNext(responseBean);
//                Loger.i("===login====" + new Gson().toJson(responseBean));
                toActivity(CommerceHomeActivity.class);
                finish();


            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(t.getMessage(), Toast.LENGTH_SHORT);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, LoginCommerceActivity.this);
    }


    //获取所有商会

    private void getCommerces() {


        Observable<List<BussinessVOBean>> observable = new LoginApiControl().getCommerces();

        CommonDialogObserver<List<BussinessVOBean>> observer = new CommonDialogObserver<List<BussinessVOBean>>(this) {

            @Override
            public void onNext(List<BussinessVOBean> list) {
                super.onNext(list);


                listCommers.clear();
                listCommers.addAll(list);

                showSingleChoiceDialog();

//                if (companyId.equals("-1") && dpt.equals("-1") && role.equals("-1")) {
//                    //被误删除后，需要重新加入公司
//                    toActivityWithType(FillCodeActivity.class, "login");
//                    finish();
//                    return;
//                }


            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(t.getMessage(), Toast.LENGTH_SHORT);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, LoginCommerceActivity.this);
    }


    //singleChoice
    private void showSingleChoiceDialog() {
        itemsCommerce = new String[listCommers.size()];
        for (int i = 0; i < listCommers.size(); i++) {

            itemsCommerce[i] = listCommers.get(i).getCommerceName();
        }

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请先选择所在商会").setIcon(R.drawable.ic_image)
                .setSingleChoiceItems(itemsCommerce, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // SimpleToast.toastMessage("选择" + itemsCommerce[which] + "成功", Toast.LENGTH_SHORT);
                        MySpEdit.getInstance().setCommerceId(listCommers.get(which).getId());
                        //   toActivityWithParams(AuthorityConfigActivity.class, "id", listCommers.get(which).getId());
                        tvCommerce.setText(itemsCommerce[which]);
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();

    }


    @Override
    public void onClick(View view) {
        //  super.onClick(view);
        switch (view.getId()) {

            case R.id.btn_sure:
                //  toActivity(AuthorityConfigActivity.class);

                //  toActivity(RecycleGridLayoutActivity.class);
                checkData();


                // toActivityWithParams(AuthorityConfigActivity.class, "id", "1");
                break;

            case R.id.tv_commerce:
                getCommerces();
                break;

            default:
                break;

        }


    }


    private void checkData() {
        String accountStr = edtAccount.getText().toString().trim();
        String pswStr = edtPsw.getText().toString().trim();
        String commerce = tvCommerce.getText().toString().trim();
        if (TextUtils.isEmpty(accountStr)) {
            SimpleToast.toastMessage("帐号不可为空！", Toast.LENGTH_SHORT);
            edtAccount.requestFocus();
            return;

        }

        if (TextUtils.isEmpty(pswStr)) {
            SimpleToast.toastMessage("密码不可为空！", Toast.LENGTH_SHORT);
            edtPsw.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(commerce) || commerce.equals("请选择商会")) {
            SimpleToast.toastMessage("请选择商会！", Toast.LENGTH_SHORT);
            return;
        }
        String commerceId = MySpEdit.getInstance().getCommerceId();
        //login_  type因为需求ge
        login(accountStr, pswStr, commerceId);


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        // VsdApplication.getRefWatcher(this).watch(this);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }


}
