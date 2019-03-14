package com.yuas.commerce.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.yuas.commerce.R;
import com.yuas.commerce.adapter.SearchRecycleAdapter;
import com.yuas.commerce.bean.PersonBean;
import com.yuas.commerce.constant.MySpEdit;
import com.yuas.commerce.network.control.AuthorityConfigControl;
import com.yuas.commerce.observer.CommonDialogObserver;
import com.yuas.commerce.observer.RxHelper;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.view.LinearLayoutColorDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 2018/12/19.
 */

public class SearchPersonActivity extends BaseActivity {

    //import android.support.v7.widget.RecyclerView;
    @BindView(R.id.edt_search_name)
    EditText edtSearchName;

    @BindView(R.id.recycle_search_name)
    RecyclerView recyclerSearchName;
    @BindView(R.id.iv_search_name)
    ImageView ivSearchName;

    private Context context;
    private String searchKey;
    private SearchRecycleAdapter mAdapter;
    private List<PersonBean> list = new ArrayList<>();
    private String commerceId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        context = SearchPersonActivity.this;
        commerceId = MySpEdit.getInstance().getCommerceId();
        initViewEvent();
        search("", commerceId);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerSearchName.setLayoutManager(linearLayoutManager);
        //custom itemderatio
        //不要用view来做分割线，会影响性能 ，具体怎么影响？
        //  recyclerSearchName.addItemDecoration(new ItemDecor(context,LinearLayoutManager.HORIZONTAL,R.drawable.item_divider_grey));
        recyclerSearchName.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        mAdapter = new SearchRecycleAdapter(context, list);
        recyclerSearchName.setAdapter(mAdapter);
        searchKey = edtSearchName.getText().toString().trim();
        mAdapter.setOnItemClickListener(new SearchRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                Loger.e("---click item");
                // Toast.makeText(context, "itemcliked" + pos, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", list.get(pos));
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        });

    }


    @Override
    protected void initViewEvent() {
        ivSearchName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.iv_search_name) {
//            if (TextUtils.isEmpty(searchKey)) {
//                Toast.makeText(context, getResources().getString(R.string.search_key_null_check), Toast.LENGTH_SHORT);
//            }
            searchKey = edtSearchName.getText().toString();
            search(searchKey, commerceId);
        }

    }

    //搜索姓名
    private void search(String searchKey, String commerceId) {
        //initData();

        Observable<List<PersonBean>> observable = new AuthorityConfigControl().searchName(searchKey, commerceId);
        CommonDialogObserver<List<PersonBean>> observer = new CommonDialogObserver<List<PersonBean>>(this) {

            @Override
            public void onNext(List<PersonBean> namesBean) {
                super.onNext(namesBean);
                if (namesBean != null) {
                    list.clear();
                    list.addAll(namesBean);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, SearchPersonActivity.this);

    }

    // String 不可变类： 保证线程安全；不可变是指成员变量

}
