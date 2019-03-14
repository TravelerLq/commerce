package com.yuas.commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuas.commerce.R;
import com.yuas.commerce.bean.AuthorityItemBean;
import com.yuas.commerce.utils.Loger;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 17/11/16.
 * GridView 的adapter
 */

public class AuthorityConfigGridAdapter extends BaseAdapter {

    //默认上传最大数值
    private String TAG = "AuthorityConfigGridAdapter";
    private int maxCount = 9;
    private List<AuthorityItemBean> datas;
    private Context context;
    private LayoutInflater inflater;
    AddAndDeleteListener listener;
    private String selectIds;
    private List<String> selectIdsList = new ArrayList<>();


    public AuthorityConfigGridAdapter(List<AuthorityItemBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setMaxImages(int maxImages) {
        this.maxCount = maxImages;
    }

    public interface AddAndDeleteListener {
        void setSelect();


    }

    public String getSelectIds() {
        Loger.e("---adapter.getSelectIds");
        return selectIds;
    }

    public List<String> getSelectIdsList() {
        Loger.e("---adapt");

        return selectIdsList;
    }

    public void clearSelectIds() {

    }

    public void clearSelectIdsList() {
        selectIdsList.clear();
    }

    public void setAddAndDeleteListener(AddAndDeleteListener listener) {
        this.listener = listener;
    }


    @Override
    public int getCount() {

        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_authority_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AuthorityItemBean bean = datas.get(position);
        viewHolder.title.setText(bean.getResourcesName());
        Glide.with(context)
                .load(bean.getResourcesIcon())
                .into(viewHolder.imageView);
        Loger.e("---pos" + position);
        if (bean.isResourcesChecked()) {
            Loger.e("---select" + position + "ids" + bean.getId());
            viewHolder.ivSelection.setImageResource(R.drawable.ic_select);
            selectIdsList.add(bean.getId());

        } else {
            viewHolder.ivSelection.setImageResource(R.drawable.ic_unselect);
        }

        return convertView;
    }

    class ViewHolder {

        ImageView imageView;
        ImageView ivSelection;
        TextView title;
        RelativeLayout rlItemParent;

        public ViewHolder(View root) {
            imageView = (ImageView) root.findViewById(R.id.iv_authority_icon);
            title = (TextView) root.findViewById(R.id.tv_authority_title);
            rlItemParent = (RelativeLayout) root.findViewById(R.id.rl_item_parent);
            ivSelection = (ImageView) root.findViewById(R.id.iv_selection);
        }
    }


}