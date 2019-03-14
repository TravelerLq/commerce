package com.yuas.commerce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.yuas.commerce.R;
import com.yuas.commerce.bean.AuthorityItemBean;
import com.yuas.commerce.bean.AuthorityResponseBean;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.view.ScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 2018/12/25.
 * <p>
 * 多布局加载，横向和竖直
 */

public class AuthorityParentRecycleAdapter extends
        RecyclerView.Adapter<AuthorityParentRecycleAdapter.MyViewHolder> implements View.OnClickListener {
    //轮播图模式
    private static final int TYPE_SINGLE = 0;
    //图集横向模式
    private static final int TYPE_MULTI = 1;
    //头布局模式
    private static final int TYPE_HEADER = 2;

    private Context context;
    private List<AuthorityResponseBean> list;
    private LayoutInflater inflater;
    private AuthorityConfigGridAdapter gridAdapter;
    List<AuthorityItemBean> listChildItems = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;

    public AuthorityParentRecycleAdapter(Context context, List<AuthorityResponseBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);

    }

    public static interface OnItemClickListener {
        void OnParentSelectClick(View view, int pos);

        void OnChildSelectClick(int pos, List<AuthorityItemBean> list);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view;

        //
        view = inflater.inflate(R.layout.item_authority_parent_layout, parent, false);
        myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AuthorityResponseBean bean = list.get(position);

        listChildItems.clear();
        listChildItems.addAll(bean.getResourcesVoList());
        gridAdapter = new AuthorityConfigGridAdapter(listChildItems, context);

        if (bean != null) {
            Loger.e("=-name" + bean.getResourcesName());
            holder.tvName.setText(bean.getResourcesName());
            holder.tvName.setTag(position);

            holder.gridView.setAdapter(gridAdapter);
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnChildSelectClick(position,bean.getResourcesVoList());
                    }

//                    Loger.e("=-parent item" + position);
//                    AuthorityItemBean authorityItemBean = bean.getResourcesVoList().get(position);
//                    authorityItemBean.setResourcesChecked(!authorityItemBean.isResourcesChecked());
////gridView.requesetLayout()
//                    holder.gridView.requestLayout();
//                    gridAdapter.notifyDataSetChanged();


                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1)
            return TYPE_MULTI;
        return TYPE_SINGLE;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        //父类的选择框
        ImageView ivSelector;
        View view;
        //里面的子类
        ScrollGridView gridView;


        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_authority_name);
            ivSelector = (ImageView) view.findViewById(R.id.iv_authority_selector);
            gridView = (ScrollGridView) view.findViewById(R.id.grid_parent);
            this.view = view;
        }
    }


}
