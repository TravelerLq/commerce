package com.yuas.commerce.bean;



import java.util.List;

/**
 * Created by liqing on 2018/12/24.
 */

public class LoginCommerceResponseBean extends BaseBean {


    /**
     * id : 2
     * username : 000
     * userpwd : 123456
     * nickname : 管理员
     * sex : 1
     * photo : ../../images/user.jpg
     * roleId : 3
     * state : null
     * integral : 135
     * coname : 南京市滁州商会
     * coid : 4
     * position : 副会长
     * createtime : 1538982939000
     * updatetime : 1543289793000
     * jointime :
     * address :
     * costtime : 2018-10-03
     * costSum :
     * referrer : 1
     * resume : 111
     * sonCoid : 6
     * identity :
     * birthtime : 2018-10-03
     * actIntegral : 20
     * busId : 1
     * bussinessVO : [{"id":1,"commerceName":"南京滁州商会"}]
     */

    private String id;
    private String username;
    private String userpwd;
    private String nickname;
    private int sex;
    private String photo;
    private String roleId;
    private Object state;
    private int integral;
    private String coname;
    private String coid;
    private String position;
    private long createtime;
    private long updatetime;
    private String jointime;
    private String address;
    private String costtime;
    private String costSum;
    private String referrer;
    private String resume;
    private int sonCoid;
    private String identity;
    private String birthtime;
    private int actIntegral;
    private int busId;
    private List<BussinessVOBean> bussinessVO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getConame() {
        return coname;
    }

    public void setConame(String coname) {
        this.coname = coname;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCosttime() {
        return costtime;
    }

    public void setCosttime(String costtime) {
        this.costtime = costtime;
    }

    public String getCostSum() {
        return costSum;
    }

    public void setCostSum(String costSum) {
        this.costSum = costSum;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getSonCoid() {
        return sonCoid;
    }

    public void setSonCoid(int sonCoid) {
        this.sonCoid = sonCoid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public int getActIntegral() {
        return actIntegral;
    }

    public void setActIntegral(int actIntegral) {
        this.actIntegral = actIntegral;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public List<BussinessVOBean> getBussinessVO() {
        return bussinessVO;
    }

    public void setBussinessVO(List<BussinessVOBean> bussinessVO) {
        this.bussinessVO = bussinessVO;
    }



}


