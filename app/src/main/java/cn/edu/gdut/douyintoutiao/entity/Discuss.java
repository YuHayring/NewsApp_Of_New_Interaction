package cn.edu.gdut.douyintoutiao.entity;

import java.time.LocalDateTime;

/**
 * @author hayring
 * @date 2020/11/6 16:30
 * 评论
 */
public class Discuss {

    /**
     * 资讯 id
     */
    private String newsId;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 用户 id
     */
    private String userId;

    /**
     * 评论内容
     */
    private String text;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
