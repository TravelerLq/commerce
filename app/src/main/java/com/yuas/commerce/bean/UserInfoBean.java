package com.yuas.commerce.bean;

/**
 * Created by liqing on 2018/12/4.
 */


public class UserInfoBean extends BaseBean {

        /**
         * birthday : 1996-10-10 00:00:00
         * createTime : null
         * dpt : 1
         * email : 1484951857@qq.com
         * headImg : http://localhost:8080/dcim/image/headimg/2173bfaafa97461a91be124c316af167.jpg
         * headImgThumb : http://localhost:8080/dcim/image/headimg/thumb_2173bfaafa97461a91be124c316af167.jpg
         * idNumber : 320682199610101769
         * phone : 13770930779
         * realname : 丁薇
         * role : 1
         * sex : 0
         * updateTime : null
         */

        private String birthday;
        private Object createTime;
        private int dpt;
        private String email;
        private String headImg;
        private String headImgThumb;
        private String idNumber;
        private String phone;
        private String realname;
        private int role;
        private String sex;
        private Object updateTime;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getDpt() {
            return dpt;
        }

        public void setDpt(int dpt) {
            this.dpt = dpt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getHeadImgThumb() {
            return headImgThumb;
        }

        public void setHeadImgThumb(String headImgThumb) {
            this.headImgThumb = headImgThumb;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

}
