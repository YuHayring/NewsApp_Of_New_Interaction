package cn.edu.gdut.douyintoutiao.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author hayring
 * @date 2020/11/6 16:26
 * 关注关系
 *
 * @author  dengJL
 * @data  2020/11/12
 * update:增加属性
 */
public class Follow implements Serializable {


    /**
     * 新增_id
     */
    @SerializedName("_id")
    private String followId;

    /**
     * 关注者
     */
    @SerializedName("objectId_follower")
    private List<User> follower;


    /**
     * 被关注者
     */
    @SerializedName("objectId_author")
    private List<User> author;

    public Follow(String followId, List< User > follower, List< User > author) {
        this.followId = followId;
        this.follower = follower;
        this.author = author;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public List< User > getFollower() {
        return follower;
    }

    public void setFollower(List< User > follower) {
        this.follower = follower;
    }

    public List< User > getAuthor() {
        return author;
    }

    public void setAuthor(List< User > author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followId='" + followId + '\'' +
                ", follower=" + follower +
                ", author=" + author +
                '}';
    }
}
