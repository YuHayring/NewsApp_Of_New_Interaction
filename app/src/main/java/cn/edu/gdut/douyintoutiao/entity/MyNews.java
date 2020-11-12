package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/11 20:24
 */
public class MyNews implements Serializable {
    /**
     * 资讯 id
     */
    private String _id;

    /**
     * 资讯标题
     */
    private String newsName;

    /**
     * 资讯概要
     */
    private String newsAbstract;

    /**
     * 资讯图片
     */
    private String newsPhotoUrl;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 更新时间
     */
    private String updatedAt;

    private String newsDetailUrl;

    public MyNews() {
    }

    public MyNews(String _id, String newsName, String newsAbstract, String newsPhotoUrl, String createdAt, String updatedAt, String newsDetailUrl) {
        this._id = _id;
        this.newsName = newsName;
        this.newsAbstract = newsAbstract;
        this.newsPhotoUrl = newsPhotoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.newsDetailUrl = newsDetailUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsAbstract() {
        return newsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        this.newsAbstract = newsAbstract;
    }

    public String getNewsPhotoUrl() {
        return newsPhotoUrl;
    }

    public void setNewsPhotoUrl(String newsPhotoUrl) {
        this.newsPhotoUrl = newsPhotoUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNewsDetailUrl() {
        return newsDetailUrl;
    }

    public void setNewsDetailUrl(String newsDetailUrl) {
        this.newsDetailUrl = newsDetailUrl;
    }

    @Override
    public String toString() {
        return "MyNews{" +
                "_id='" + _id + '\'' +
                ", newsName='" + newsName + '\'' +
                ", newsAbstract='" + newsAbstract + '\'' +
                ", newsPhotoUrl='" + newsPhotoUrl + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", newsDetailUrl='" + newsDetailUrl + '\'' +
                '}';
    }
}
