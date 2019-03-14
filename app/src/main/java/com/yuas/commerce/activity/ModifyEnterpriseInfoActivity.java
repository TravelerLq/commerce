package com.yuas.commerce.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yuas.commerce.R;
import com.yuas.commerce.bean.EnterpriseInfoBean;
import com.yuas.commerce.bean.ModifyEnterpriseRequestBean;
import com.yuas.commerce.bean.PicBean;
import com.yuas.commerce.bean.VedioUploadResponseBean;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.constant.MySpEdit;
import com.yuas.commerce.listener.UploadProgressListener;
import com.yuas.commerce.network.control.FileUploadApiControl;
import com.yuas.commerce.network.control.ModifyEnterpriseInfoControl;
import com.yuas.commerce.observer.CommonDialogObserver;
import com.yuas.commerce.observer.RxHelper;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.utils.UriUtil;
import com.yuas.commerce.view.SimpleToast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by liqing on 2018/12/27.
 */

public class ModifyEnterpriseInfoActivity extends BaseActivity implements UploadProgressListener {

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    @BindView(R.id.edt_company_introduce)
    EditText edtCompanyIntroduce;

    @BindView(R.id.edt_company_product)
    EditText edtCompanyIProduct;

    @BindView(R.id.edt_company_website)
    EditText edtCompanyWebsite;

    @BindView(R.id.edt_contact)
    EditText edtContact;

    @BindView(R.id.edt_contact_way)
    EditText edtContactWay;

    @BindView(R.id.tv_add_pic)
    TextView tvAddPic;

    @BindView(R.id.img_pic)
    ImageView imgPic;

    @BindView(R.id.tv_add_vedio)
    TextView tvAddVedio;

    @BindView(R.id.tv_other_modify)
    TextView tvOtherModify;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @BindView(R.id.frame_vedio)
    FrameLayout frameVedio;

    @BindView(R.id.img_video_thumbnail)
    ImageView imgVedioThumbnail;

    private Dialog dialog;
    private View inflate;
    private TextView cancel;
    private TextView tvSelectOne;
    private TextView tvSelectTwo;
    private Context context;
    // Request code for user select video file.
    private static final int REQUEST_CODE_SELECT_VIDEO_FILE = 1;
    // Request code for require android READ_EXTERNAL_PERMISSION.
    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //请求OtherModifyInfo
    private static final int REQUEST_CODE_OTHER_MODIFY_INFO = 113;


    // Used when update video progress thread send message to progress bar handler.
    private static final int UPDATE_VIDEO_PROGRESS_BAR = 3;
    //recording
    private static int REQUEST_VEDIO_CAPTURE = 300;

    // Save local video file uri.
    private Uri videoFileUri = null;
    private File fileVedio;

