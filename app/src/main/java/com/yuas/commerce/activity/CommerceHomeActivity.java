package com.yuas.commerce.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yuas.commerce.R;
import com.yuas.commerce.constant.MySpEdit;
import com.yuas.commerce.fragment.HomeCommerceFragment;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.utils.Utils;
import com.yuas.commerce.view.BottomNavigationItem;
import com.yuas.commerce.view.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommerceHomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.textview_title)
    TextView tvTitle;


    private String lastVersion;
    private String apkUrl;
    private MySpEdit prefs;
    private Context mContext;

    private List<BottomNavigationItem> data = new ArrayList<>();


    private HomeCommerceFragment homeCommerceFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commerce_home);
        ButterKnife.bind(this);
        mContext = CommerceHomeActivity.this;

        //消息
        //  msgService();

        tvTitle.setText(getResources().getString(R.string.home_title));
        lastVersion = Utils.getVersionName();
        Loger.e("--lastVersionName==" + lastVersion);

        buttonBack.setVisibility(View.GONE);
        tvHeader.setVisibility(View.GONE);
        //更新检测
        //testDown();
        // checkUpdateApp();

        homeCommerceFragment = new HomeCommerceFragment();
        addFragmentNotToStack(R.id.search_edit_frame, homeCommerceFragment);
        BottomNavigationItem b0 = (BottomNavigationItem) findViewById(R.id.bni0);
        BottomNavigationItem b1 = (BottomNavigationItem) findViewById(R.id.bni1);
        BottomNavigationItem b2 = (BottomNavigationItem) findViewById(R.id.bni2);
        BottomNavigationItem b3 = (BottomNavigationItem) findViewById(R.id.bni3);

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        tvHeader.setOnClickListener(this);

        data.add(b0);
        data.add(b1);
        data.add(b2);
        data.add(b3);
        setClick(1);
//        String type = getIntent().getStringExtra(AppConstant.KEY_TYPE);
//        if (!TextUtils.isEmpty(type) && type.equals("msg")) {
//            setClick(2);
//            addFragmentNotToStack(R.id.search_edit_frame, new MessageFragment());
//        } else {
//            setClick(1);
//            addFragmentNotToStack(R.id.search_edit_frame, new HomeFragment());
//        }
        //if data is empty

    }


    private void setClick(int postion) {
        for (int i = 0; i < data.size(); i++) {
            if (i == postion) {
                data.get(i).setClick(true);
            } else {
                data.get(i).setClick(false);
            }

        }
    }





    public void goToNotificationSettings() {
        Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(i);
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bni0:
//                setClick(0);
//                addFragmentNotToStack(R.id.search_edit_frame, homeFragment);
                SimpleToast.toastMessage(getResources().getString(R.string.not_use), Toast.LENGTH_SHORT);
            case R.id.bni1:
                setClick(1);
                addFragmentNotToStack(R.id.search_edit_frame, new HomeCommerceFragment());
                break;
            case R.id.bni2:
//                setClick(2);
//                addFragmentNotToStack(R.id.search_edit_frame, new MessageFragment());

                SimpleToast.toastMessage(getResources().getString(R.string.not_use), Toast.LENGTH_SHORT);
                break;
            case R.id.bni3:
//                setClick(3);
//                addFragmentNotToStack(R.id.search_edit_frame, new EnterpriseHomeFragment());
                SimpleToast.toastMessage(getResources().getString(R.string.not_use), Toast.LENGTH_SHORT);
                break;
            case R.id.tv_header:
                toActivity(UserInfoActivity.class);
                break;
            default:
                break;
        }


    }

    @Override
    protected void initViewEvent() {

    }
}
