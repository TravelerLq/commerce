package com.yuas.commerce.network.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by liqing on 2018/12/4.
 */

public interface UserInfoApi {

    @Multipart
    @POST("shuidao/users/uploadheadimg")
    Observable<String> upload_single_file(@Part MultipartBody.Part image);

    @GET ("shuidao/users/info")
    Observable<String> getUserInfo();
}
