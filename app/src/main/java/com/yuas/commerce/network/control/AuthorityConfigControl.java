package com.yuas.commerce.network.control;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.commerce.bean.AuthorityRequestBean;
import com.yuas.commerce.bean.AuthorityResponseBean;
import com.yuas.commerce.bean.PersonBean;
import com.yuas.commerce.bean.PositionBean;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.exception.IApiException;
import com.yuas.commerce.network.api.AuthorityConfigApi;
import com.yuas.commerce.utils.Loger;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 2018/12/19.
 */

public class AuthorityConfigControl extends BaseControl {


    //获取职位列表
    public Observable getPostions(String commerceId) {
        Retrofit retrofit = buildRetrofitWithParams();
        AuthorityConfigApi api = retrofit.create(AuthorityConfigApi.class);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("commerceId", commerceId);
        Observable<Response<String>> observable = api.getPositions(paramsMap);
        return observable.map(new Function<Response<String>, List<PositionBean>>() {
            @Override
            public List<PositionBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                Loger.e("getPostions = " + responseStr);
                JSONObject jsonObject = JSONObject.parseObject(responseStr);
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
//                    JSONObject jsonData = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    List<PositionBean> list = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), PositionBean.class);
                    return list;
                }
                return null;
            }
        });

    }

    //获取权限列表
    public Observable getAuthority(String type) {

        Retrofit retrofit = builderJsonHeader2();
        AuthorityConfigApi api = retrofit.create(AuthorityConfigApi.class);
//        String ids = requestBean.getIds();
        Map<String, String> params = new HashMap<>();
        params.put("frontOrBack", type);
        String jsonRequestStr = JSONObject.toJSONString(params);
        Observable<Response<String>> observable = api.getAuthority(jsonRequestStr);
        return observable.map(new Function<Response<String>, List<AuthorityResponseBean>>() {

            @Override
            public List<AuthorityResponseBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                Loger.e("getAuthority = " + responseStr);
                JSONObject jsonObject = JSONObject.parseObject(responseStr);

                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    // JSONObject jsonData = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    String jsoStr = jsonObject.getString(AppConstant.JSON_DATA);
                    if (jsoStr != null) {

                        // String js = JSONObject.toJSONString(jsoStr, SerializerFeature.WriteClassName);//将array数组转换
                        List<AuthorityResponseBean> list = JSON.parseArray(jsoStr, AuthorityResponseBean.class);
                        return list;
                    }

                    // AuthorityResponseBean responseBean = JSON.parseObject(jsonData.getString("authority"), AuthorityResponseBean.class);
                }
                return null;
            }
        });

    }

    //根据 职位id获取权限
    public Observable getAuthorityByPosition(String type, String id) {

        Retrofit retrofit = buildRetrofitWithParams();
        AuthorityConfigApi api = retrofit.create(AuthorityConfigApi.class);
        Map<String, Object> params = new HashMap<>();
        params.put("frontOrBack", type);
        params.put("id", id);
        //   String jsonRequestStr = JSONObject.toJSONString(params);
        Observable<Response<String>> observable = api.getAthorityByPosition(params);
        return observable.map(new Function<Response<String>, List<AuthorityResponseBean>>() {

            @Override
            public List<AuthorityResponseBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                Loger.e("getAuthority = " + responseStr);
                JSONObject jsonObject = JSONObject.parseObject(responseStr);

                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    // JSONObject jsonData = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    String jsoStr = jsonObject.getString(AppConstant.JSON_DATA);
                    if (jsoStr != null) {

                        // String js = JSONObject.toJSONString(jsoStr, SerializerFeature.WriteClassName);//将array数组转换
                        List<AuthorityResponseBean> list = JSON.parseArray(jsoStr, AuthorityResponseBean.class);
                        return list;
                    }

                    // AuthorityResponseBean responseBean = JSON.parseObject(jsonData.getString("authority"), AuthorityResponseBean.class);
                }
                return null;
            }
        });

    }

    //根据 userid获取权限
    public Observable getAuthoritByName(String type, String userId, String commerceId) {

        Retrofit retrofit = buildRetrofitWithParams();
        AuthorityConfigApi api = retrofit.create(AuthorityConfigApi.class);
//        String ids = requestBean.getIds();
        Map<String, Object> params = new HashMap<>();
        params.put("frontOrBack", type);
        params.put("userId", userId);
        params.put("commerceId", commerceId);
        //String jsonRequestStr = JSONObject.toJSONString(params);
        Observable<Response<String>> observable = api.getAhorityByName(params);
        return observable.map(new Function<Response<String>, List<AuthorityResponseBean>>() {

            @Override
            public List<AuthorityResponseBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                Loger.e("getAuthority = " + responseStr);
                JSONObject jsonObject = JSONObject.parseObject(responseStr);
                String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (AppConstant.JSON_SUCCESS.equals(response)) {
                    // JSONObject jsonData = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    String jsoStr = jsonObject.getString(AppConstant.JSON_DATA);
                    if (jsoStr != null) {

                        // String js = JSONObject.toJSONString(jsoStr, SerializerFeature.WriteClassName);//将array数组转换
                        List<AuthorityResponseBean> list = JSON.parseArray(jsoStr, AuthorityResponseBean.class);
                        return list;
                    }

                    // AuthorityResponseBean responseBean = JSON.parseObject(jsonData.getString("authority"), AuthorityResponseBean.class);
                }
                JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                throw new IApiException("获取权限", jsonObject1.getString(AppConstant.JSON_MSG));
            }
        });

    }

    //根据名字搜索

    public Observable searchName(String name, String commerceId) {
        Retrofit retrofit = buildRetrofitWithParams();
        AuthorityConfigApi api = retrofit.create(AuthorityConfigApi.class);
        Map<String, Object> params = new HashMap<>();
        //userName=陈&commerceId=1
        params.put("userName", name);
        params.put("commerceId", commerceId);

        Observable<Response<String>> observable = api.searchName(params);
        return observable.map(new Function<Response<String>, List<PersonBean>>() {

            @Override
            public List<PersonBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                Loger.e("getSearchName = " + responseStr);
                JSONObject jsonObject = JSONObject.parseObject(responseStr);

                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    // JSONObject jsonData = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    String jsonArray = jsonObject.getString(AppConstant.JSON_DATA);
                    if (!TextUtils.isEmpty(jsonArray)) {

                        List<PersonBean> list = JSON.parseArray(jsonArray, PersonBean.class);
                        return list;
                    }

                    // AuthorityResponseBean responseBean = JSON.parseObject(jsonData.getString("authority"), AuthorityResponseBean.class);
                }
                return null;
            }
        });
    }


    //提交配置好的权限

    public Observable<Boolean> submitAuthority(AuthorityRequestBean bean, String type) {
        Observable observable = null;
        Retrofit retrofit = builderJsonHeader2();
        AuthorityConfigApi api = retrofit.create(AuthorityConfigApi.class);
        Map<String, Object> params = new HashMap<>();
        if (type.equals("0")) {
            //按照职

            params.put("roleId", bean.getRoleId());
            params.put("resourcesId", bean.getResourcesId());
            observable = api.submitAuthorityByPosition(JSON.toJSONString(params));
        } else {
            //按姓名
            params.put("userId", bean.getUserId());
            params.put("resourcesId", bean.getResourcesId());
            params.put("commerceId", bean.getCommerceId());
            String paramsStr = JSON.toJSONString(params);
            observable = api.submitAuthorityByName(paramsStr);
        }

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (AppConstant.JSON_SUCCESS.equals(status)) {
                    return true;
                }
                JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                throw new IApiException("获取权限", jsonObject1.getString(AppConstant.JSON_MSG));
            }
        });

    }

}

