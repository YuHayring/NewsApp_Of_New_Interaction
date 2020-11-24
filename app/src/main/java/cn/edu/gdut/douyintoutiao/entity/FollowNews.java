package cn.edu.gdut.douyintoutiao.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FollowNews implements Serializable {

    @SerializedName("_id")
    private String followNewsId;

    @SerializedName("objectId_news")
    private List<MyNews> followNews;

    public FollowNews(String followNewsId, List< MyNews > followNews) {
        this.followNewsId = followNewsId;
        this.followNews = followNews;
    }

    public String getFollowNewsId() {
        return followNewsId;
    }

    public void setFollowNewsId(String followNewsId) {
        this.followNewsId = followNewsId;
    }

    public List< MyNews > getFollowNews() {
        return followNews;
    }

    public void setFollowNews(List< MyNews > followNews) {
        this.followNews = followNews;
    }

    @Override
    public String toString() {
        return "FollowNews{" +
                "followNewsId='" + followNewsId + '\'' +
                ", followNews=" + followNews +
                '}';
    }
}
