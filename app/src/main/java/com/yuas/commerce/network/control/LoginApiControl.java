package com.yuas.commerce.network.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.commerce.bean.BussinessVOBean;
import com.yuas.commerce.bean.LoginCommerceResponseBean;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.constant.MySpEdit;
import com.yuas.commerce.exception.IApiException;
import com.yuas.commerce.network.api.LoginApi;
import com.yuas.commerce.utils.Loger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Yso on 2017/11/13.
 */

public class LoginApiControl extends BaseControl {

    //商会通的登录
    public Observable<LoginCommerceResponseBean> loginCommerce(String name, String psw,String commerceId) {
        Retrofit retrofit = builderJsonHeader2();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("username", name);
        parmsMap.put("userpwd", psw);
        parmsMap.put("commerceId",commerceId);

        //  parmsMap.put("type",loginRequestbean.getType());
        String jsonRequestStr = JSONObject.toJSONString(parmsMap);
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<Response<String>> observable = loginApi.loginCommerce(jsonRequestStr);
        Loger.i("login requestbody= " + jsonRequestStr);
        return observable.map(new Function<Response<String>, LoginCommerceResponseBean>() {
            @Override
            public LoginCommerceResponseBean apply(@NonNull Response<String> s) throws Exception {
                Loger.e("login = " + s);
                Headers headers = s.headers();
                String authStr = headers.get("Authorization");
                Loger.e("commerce -auth" + authStr);

                MySpEdit.getInstance().setCommerceAuthor(authStr);
                JSONObject jsonObject = JSON.parseObject(s.body());
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    String data =jsonObject.getString(AppConstant.JSON_DATA);
                    LoginCommerceResponseBean loginResponseBean =JSON.parseObject(data,LoginCommerceResponseBean.class);
                    return loginResponseBean;
                }

                throw new IApiException("商会通登陆", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


    //商会通的登录
    public Observable<List<BussinessVOBean>> getCommerces() {
        Retrofit retrofit = builderJsonHeader2();

        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<Response<String>> observable = loginApi.getCommerces();

        return observable.map(new Function<Response<String>, List<BussinessVOBean>>() {
            @Override
            public List<BussinessVOBean> apply(@NonNull Response<String> s) throws Exception {
                Loger.e("login = " + s);
                Headers headers = s.headers();
                String authStr = headers.get("Authorization");
                Loger.e("commerce -auth" + authStr);

                MySpEdit.getInstance().setCommerceAuthor(authStr);
                JSONObject jsonObject = JSON.parseObject(s.body());
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    String data =jsonObject.getString(AppConstant.JSON_DATA);
                    List<BussinessVOBean> list =JSON.parseArray(data,BussinessVOBean.class);
                    return list;
                }

                throw new IApiException("商会通获取", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


    public Observable<String> loginOut() {
        Retrofit retrofit = builderJsonRetrofit();
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<String> observable = loginApi.loginOut();
        return observable.map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                Loger.e("---response.body=" + s);
                return null;
            }
        });
    }

}
