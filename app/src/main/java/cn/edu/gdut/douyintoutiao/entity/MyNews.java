package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/11 20:24
 */
public class MyNews implements Serializable {

    public static final int TEXT = 0;

    public static final int VIDEO = 1;



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

    /**
     * 新闻具体的uri
     */
    private String newsDetailUrl;

    /**
     * 新闻类型
     */
    private Integer type;

    /**
     * 作者
     */
    private List<User> author;


    /**
     * 新闻tag
     */

    private String tag;


    /**
     * 喜欢数
     */
    private Integer like;



    /**
     * 视频转文字专用 url
     */
    private String urlOfTextOfVideo;


    public MyNews() {
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
        if(!newsAbstract.isEmpty() ){ return newsAbstract;}
        else {
            return ("这个作者很懒，没有提供概述。");
        }
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
                ", type=" + type +
                ", author=" + author +
                ", tag='" + tag + '\'' +
                ", like=" + like +
                '}';
    }

    public String getTag() {
        return tag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<User> getAuthor() {
        return author;
    }

    public void setAuthor(List<User> author) {
        this.author = author;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyNews myNews = (MyNews) o;
        return Objects.equals(_id, myNews._id) &&
                Objects.equals(newsName, myNews.newsName) &&
                Objects.equals(newsAbstract, myNews.newsAbstract) &&
                Objects.equals(newsPhotoUrl, myNews.newsPhotoUrl) &&
                Objects.equals(createdAt, myNews.createdAt) &&
                Objects.equals(updatedAt, myNews.updatedAt) &&
                Objects.equals(newsDetailUrl, myNews.newsDetailUrl) &&
                Objects.equals(type, myNews.type) &&
                Objects.equals(author, myNews.author) &&
                Objects.equals(tag, myNews.tag) &&
                Objects.equals(like, myNews.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, newsName, newsAbstract, newsPhotoUrl, createdAt, updatedAt, newsDetailUrl, type, author, tag, like);
    }
    
    public String getUrlOfTextOfVideo() {
        return urlOfTextOfVideo;
    }

    public void setUrlOfTextOfVideo(String urlOfTextOfVideo) {
        this.urlOfTextOfVideo = urlOfTextOfVideo;
    }
}
