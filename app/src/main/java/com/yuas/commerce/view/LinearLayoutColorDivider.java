package com.yuas.commerce.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuas.commerce.utils.Loger;


/**
 * Created by liqing on 2018/12/20.
 */

public class LinearLayoutColorDivider extends RecyclerView.ItemDecoration {

    private static String TAG = LinearLayoutColorDivider.class.getSimpleName();
    private final Drawable mDivider;
    private final int mSize;
    private final int mOrientation;

    public LinearLayoutColorDivider(Resources resources, @ColorRes int color,
                                    @DimenRes int size, int orientation) {
        mDivider = new ColorDrawable(resources.getColor(color));
        mSize = resources.getDimensionPixelSize(size);
        mOrientation = orientation;
    }

    //onDraw中为divider 绘制范围 可以超出GetIntemOffset ,但是是绘制在View下的，所以不可见但会出现overdraw
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Loger.e(TAG + "----onDraw");
        int left;
        int right;
        int top;
        int bottom;
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                left = child.getRight() + params.rightMargin;
                right = left + mSize;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }

        } else {
            //vertical -----
            left = parent.getPaddingLeft(); //paddingleft
            right = parent.getWidth() - parent.getPaddingRight(); //with -paddingRight
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                //view.getBottom :Bottom position of this view relative to its parent
                top = child.getBottom() + params.bottomMargin;  // view.getBottom =view.top+view.getHeight();
                //params.bottomMargin : item.xml 中的layout_marginBottom属性值；官方bottomMargin解释the bottom margin in px of the child
              //  Loger.e("--child.getBottom"+child.getBottom() +"\n"+"params.bottomMargin"+params.bottomMargin);
                bottom = top + mSize;
                //setBound: 画具体位置
                mDivider.setBounds(left, top, right, bottom);
                //  mDivider.setBounds(left, top, right, );
                mDivider.draw(c);
            }
        }
    }


    //getItemOffset 算入insets 中，（记录所有ItemDeraction的值），之后在recycleview measureChild()计算到item view.padding
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        Loger.e(TAG + "---getItemOffsets");
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, mSize, 0);

        } else {
            outRect.set(0, 0, 0, mSize);
        }

    }
}
