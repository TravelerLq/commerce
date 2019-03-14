package com.yuas.commerce.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 2018/12/19.
 */

public interface ModifyEnterpriseInfoApi {


    //获取企业资料
    @GET("com/companyInfo")
    Observable<Response<String>> getEnterpriseInfo(@QueryMap Map<String, Object> map);


    //修改企业资料
    @POST("com/companychange")
    Observable<Response<String>> submitModifyInfo(@Body String string);



}
