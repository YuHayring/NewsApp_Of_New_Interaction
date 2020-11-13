package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;

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
    private String _id;

    /**
     * 被关注者 id
     */
    private String authorId;

    public Follow(String _id, String authorId) {
        this._id = _id;
        this.authorId = authorId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Follow() {
        this._id = _id;
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "_id='" + _id + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}
