package com.yuas.commerce.constant;

import android.os.Environment;
import android.text.TextUtils;

/**
 * Created by Victor on 11/3/2017.
 */

public class AppConstant {
    /**
     * 水平ListView 分页的每页数据大小
     */
    public static final int HZ_VEWPAGE_EVERY_PAGE_NO = 8;
    /**
     * 快捷扫描成功结果
     */
    public static final int SCAN_RESULT_OK = 200;

    /**
     * 快捷扫描失败结果
     */

    public static final int SCAN_RESULT_FAILED = 201;

    public static final String SERIAL_KEY = "serial_key";
    public static final String INT_KEY = "int_key";

    public static final int REQUEST_CODE = 1100;

    /**
     * 水平ListView 分页的每页数据大小
     */
    public static final int HZ_SEND_VEWPAGE_EVERY_PE_NAGO = 9;
    //http://192.168.1.114:8080/yuanshen/public/login
    //http://192.168.1.100:8080/shuidao/chk/listrelas192.168.1.88
    // public static final String BASE_URL = "http://180.111.35.99:8080/";

    public static final String BASE__DOWN_URL = "http://192.168.31.109:8080/shuidao/form/down?id=";
    //https://cs.royalsecurity.cn/shuidao/notoken/login

    //testhttp://192.168.31.86/
    //public static final String BASE_URL = "http://192.168.31.86:8080/";

    // public static final String BASE_URL = "http://192.168.31.86:8088/";


    //财商道-https://cs.royalsecurity.cn/testAuth/正式环境地址 Ip url
    public static final String BASE_URL = "http://101.200.85.207:8080/";

    //琢税鸟 正式
    private static final String BASE_URL_PECKER = "https://cs.royalsecurity.cn/";
    public static final String BASE_URL_PIC="https://cs.royalsecurity.cn/production/";
    //琢税鸟 测试
  //  private static final String BASE_URL_PECKER = "http://192.168.31.195:8080/";

    //  private static final String URL_TEST = "http://10.90.65.209:8084/";//http://10.126.211.7/";
    private static final String URL_PRD = "http://10.90.65.209:8082/";//"http://10.126.211.142/";//"http://10.90.65.209:8082/";"10.126.211.7";
    public static final String OUTSIZE_URL = "http://10.90.65.209:8088/";
    // // .baseUrl(AppConstant.getURL())http://192.168.31.86:8080/

    private static final String URL_TEST = "http://192.168.31.86:8080/";

    //
    private static final String URL_BASE_COMMERCE = "https://cs.royalsecurity.cn/testAuth/";

    private static final String URL_BASE_COMMERCE_RESOURCE = "https://cs.royalsecurity.cn/";

    private static final String URL_TEST_COMMERCE = "http://192.168.31.86:8088/";
//    public static final String SPARK_RA_URL = "spark_ra_url";
//    public static final String APP_KEY = "appkey";
//    public static final String APP_SECRET = "appsecret";
//    public static final String TEMPLATE = "template";
//    public static final String SP_NAME = "cn.unitid.shared.preference";

    /**
     * spark 注册中心的地址
     * http://222.190.151.230:9122/ra/
     * http://222.190.151.230:9122/ra/
     */
    // public static final String SPARK_RA_URL ="http://sparkra.syan.com.cn/ra";
    public static final String SPARK_RA_URL = "http://222.190.151.230:9122/ra";
    /**
     * app key
     * abd4282357f84f0aaf997831668fc435
     */
    // public static final String APP_KEY="4a2cf89d31fa474fa25b29b81658c515";
    public static final String APP_KEY = "abd4282357f84f0aaf997831668fc435";

    /**
     * app secret
     * <p>
     * lhApAcleZ8qE
     */
    // public static final String APP_SECRET="97bpkZi9Zh7v";
    public static final String APP_SECRET = "lhApAcleZ8qE";
    public static final String TEMPLATE = "eabcfbe2857d41d8866e6e74f1ddf6c1";

    public static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/caishangdao";


    public static String TEMP_URL = "";//http://122.97.247.131:8100/

    public static String getURL() {
        String url = "";
        if (!TextUtils.isEmpty(TEMP_URL)) {
            return TEMP_URL;
        }
        if (MySpEdit.getInstance().getAppEmv()) {
            url = URL_PRD;
        } else {
            url = URL_TEST;
        }
        url = BASE_URL;
        return url;
    }

    public static String getCommerceURL() {
        return URL_BASE_COMMERCE;
    }

    public static String getPeckerURL() {
        return BASE_URL_PECKER;
    }

    public static String getCommerceUrlResource() {
        return URL_BASE_COMMERCE_RESOURCE;
    }

    public static String getTestURL() {
        String url = "";
        if (!TextUtils.isEmpty(URL_TEST)) {
            return URL_TEST;
        }

        url = URL_TEST;
        return url;
    }

    public static final String JSON_STATUS = "Status";
    public static final String JSON_CODE = "code";
    public static final String JSON_DATA = "data";
    public static final String JSON_MSG = "msg";
    public static final String JSON_SUCCESS_STATUS = "200";
    public static final String JSON_SUCCESS_REPEAT_STATUS = "203";
    public static final String JSON_RESPONSE = "response";
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_ERROE = "error";
    public static final String JSON_MESSAGE = "message";


    public static final String NOTICE_ACTION = "notice_unkown_action";

    public static final int QUCIK_CLICK = 1000;


    /**
     * 存料列表
     */
    public static final int MATERIAL_WAIT = 1;//待存
    public static final int MATERIAL_PROGRESS = 2;//进行中
    public static final int MATERIAL_DONE = 3;//已完成

    /**
     * 公司信息更新
     */
    public static final String UPDATE = "update";//
    public static final String NEW = "new";
    public static final String NEW_LOGIN = "new_login";//已完成

    public static final String KEY_USER = "user";

    public static final String KEY_ENTERPRISE = "enterprise";
    public static final String KEY_ENTERPRISE_LIST = "enterprise_list";

    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_DEPARTMENT_LIST = "department_list";

    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CATEGORY_LIST = "category_list";

    public static final String KEY_REIM_ITEM = "reim_item";
    public static final String KEY_REIM_ITEM_LIST = "reim_item_list";

    public static final String KEY_TYPE = "type";
    public static final String KEY_TYPE_ANALYZE = "type_analyze";
    public static final String VALUE_AJUST = "ajust";
    public static final String VALUE_ADD = "add";
    public static final String VALUE_BUDGET_DEPART = "depart";
    public static final String VALUE_BUDGET_CATEGORY = "category";
    public static final String VALUE_SUBJECT_TYPE = "subject_type";


}
