package com.yuas.commerce.bean;


import java.util.List;

/**
 * Created by liqing on 2018/12/19.
 */

public class AuthorityResponseBean extends BaseBean {


    /**
     * id : 1
     * resourcesName : 优秀企业展示
     * resourcesIcon : ===
     * resourcesChecked : false
     * resourcesVoList : [{"id":7,"resourcesName":"查看优秀企业详情","resourcesIcon":"===","resourcesChecked":false,"resourcesVoList":null}]
     */

    private String id;
    private String resourcesName;
    private String resourcesIcon;
    private boolean resourcesChecked;

    private List<AuthorityItemBean> resourcesVoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourcesName() {
        return resourcesName;
    }

    public void setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
    }

    public String getResourcesIcon() {
        return resourcesIcon;
    }

    public void setResourcesIcon(String resourcesIcon) {
        this.resourcesIcon = resourcesIcon;
    }

    public boolean isResourcesChecked() {
        return resourcesChecked;
    }

    public void setResourcesChecked(boolean resourcesChecked) {
        this.resourcesChecked = resourcesChecked;
    }

    public List<AuthorityItemBean> getResourcesVoList() {
        return resourcesVoList;
    }

    public void setResourcesVoList(List<AuthorityItemBean> resourcesVoList) {
        this.resourcesVoList = resourcesVoList;
    }
}
