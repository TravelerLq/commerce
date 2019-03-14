package com.yuas.commerce.network.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.commerce.bean.UserInfoBean;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.exception.IApiException;
import com.yuas.commerce.listener.UploadProgressListener;
import com.yuas.commerce.network.api.UserInfoApi;
import com.yuas.commerce.utils.Loger;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by liqing on 17/11/20.
 */

public class UserInfoApiControl extends BaseControl {

    public Observable<UserInfoBean> getUserInfo() {
        Retrofit retrofit = builderRetrofitWithHeader();
        UserInfoApi userInfoApi = retrofit.create(UserInfoApi.class);
        Observable<String> observable = userInfoApi.getUserInfo();
        return observable.map(new Function<String, UserInfoBean>() {

            @Override
            public UserInfoBean apply(String s) throws Exception {
                Loger.e("userInfo--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    UserInfoBean userInfoBean1 = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), UserInfoBean.class);
                    return userInfoBean1;
                }
                throw new IApiException("userInfo", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }


    public Observable<String> upload_single_file(File file, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderUploadPicRetrofit();
        UserInfoApi userInfoApi = retrofit.create(UserInfoApi.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Loger.e("路径图片 " + file.getName());
        return userInfoApi.upload_single_file(part);
    }


}
