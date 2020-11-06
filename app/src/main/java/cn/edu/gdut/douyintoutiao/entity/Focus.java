package cn.edu.gdut.douyintoutiao.entity;

/**
 * @author hayring
 * @date 2020/11/6 16:27
 * 关注资讯
 */
public class Focus {

    /**
     * 关注者 Id
     */
    private String followerId;

    /**
     * 资讯 id
     */
    private String tagId;

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
