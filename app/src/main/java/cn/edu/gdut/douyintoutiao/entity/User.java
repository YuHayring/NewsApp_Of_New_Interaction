package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;

/**
 * @author hayring
 * @date 2020/11/6 16:04
 * 用户实体
 */
public class User implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 电话号码
     */
    private String userTelephone;

    /**
     * 加密后的密码
     */
    private String userPassword;

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
    private byte[] userBackground;


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
}