    //公司视频
    private String videoUrl = "";
    //视频截图
    private String videoScreenshotsUrl = "";
    //图片地址
    private String pictureUrl = "";
    //图片缩略图
    private String pictureThumbnailUrl = "";
    private String commerceCompanyId;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private List<PicBean> picBeanList;
    private EnterpriseInfoBean enterpriseInfoBeanGet;
    private ModifyEnterpriseRequestBean modifyBean;
    private String baseUrl;


// "coid": 4,

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_enterprise_info);
        picBeanList = new ArrayList<>();
        titleTxt.setText(getResources().getString(R.string.modify_company_info));
        frameVedio.setVisibility(View.GONE);
        imgPic.setVisibility(View.GONE);
        context = ModifyEnterpriseInfoActivity.this;
        initViewEvent();
        initTestData();
        baseUrl = AppConstant.getCommerceUrlResource();
    }


    @Override
    protected void initViewEvent() {
        backBtn.setOnClickListener(this);
        titleTxt.setOnClickListener(this);
        tvAddPic.setOnClickListener(this);
        tvAddVedio.setOnClickListener(this);
        tvOtherModify.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        imgVedioThumbnail.setOnClickListener(this);

        commerceCompanyId = MySpEdit.getInstance().getCommerceCompanyId();

        Loger.e("getCommerceCompanyId=" + commerceCompanyId);

        getData(commerceCompanyId);

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_add_pic:
                //选择照片
                takePics();
                break;
            case R.id.tv_add_vedio:
                showDialog("本地视频", "拍摄");
                break;
//            case R.id.tv_select_company:
//                //tvSelectOne  本地视频
//                selectVedioFromLocal();
//                break;
//            case R.id.tv_register_company:
//                //tvSelectTwo 拍摄
//                recording();
//                break;

            case R.id.tv_other_modify:
//                toActivity(OtherModifyInfoActivity.class);
                toOtherInfoActivityWithParams(OtherModifyInfoActivity.class, enterpriseInfoBeanGet,
                        REQUEST_CODE_OTHER_MODIFY_INFO, true);
                break;
            case R.id.tv_sure:
                chedata();
                break;
            case R.id.img_video_thumbnail:
                playVedio();
                break;
            case R.id.img_pic:
                //查看大图

                break;
            default:
                break;
        }
    }

    private void playVedio() {
        openSystemVideoPlayer(baseUrl + videoUrl);//视频未下载，播放网络视频

//        if (fileVedio.exists()) {
//            String videoFilePath = fileVedio.getAbsolutePath();
//            openSystemVideoPlayer(videoFilePath);
//        } else {
//            openSystemVideoPlayer(baseUrl+videoUrl);//视频未下载，播放网络视频
//
//        }
    }

    /**
     * 使用系统自带播放器播放视频
     *
     * @param videoPath
     */
    private void openSystemVideoPlayer(String videoPath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(videoPath);
            intent.setDataAndType(uri, "video/*");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

            if (e.toString().contains("ActivityNotFoundException")) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                        .setTitle("温馨提醒")
                        .setMessage("该手机没有播放视频的应用，请安装视频播放软件后再试")
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                gotoAppDetailSettingIntent(context);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialogBuilder.create().show();
            }
            e.printStackTrace();
        }
    }

    public static void gotoAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            Log.e("LoginActivity", ".SDK_INT >= 9 ");
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));

        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
        //launchApp(context, localIntent);
    }


    private void recording() {

        //检查授权
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(ModifyEnterpriseInfoActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            //打开录制视频
            gotoCapture();
        }

    }

    //capture vedio
    private void gotoCapture() {
        Intent intent = new Intent();

        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VEDIO_CAPTURE);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(resultCode==RESULT_OK){
//            switch (requestCode){
//                case REQUEST_CODE_SELECT_VIDEO_FILE:
//                    break;
//            }
//        }

        // Identify activity by request code.
        if (requestCode == REQUEST_CODE_SELECT_VIDEO_FILE) {
            // If the request is success.
            if (resultCode == RESULT_OK) {
                // To make example simple and clear, we only choose video file from local file,
                // this is easy to get video file real local path.
                // If you want to get video file real local path from a video content provider
                videoFileUri = data.getData();
                String vedioPath = UriUtil.getPath(context, videoFileUri);
                Loger.e("vedioPath--" + vedioPath);
                if (!TextUtils.isEmpty(vedioPath)) {

                    //上传视频
                    uploadFile("vedio", vedioPath, this);
                }


            }
        } else if (requestCode == REQUEST_VEDIO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                //
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    android.support.v4.app.ActivityCompat.requestPermissions(ModifyEnterpriseInfoActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_PERMISSION);
                } else {
                    Uri uri = data.getData();
                    String pathToVideoFile = getPath(uri);
                    //  String pathToVideoFile = getRealPathFromURIPath(uri, ModifyEnterpriseInfoActivity.this);
                    Loger.e("--uriPath--" + pathToVideoFile);
                    if (!TextUtils.isEmpty(pathToVideoFile)) {

                        uploadFile("vedio", pathToVideoFile, this);
                    }

                }

            }
        } else if (requestCode == PhotoPicker.REQUEST_CODE) {
            setPics(data);
        } else if (requestCode == REQUEST_CODE_OTHER_MODIFY_INFO) {
            if (data != null) {
                modifyBean = (ModifyEnterpriseRequestBean) data.getExtras().getSerializable(AppConstant.SERIAL_KEY);
            }
            //

        }
    }

    //setpics
    //图片设置
    private void setPics(Intent data) {

        if (data != null) {
            selectedPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            picBeanList.clear();
            PicBean picBean;

            for (int i = 0; i < selectedPhotos.size(); i++) {
                picBean = new PicBean();
                picBean.setPhotoPath(selectedPhotos.get(i));
                //0 是+ 非0 是图片
                picBean.setStatus(1);
                picBeanList.add(picBean);
            }

            uploadPics(picBeanList);


        }
    }

    //upload pics


    private void uploadPics(final List<PicBean> picBeanList) {
        File file = new File(picBeanList.get(0).getPhotoPath());
        Observable<String> observable = new FileUploadApiControl().uploadSinglePic(file, this);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                //上传成功，显示照片
                JSONObject jsonObject = JSON.parseObject(s);
                String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (response.equals(AppConstant.JSON_SUCCESS)) {
                    String data = jsonObject.getString(AppConstant.JSON_DATA);
                    PicBean picBean = JSON.parseObject(data, PicBean.class);
                    if (picBean != null) {
                        imgPic.setVisibility(View.VISIBLE);
                        pictureUrl = picBean.getOriginalUrl();
                        pictureThumbnailUrl = picBean.getThumbnailUrl();
                        Glide.with(ModifyEnterpriseInfoActivity.this)
                                .load(baseUrl + pictureThumbnailUrl)
                                .into(imgPic);
                    }


                } else {
                    SimpleToast.toastMessage(getResources().getString(R.string.failed), Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };


        RxHelper.bindOnUI(observable, observer);

    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
       try{
           if (cursor != null) {
               // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
               // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
               int column_index = cursor
                       .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
               cursor.moveToFirst();
               return cursor.getString(column_index);

       }}catch (Exception e){
           e.printStackTrace();
       }finally {
           cursor.close();
       }
      return null;
    }


    //选择本地视频
    private void selectVedioFromLocal() {
        // Check whether user has granted read external storage permission to this activity.
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        // If not grant then require read external storage permission.
        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            String requirePermission[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(ModifyEnterpriseInfoActivity.this, requirePermission, REQUEST_CODE_READ_EXTERNAL_PERMISSION);
        } else {
            selectVideoFile();
        }
    }

    /* This method start get content activity to let user select video file from local directory.*/
    private void selectVideoFile() {
        // Create an intent with action ACTION_GET_CONTENT.
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        Intent selectVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);

        // Show video in the content browser.
        // Set selectVideoIntent.setType("*/*") to select all data
        // Intent for this action must set content type, otherwise you will encounter below exception : android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.GET_CONTENT }
        selectVideoIntent.setType("video/*");

        // Start android get content activity ( this is a android os built-in activity.) .
        startActivityForResult(selectVideoIntent, REQUEST_CODE_SELECT_VIDEO_FILE);
    }


    private void setVideoThumbnail(File file) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(),
                MediaStore.Video.Thumbnails.MINI_KIND);
        //获取图片后，压缩成指定大小
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 200);

        } else {
            //加载一个默认图片
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_default);
        }
        imgVedioThumbnail.setImageBitmap(bitmap);

    }


    private void takePics() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .setSelected(selectedPhotos)
                .start(ModifyEnterpriseInfoActivity.this);

    }


    public void showDialog(String str1, String str2) {
        dialog = new Dialog(this, R.style.BottomDialog);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_choose_role, null);
        tvSelectTwo = (TextView) inflate.findViewById(R.id.tv_register_company);
        tvSelectOne = (TextView) inflate.findViewById(R.id.tv_select_company);
        cancel = (TextView) inflate.findViewById(R.id.btn_cancel);
        tvSelectOne.setText(str1);
        tvSelectTwo.setText(str2);

        tvSelectTwo.setOnClickListener(this);
        tvSelectOne.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        // WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //获得window窗口的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        dialogWindow.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(inflate);
        dialog.show();
    }


    private void uploadFile(String type, final String path, UploadProgressListener uploadProgressListener) {
        fileVedio = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new FileUploadApiControl().uploadVedio(fileVedio, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(ModifyEnterpriseInfoActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);

                Loger.i("subFile" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    SimpleToast.toastMessage("上传成功", Toast.LENGTH_SHORT);
                    VedioUploadResponseBean bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), VedioUploadResponseBean.class);
                    frameVedio.setVisibility(View.VISIBLE);
                    tvAddVedio.setVisibility(View.GONE);
                    if (bean != null) {
                        videoUrl = bean.getOriginalUrl();

                        videoScreenshotsUrl = bean.getVideoPicuture();
                    }

                    setVideoThumbnail(fileVedio);
                    //  Bitmap bitmap = BitmapFactory.decodeFile(path);

                } else {
                    SimpleToast.toastMessage("上传失败，请重试", Toast.LENGTH_SHORT);
                }


            }
        };
        RxHelper.bindOnUI(observable, observer);
    }


    private void initTestData() {
        edtCompanyIntroduce.setText("testProfile");
    }

    private void chedata() {

        ModifyEnterpriseRequestBean requestBean = new ModifyEnterpriseRequestBean();
        if (modifyBean != null) {
            requestBean = modifyBean;
        }

        String companyIntroduceStr = edtCompanyIntroduce.getText().toString();
        String companyWebsiteStr = edtCompanyWebsite.getText().toString();
        String companyProduct = edtCompanyIProduct.getText().toString();
        String contactStr = edtContact.getText().toString();
        String contactWayStr = edtContactWay.getText().toString();

        requestBean.setCompanyProfile(companyIntroduceStr);

        //视频+照片
        requestBean.setId(commerceCompanyId);
        requestBean.setVideoUrl(videoUrl);
        requestBean.setVideoScreenshotsUrl(videoScreenshotsUrl);
        requestBean.setPictureThumbnailUrl(pictureThumbnailUrl);
        requestBean.setPictureUrl(pictureUrl);

        requestBean.setCompanyWebsite(companyWebsiteStr);
        requestBean.setMainProduct(companyProduct);
        requestBean.setLinkman(contactStr);
        requestBean.setContactInformation(contactWayStr);


        //其他项目
//            requestBean.setCompanyNature(modifyBean.getCompanyNature());
//            requestBean.setAddress(modifyBean.getAddress());
//            requestBean.setBank(modifyBean.getBank());
//            requestBean.setBankAccount(modifyBean.getBankAccount());
//
//            requestBean.setValueAddedTaxMethod(modifyBean.getValueAddedTaxMethod());
//            requestBean.setIncomeTaxCollectionMethod(modifyBean.getIncomeTaxCollectionMethod());
//            requestBean.setBillingMethod(modifyBean.getBillingMethod());
//            requestBean.setLegalName(modifyBean.getLegalName());
//            requestBean.setLegalIdNumber(modifyBean.getLegalIdNumber());


        submitModify(requestBean);

    }

    private void getData(String id) {
        Observable<EnterpriseInfoBean> observable = new ModifyEnterpriseInfoControl().getEnterpriseInfo(id);
        CommonDialogObserver<EnterpriseInfoBean> observer = new CommonDialogObserver<EnterpriseInfoBean>(this) {
            @Override
            public void onNext(EnterpriseInfoBean enterpriseInfoBean) {
                super.onNext(enterpriseInfoBean);
                if (enterpriseInfoBean != null) {
                    enterpriseInfoBeanGet = enterpriseInfoBean;
                    SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                    initData(enterpriseInfoBean);

                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(getResources().getString(R.string.failed), Toast.LENGTH_SHORT);

            }
        };
        RxHelper.bindOnUI(observable, observer);
    }

    private void initData(EnterpriseInfoBean enterpriseInfoBean) {
        edtCompanyIntroduce.setText(enterpriseInfoBean.getCompanyProfile());
        edtCompanyWebsite.setText(enterpriseInfoBean.getCompanyWebsite());
        edtCompanyIProduct.setText(enterpriseInfoBean.getMainProduct());
        edtContact.setText(enterpriseInfoBean.getLinkman());
        edtContactWay.setText(enterpriseInfoBean.getContactInformation());

        //vedio about
        videoUrl = enterpriseInfoBean.getVideoUrl();
        videoScreenshotsUrl = enterpriseInfoBean.getVideoScreenshotsUrl();
        //pic
        pictureThumbnailUrl = enterpriseInfoBean.getPictureThumbnailUrl();
        pictureUrl = enterpriseInfoBean.getPictureUrl();

        //照片缩略图不为空
        if (!TextUtils.isEmpty(pictureThumbnailUrl)) {
            imgPic.setVisibility(View.VISIBLE);
            Glide.with(ModifyEnterpriseInfoActivity.this)
                    .load(baseUrl + pictureThumbnailUrl)
                    .into(imgPic);
        }

        //视频地址不为空
        if (!TextUtils.isEmpty(videoUrl)) {
            //
            frameVedio.setVisibility(View.VISIBLE);

            Glide.with(ModifyEnterpriseInfoActivity.this)
                    .load(baseUrl + videoScreenshotsUrl)
                    .into(imgVedioThumbnail);

        }

    }


    private void submitModify(ModifyEnterpriseRequestBean requestBean) {
        Observable<Boolean> observable = new ModifyEnterpriseInfoControl().submitModifyInfo(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUI(observable, observer);

    }

    @Override
    public void onProgress(long currentBytesCount, long totalBytesCount) {

    }
}
