package com.yuas.commerce.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.yuas.commerce.R;
import com.yuas.commerce.adapter.AuthorityParentRecycleAdapter;
import com.yuas.commerce.bean.AuthorityItemBean;
import com.yuas.commerce.bean.AuthorityResponseBean;
import com.yuas.commerce.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 2018/12/25.
 */

public class RecycleGridLayoutActivity extends BaseActivity {

    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    AuthorityParentRecycleAdapter mAdapter;
    List<AuthorityResponseBean> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_config1_layout);
        initData();
        //  GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                //int type = recyclerView.getAdapter().getItemViewType(position);//获得返回值
                if (position == 1) {
                    return 2;
                }
                return 1;


            }
        });

        //

        recyclerView.setLayoutManager(gridLayoutManager);


        Loger.e("--");
        mAdapter = new AuthorityParentRecycleAdapter(RecycleGridLayoutActivity.this, list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AuthorityParentRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnParentSelectClick(View view, int pos) {

            }

            @Override
            public void OnChildSelectClick(int pos, List<AuthorityItemBean> list) {
                Loger.e("--- OnChildSelectClick");
                AuthorityItemBean authorityItemBean = list.get(pos);
                authorityItemBean.setResourcesChecked(!authorityItemBean.isResourcesChecked());
                mAdapter.notifyDataSetChanged();
            }
        });


//        recyclerView = new HomeAdapter(this, mDataList);
//        recyclerView.setAdapter(mAdapter);

    }

    private void initData() {

        for (int i = 0; i < 4; i++) {
            AuthorityResponseBean bean = new AuthorityResponseBean();
            bean.setResourcesName("level1" + i);
            bean.setResourcesChecked(false);
            List<AuthorityItemBean> childItemiList = new ArrayList<>();

            for (int j = 0; j < 3; j++) {

                AuthorityItemBean bean1 = new AuthorityItemBean();
                if (i != 0) {
                    bean1.setResourcesName("level2" + i);
                    bean1.setResourcesChecked(true);
                }

                childItemiList.add(bean1);
            }
            bean.setResourcesVoList(childItemiList);
            list.add(i, bean);
        }

    }


    @Override
    protected void initViewEvent() {

    }
}
