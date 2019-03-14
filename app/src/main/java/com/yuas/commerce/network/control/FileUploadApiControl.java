package com.yuas.commerce.network.control;

import com.yuas.commerce.listener.UploadProgressListener;
import com.yuas.commerce.network.api.UploadFileApi;
import com.yuas.commerce.utils.Loger;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by liqing on 17/11/20.
 * 商会通里面的文件上传（图片 +视频）
 */

public class FileUploadApiControl extends BaseControl {

    //一张图片

    public Observable<String> uploadSinglePic(File file, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderUploadFileRetrofit2();
        UploadFileApi api = retrofit.create(UploadFileApi.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        builder.addFormDataPart("picturefile", file.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return api.uploadSinglePic(multipartBody);
    }

    //多图片
    public Observable<String> uploadMultiPics(List<File> files, final UploadProgressListener uploadProgressListener) {
        //  header 5
        // Retrofit retrofit = builderUploadPicRetrofit();
        Retrofit retrofit = builderUploadPicOneHeaderRetrofit();
        UploadFileApi api = retrofit.create(UploadFileApi.class);

        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("files", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();


        return api.uploadMultiPics(multipartBody);
    }

//uploadVedio

    public Observable<String> uploadVedio(File file, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderUploadFileRetrofit2();
        UploadFileApi api = retrofit.create(UploadFileApi.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("video/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("videofile", file.getName(), requestBody);
        Loger.e(" 视频 " + file.getName());
//        api.uploadVedio("des",part);
        return api.uploadVedio(part);
    }


}
