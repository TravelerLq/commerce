package com.yuas.commerce.bean;



/**
 * Created by liqing on 2018/12/18.
 */

public class AuthorityItemBean extends BaseBean {


    /**
     * id : 7
     * resourcesName : 查看优秀企业详情
     * resourcesIcon : ===
     * resourcesChecked : false
     * resourcesVoList : null
     */

    private String id;
    private String resourcesName;
    private String resourcesIcon;
    private boolean resourcesChecked;
    private Object resourcesVoList;

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

    public Object getResourcesVoList() {
        return resourcesVoList;
    }

    public void setResourcesVoList(Object resourcesVoList) {
        this.resourcesVoList = resourcesVoList;
    }
}
