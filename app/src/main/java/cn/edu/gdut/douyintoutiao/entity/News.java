package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;

/**
 * @author hayring
 * @date 2020/11/6 16:15
 * 资讯实体
 */
@Deprecated
public class News implements Serializable {
    
    //文字
    public static final int TEXT = 0;
    
    //视频
    public static final int VIDEO = 1;
    
    
    

    /**
     * 资讯 id
     */
    private String newsId;

    /**
     * 资讯名称
     */
    private String newsName;

    /**
     * 资讯类型
     */
    private int newsType;

    /**
     * 资讯数据
     */
    private String newsUrl;

    /**
     *
     */
    private String tagId;


    private String userId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
