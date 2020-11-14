package cn.edu.gdut.douyintoutiao.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hayring
 * @date 2020/11/6 16:30
 * 评论
 */
public class Discuss implements Serializable {

    /**
     * 主键id
     */

    @SerializedName("_id")
    private String commentId;

    /**
     * 资讯 id
     */
    private String newsId;

    /**
     * 插入时间
     */
    @SerializedName("createdAt")
    private LocalDateTime time;

    /**
     * 更新时间
     */
    @SerializedName("updatedAt")
    private LocalDateTime updateTime;


    /**
     * 评论内容
     */
    @SerializedName("content")
    private String text;

    /**
     * 用户个人资料
     */
    private User user;

    public Discuss(String commentId, String newsId, LocalDateTime time, LocalDateTime updateTime, String text, User user) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.time = time;
        this.updateTime = updateTime;
        this.text = text;
        this.user = user;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
