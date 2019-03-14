package com.yuas.commerce.bean;



/**
 * Created by liqing on 2018/12/19.
 */

public class PersonBean extends BaseBean {


    /**
     * userId : 581
     * userName : 陈明坤
     * commerceId : 1
     */

    private String userId;
    private String userName;
    private int commerceId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(int commerceId) {
        this.commerceId = commerceId;
    }
}
