package com.yuas.commerce.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yuas.commerce.R;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.listener.UploadProgressListener;
import com.yuas.commerce.network.control.UserInfoApiControl;
import com.yuas.commerce.observer.CommonDialogObserver;
import com.yuas.commerce.observer.RxHelper;
import com.yuas.commerce.utils.FileUtils;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.view.SimpleToast;


import java.io.File;

import butterknife.BindView;
import io.reactivex.Observable;

import static com.yuas.commerce.utils.AutoInstall.getFileProviderAuthority;
import static com.yuas.commerce.utils.FileUtils.getRealFilePathFromUri;


/**
 * Created by liqing on 2018/12/6.
 */

public class ViewBigImageActivity extends BaseActivity {

    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;

    @BindView(R.id.button_back)
    ImageButton buttonBack;
    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_change)
    TextView tvChange;

    @BindView(R.id.iv_header_big)
    ImageView ivHeaderBig;


    private File tempFile;


    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_image_layout);
        initViewEvent();
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            Glide.with(ViewBigImageActivity.this)
                    .load(url)
                    .into(ivHeaderBig);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_change) {
            type = 2;
            uploadHeadImage();
        } else if (view.getId() == R.id.button_back) {
            finish();
        }
    }


    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        // View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_header_image_layout, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(ViewBigImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ViewBigImageActivity.this, new String[]
                                    {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    goToCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(ViewBigImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ViewBigImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    goToPhotos();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void goToCamera() {

        //创建拍照存储的图片文件
        tempFile = new File(FileUtils.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
        //tempFile = new File(FileUtils.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/", System.currentTimeMillis() + ".jpg");
        Loger.e("tempFile=" + tempFile.getPath());
        //跳转到调用系统照相机
        Uri contentUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7。0系统共享文件，分享路径定义 xml/file_paths.xml
            //${applicationId}.vsd_provider

            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            //String authority = getApplicationContext().getApplicationInfo().packageName + ".fileProvider";
            String authority = getFileProviderAuthority();
            Loger.e("authority=" + authority);
            contentUri = FileProvider.getUriForFile(ViewBigImageActivity.this, authority, tempFile);

        } else {
            contentUri = Uri.fromFile(tempFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, REQUEST_CAPTURE);

    }

    private void goToPhotos() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE:
                    //拍照返回，跳到图片剪裁
                    gotoClipActivity(Uri.fromFile(tempFile));
                    break;
                case REQUEST_PICK:
                    Uri uri = data.getData();
                    gotoClipActivity(uri);

                    break;
                case REQUEST_CROP_PHOTO:
                    //剪切图片成功返回
                    final Uri uriCrop = data.getData();
                    if (uriCrop == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uriCrop);
                    //剪裁成功，上传到服务器
                    Loger.e("crop-filePath" + cropImagePath);
                    uploadPic(cropImagePath, uploadProgressListener);


                    break;
                default:
                    break;
            }

        }

    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    private void uploadPic(final String path, UploadProgressListener uploadProgressListener) {
        File file = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new UserInfoApiControl().upload_single_file(file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(ViewBigImageActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);

                Loger.i("subFile" + s);

                JSONObject jsonObject = JSON.parseObject(s);
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    SimpleToast.toastMessage("上传成功", Toast.LENGTH_SHORT);
                    // UserInfoBean userInfoBean1 = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), UserInfoBean.class);
                    Loger.e("uplaod path=" + path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);

                    ivHeaderBig.setImageBitmap(bitmap);


                } else {
                    SimpleToast.toastMessage("上传失败，请重试", Toast.LENGTH_SHORT);
                }


                //

//                Glide.with(UserInfoActivity.this)
//                        .load(path)
//
//                        .into(headerImage);
            }
        };
        RxHelper.bindOnUI(observable, observer);
    }


    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };

    @Override
    protected void initViewEvent() {
        tvChange.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

    }
}
