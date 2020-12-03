package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

/**
 * @author hayring
 * @date 11/27/20 4:04 PM
 */
public class VideoViewModel extends ViewModel {


    private VideoModel videoModel = VideoModel.getInstance();

    public VideoViewModel() {}

    public VideoViewModel(Activity activity) {
        this.activity = activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    protected Activity activity;

    public void insertTagsFollowByNewsIdUserId(String newsId, String userId){
        videoModel.insertTagsFollowByNewsIdUserId(newsId,userId);
    }

    public void deleteTagsFollowByNewsIdUserId (String newsId, String userId){
        videoModel.deleteTagsFollowByNewsIdUserId(newsId, userId);
    }


}
