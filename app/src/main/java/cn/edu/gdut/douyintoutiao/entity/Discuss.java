package cn.edu.gdut.douyintoutiao.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @SerializedName("news")
    private List<MyNews> news;

    /**
     * 插入时间
     */
    @SerializedName("createdAt")
    private Date time;

    /**
     * 更新时间
     */
    @SerializedName("updatedAt")
    private Date updateTime;


    /**
     * 评论内容
     */
    @SerializedName("content")
    private String text;

    /**
     * 用户个人资料
     */
    @SerializedName("user")
    private List<User> user;

    public Discuss() {
    }

    public List<MyNews> getNews() {
        return news;
    }

    public List<User> getUser() {
        return user;
    }

    public String getCommentId() {
        return commentId;
    }

    public Date getTime() {
        return time;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return "Discuss{" +
                "commentId='" + commentId + '\'' +
                ", news=" + news +
                ", time=" + time +
                ", updateTime=" + updateTime +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }
}
