package com.yuas.commerce.utils;

import java.net.URL;

/**
 * 解决图片Url地址带有token的方法,为了后期使用
 * using:
 *  Glide.with()
 * .load(new MyGlideUrl(url))
 * .into(imageview);
 */

public class MyGlideUrl extends com.bumptech.glide.load.model.GlideUrl {
    public MyGlideUrl(URL url) {
        super(url);
    }

    //重写,根据图片token地址，用来获取不带token的图片地址，作为key返回
    @Override
    public String getCacheKey() {
        return super.getCacheKey();
    }


}
