package com.yuas.commerce.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.commerce.bean.EnterpriseInfoBean;
import com.yuas.commerce.bean.ModifyEnterpriseRequestBean;
import com.yuas.commerce.constant.AppConstant;
import com.yuas.commerce.exception.IApiException;
import com.yuas.commerce.network.api.ModifyEnterpriseInfoApi;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 2018/12/19.
 */

public class ModifyEnterpriseInfoControl extends BaseControl {


    //提交配置好的权限

    public Observable<EnterpriseInfoBean> getEnterpriseInfo(String id) {

        Retrofit retrofit = buildRetrofitWithParams();
        ModifyEnterpriseInfoApi api = retrofit.create(ModifyEnterpriseInfoApi.class);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

//            params.put("userId", bean.getUserId());
//            params.put("resourcesId", bean.getResourcesId());
//            params.put("commerceId", bean.getCommerceId());
        Observable observable = api.getEnterpriseInfo(params);


        return observable.map(new Function<Response<String>, EnterpriseInfoBean>() {
            @Override
            public EnterpriseInfoBean apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (AppConstant.JSON_SUCCESS.equals(status)) {
                    EnterpriseInfoBean bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), EnterpriseInfoBean.class);

                    return bean;
                }
                JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                throw new IApiException("公司简介获取", jsonObject1.getString(AppConstant.JSON_MSG));
            }
        });

    }

    //提交配置好的权限
    /*

        "id": 501,
        "companyProfile": "111",
        "videoUrl": null,
        "videoScreenshotsUrl": null,
        "pictureUrl": null,
        "pictureThumbnailUrl": null,
        "companyWebsite": null,
        "mainProduct": null,
        "linkman": null,
        "contactInformation": null,
        "companyName": "中山园林管理局",
        "valueAddedTaxMethod": "一般纳税人",
        "taxNumber": "无",
        "bank": null,
        "bankAccount": null,
        "address": null,
        "billingMethod": "增值税普通发票",
        "legalName": null,
        "legalIdNumber": null,
        "companyNature": "有限责任公司",
        "incomeTaxCollectionMethod": "查账征收"
    }
     */

    public Observable<Boolean> submitModifyInfo(ModifyEnterpriseRequestBean bean) {
        Observable observable = null;
        Retrofit retrofit = builderJsonHeader2();
        ModifyEnterpriseInfoApi api = retrofit.create(ModifyEnterpriseInfoApi.class);
        Map<String, Object> params = new HashMap<>();

        params.put("id", bean.getId());
        params.put("companyProfile", bean.getCompanyProfile());

        params.put("videoUrl", bean.getVideoUrl());
        params.put("videoScreenshotsUrl", bean.getVideoScreenshotsUrl());
        params.put("pictureUrl", bean.getPictureUrl());
        params.put("pictureThumbnailUrl", bean.getPictureThumbnailUrl());

        params.put("companyWebsite", bean.getCompanyWebsite());
        params.put("mainProduct", bean.getMainProduct());
        params.put("linkman", bean.getLinkman());
        params.put("contactInformation", bean.getContactInformation());
        params.put("valueAddedTaxMethod", bean.getValueAddedTaxMethod());
        params.put("taxNumber", bean.getTaxNumber());

        params.put("bank", bean.getBank());
        params.put("bankAccount", bean.getBankAccount());
        params.put("address", bean.getAddress());
        params.put("billingMethod", bean.getBillingMethod());
        params.put("legalName", bean.getLegalName());
        params.put("legalIdNumber", bean.getLegalIdNumber());
        params.put("companyNature", bean.getCompanyNature());
        params.put("incomeTaxCollectionMethod", bean.getIncomeTaxCollectionMethod());

        String paramsStr = JSON.toJSONString(params);
        observable = api.submitModifyInfo(paramsStr);


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
                throw new IApiException("公司信息修改", jsonObject1.getString(AppConstant.JSON_MSG));
            }
        });

    }

}

