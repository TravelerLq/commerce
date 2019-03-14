package com.yuas.commerce.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.yuas.commerce.R;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.utils.ScreenUtil;


/**
 * Created by liqing on 2018/12/3.
 * 底部导航栏item
 */

public class BottomNavigationItem extends View {
    private Bitmap src;
    private String text;
    private int color;
    private Paint paint;
    private Paint textPaint;
    private Bitmap bitmap;
    private Rect imgRect;
    private Rect textRect;
    private Rect textClickRect;

    private boolean click = false;//选中状态，默认为未选中
    private int clickColor;
    private int clickTextColor;
    private Paint clickPaint;
    private Paint clickTextPaint;
    private Bitmap clickBitmap;
    private int currentR = 0;
    private Paint bgPaint;
    private Matrix matrix;
    private int imgWidth = ScreenUtil.dp2px(getContext(), 25);
    private int textSize = ScreenUtil.sp2px(getContext(), 10);
    private int clickTextSize = ScreenUtil.sp2px(getContext(), 12);
    private int bottom = ScreenUtil.dp2px(getContext(), 46);

    private int iconWidth;
    private int iconHeight;

    public void setClick(boolean click) {
        this.click = click;
        //onDraw()
        invalidateView();
    }

    public boolean getClick() {
        return click;
    }

    public BottomNavigationItem(Context context) {
        this(context, null);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initTools();
    }

    /**
     * 从XML中获取对应属性
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationItem);
        BitmapDrawable bd = (BitmapDrawable) array.getDrawable(R.styleable.BottomNavigationItem_src);
        assert bd != null;
        src = bd.getBitmap();
        text = array.getString(R.styleable.BottomNavigationItem_text);
        clickColor = array.getColor(R.styleable.BottomNavigationItem_color, 0xff6666);
        clickTextColor = array.getColor(R.styleable.BottomNavigationItem_click_text_color, 0xff6666);
       //释放资源
        array.recycle();
    }

    private void initTools() {
        textRect = new Rect();
        textClickRect = new Rect();
        textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        clickTextPaint = new Paint();
        clickTextPaint.setTextSize(clickTextSize);
        clickTextPaint.getTextBounds(text, 0, text.length(), textClickRect);
        imgRect = new Rect();
        paint = new Paint();

        clickPaint = new Paint();
        bgPaint = new Paint();
        bgPaint.setColor(clickColor);
        bgPaint.setAlpha(50);
        color = 0xFF807E7E;
        matrix = new Matrix();
    }

    //onMeasue override weight =0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int left = getMeasuredWidth() / 2 - imgWidth / 2;
        int top = ScreenUtil.dp2px(getContext(), 8);
        imgRect.set(left, top, left + imgWidth, top + imgWidth);
        matrix.setTranslate(0, ScreenUtil.dp2px(getContext(), -2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //invalidate called onDraw()---
        Loger.e("---onDraw--click"+click);
        if (click) {
            if (clickBitmap == null)
                clickBitmap = creatClickBitmap(clickColor, imgRect, clickPaint);
            // drawRipple(canvas);
            canvas.drawBitmap(clickBitmap, matrix, null);
            drawTargetText(canvas, 255);
        } else {
            if (bitmap == null) bitmap = creatClickBitmap(color, imgRect, paint);
            canvas.drawBitmap(bitmap, 0, 0, null);
            drawSourceText(canvas, 0);
        }

    }

    /**
     * 画波纹
     */
    private void drawRipple(Canvas canvas) {
        canvas.save();
        if (currentR < getMeasuredWidth() / 2 + 10) {
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, currentR, bgPaint);
            currentR += 10;
            postInvalidateDelayed(15);
        } else {
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 0, bgPaint);
            currentR = 0;
        }
        canvas.restore();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(this.iconWidth, this.iconHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (drawable instanceof BitmapDrawable) {
            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new RuntimeException("The Drawable must be an instance of BitmapDrawable");
        }
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        //clickTextPaint.setColor(clickColor);
        clickTextPaint.setColor(clickTextColor);
        clickTextPaint.setAlpha(alpha);
        canvas.drawText(text, getMeasuredWidth() / 2 - textClickRect.width() / 2, bottom, clickTextPaint);

    }

    private void drawSourceText(Canvas canvas, int alpha) {
        textPaint.setColor(color);
        textPaint.setAlpha(255 - alpha);
        canvas.drawText(text, getMeasuredWidth() / 2 - textRect.width() / 2, bottom, textPaint);
    }


    private Bitmap creatClickBitmap(int color, Rect rect, Paint paint) {
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawRect(rect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAlpha(255);
        canvas.drawBitmap(src, null, rect, paint);
        return bitmap;
    }



    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }


}

