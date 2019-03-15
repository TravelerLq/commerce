package com.yuas.commerce.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yuas.commerce.R;
import com.yuas.commerce.activity.AuthorityConfigActivity;
import com.yuas.commerce.activity.ModifyEnterpriseInfoActivity;
import com.yuas.commerce.bean.UserBean;
import com.yuas.commerce.data.UserData;
import com.yuas.commerce.observer.DialogObserverHolder;
import com.yuas.commerce.utils.Loger;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 * <p>
 * 商会管理的主页fragment
 */

public class HomeCommerceFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {

    private RelativeLayout rlReim;

    private RelativeLayout rlContract;
    private RelativeLayout rlInvoicing;
    private RelativeLayout rlBudget;
    private String roleType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_commerce, container, false);
        UserBean userBean = UserData.getUserInfo();
        if (userBean != null) {
            roleType = userBean.getType();
            Loger.e("---");
//            if (type.equals("1") ) {
//                //boss
//
//            } else {
//                //员工
//                setContentView(R.layout.activity_main_employee);
//            }
        }
        rlReim = (RelativeLayout) view.findViewById(R.id.rl_reim);
        rlContract = (RelativeLayout) view.findViewById(R.id.rl_contract);
        rlInvoicing = (RelativeLayout) view.findViewById(R.id.rl_invoicing);
        rlBudget = (RelativeLayout) view.findViewById(R.id.rl_budget);
        initEvent();
        return view;
    }

    private void initEvent() {
        rlReim.setOnClickListener(this);
        rlContract.setOnClickListener(this);
        rlInvoicing.setOnClickListener(this);
        rlBudget.setOnClickListener(this);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_reim:
                toActivity(HomeCommerceFragment.this, AuthorityConfigActivity.class);
                // toActivity(HomeFragment.this, ReimAddActivity.class);

                break;
            case R.id.rl_contract:
                toActivity(HomeCommerceFragment.this, AuthorityConfigActivity.class);

                break;
            case R.id.rl_invoicing:
                //远程开票：
                //  toActivity(HomeFragment.this, RemotelyBillingRecycleActivity.class);
                toActivity(HomeCommerceFragment.this,ModifyEnterpriseInfoActivity.class);
               // SimpleToast.toastMessage("系统建设中...", Toast.LENGTH_SHORT);
                break;
            case R.id.rl_budget:
               // toActivity(HomeCommerceFragment.this, LoginCommerceActivity.class);

//                //   toActivity(HomeFragment.this,ReimAddTypeActivity.class);
//                if (roleType.equals("1")) {
//                    toActivity(HomeCommerceFragment.this, BudgetChooseActivity.class);
//                } else {
//                    SimpleToast.toastMessage("对不起，您暂无权限查看", Toast.LENGTH_SHORT);
//                }

                break;
            default:
                break;
        }

    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void removeDisposable(Disposable disposable) {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return null;
    }
}
