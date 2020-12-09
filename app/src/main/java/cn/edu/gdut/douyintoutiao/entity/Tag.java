package cn.edu.gdut.douyintoutiao.entity;

import java.io.Serializable;

/**
 * @author hayring
 * @date 2020/11/6 16:13
 * 事件实体(tag)
 */
@Deprecated
public class Tag implements Serializable {

    /**
     * 事件 id
     */
    private String tagId;

    /**
     * 事件名称
     */
    private String tagName;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
