package com.yuas.commerce.bean;


/**
 * Created by liqing on 17/12/4.
 */

public class HomeAuthorityBean extends BaseBean {

    /**
     * name : RevenueMaterial
     * hasAuthority : true
     */

    private String name;
    private String hasAuthority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHasAuthority() {
        return hasAuthority;
    }

    public void setHasAuthority(String hasAuthority) {
        this.hasAuthority = hasAuthority;
    }
}
