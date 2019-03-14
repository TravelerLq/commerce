package com.yuas.commerce.network.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by liqing on 2018/12/28.
 */

public interface UploadFileApi {

    @POST("upload/pictures")
    Observable<String> uploadSinglePic(@Body MultipartBody multipartBody);



//multiPics
    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */

    @POST("shuidao/upload/pictures")
    Observable<String> uploadMultiPics(@Body MultipartBody multipartBody);




//    //上传视频
    @Multipart
    @POST("upload/videos")
    Observable<String> uploadVedio(@Part MultipartBody.Part vedioFile);

    //上传视频
//    @Multipart
//    @POST("/houtaimodel/upload/videos")
//    Observable<String> uploadVedio(@Part("description")String des,
//                                   @Part MultipartBody.Part vedioFile);



}
