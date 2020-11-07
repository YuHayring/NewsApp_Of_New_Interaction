package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;

/**
 * @author hayring
 * @date 2020/11/6 16:26
 * 关注关系
 */
public class Follow implements Serializable {

    /**
     * 关注者 id
     */
    private String followerId;

    /**
     * 被关注者 id
     */
    private String authorId;

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
