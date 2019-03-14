package com.yuas.commerce.bean;

import android.graphics.Bitmap;


/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class PicBean extends BaseBean {
    //0 是+ 非0 是图片
    private int status;
    private Bitmap bitmap;
    private String photoPath;
    private String originalUrl;
    private String thumbnailUrl;


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public PicBean() {
    }



    public PicBean(int status, Bitmap bitmap) {
        this.status = status;
        this.bitmap = bitmap;
    }


    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
