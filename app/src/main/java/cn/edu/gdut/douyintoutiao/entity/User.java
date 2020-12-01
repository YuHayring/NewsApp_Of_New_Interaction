package cn.edu.gdut.douyintoutiao.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * @author hayring
 * @date 2020/11/6 16:04
 * 用户实体
 */
public class User implements Serializable {

    /**
     * 用户id
     */
    @SerializedName("_id")
    private String userId;

    /**
     * 用户名
     */
    @SerializedName("name")
    private String userName;

    /**
     * 电话号码
     */
    private String userTelephone;

    private String userPassword;
    /**
     * 加密后的密码
     */
    @SerializedName("RsaPassword")
    private byte[] RsaPassword;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 地区
     */
    private String userLocation;

    /**
     * 个人简介
     */
    private String userDescription;

    /**
     * 背景图片
     */
    @Deprecated
    private byte[] userBackground;

    /**
     * 用户头像
     * author: @dengJL
     * data: 11/18
     */
    private String userImageUrl;

    /**
     * 创建时间
     */
    @SerializedName("createdAt")
    private Date createdTime;

    /**
     * 修改时间
     */
    @SerializedName("updatedAt")
    private Date updatedTime;


    /**
     * 关注 tabs
     */
    private int tabs;


    public User() {
    }

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

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public byte[] getRsaPassword() {
        return RsaPassword;
    }

    public void setRsaPassword(byte[] rsaPassword) {
        RsaPassword = rsaPassword;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public byte[] getUserBackground() {
        return userBackground;
    }

    public void setUserBackground(byte[] userBackground) {
        this.userBackground = userBackground;
    }


    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getTabs() {
        return tabs;
    }

    public void setTabs(int tabs) {
        this.tabs = tabs;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userTelephone='" + userTelephone + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", RsaPassword=" + RsaPassword +
                ", userAge=" + userAge +
                ", userLocation='" + userLocation + '\'' +
                ", userDescription='" + userDescription + '\'' +
                ", userBackground=" + Arrays.toString(userBackground) +
                ", userImageUrl='" + userImageUrl + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
