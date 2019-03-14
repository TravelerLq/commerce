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

public interface AuthorityConfigApi {

    //职位表的获取 /houtaimodel/authority/commerceidentity?commerceId=2
    @GET("authority/commerceidentity")
    Observable<Response<String>> getPositions(@QueryMap Map<String, Object> map);

    //ahthority获取 houtaimodel/authority/resources
    @POST("authority/resources")
    Observable<Response<String>> getAuthority(@Body String string);


    //houtaimodel/authority/initposition?frontOrBack=1&id=1
    @GET("authority/initposition")
    Observable<Response<String>> getAthorityByPosition(@QueryMap Map<String, Object> map);


    // /houtaimodel/authority/initpersonal?frontOrBack=1&userId=2&commerceId=2
    @GET("authority/initpersonal")
    Observable<Response<String>> getAhorityByName(@QueryMap Map<String, Object> map);

    //ahthority配置提交
    @POST("shuidao/quotas/all")
    Observable<Response<String>> submitAuthority(@QueryMap Map<String, Object> map);

    //http://192.168.31.86:8088/houtaimodel/authority/confpersonal

    @POST("authority/confpersonal")
    Observable<Response<String>> submitAuthorityByName(@Body String str);

    //http://192.168.31.86:8088/houtaimodel/authority/confposition
    @POST("authority/confposition")
    Observable<Response<String>> submitAuthorityByPosition(@Body String str);



    //配置权限时候 搜索名字  /houtaimodel/user/getname?userName=陈&commerceId=1

    @GET("user/getname")
    Observable<Response<String>> searchName(@QueryMap Map<String, Object> map);


    //修改


}
