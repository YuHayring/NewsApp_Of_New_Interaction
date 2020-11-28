package cn.edu.gdut.douyintoutiao.view.show.video.videolist;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.video.VideoModel;
import cn.edu.gdut.douyintoutiao.view.show.video.VideoViewModel;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 * @date 11/29/20 3:28 AM
 */
public class VideoListViewModel extends VideoViewModel {

    public VideoListViewModel() {}

    public VideoListViewModel(Activity activity) {
        super(activity);
    }


    private static final int PAGE_COUNT = 16;

    VideoModel videoModel = VideoModel.getInstance();


    MutableLiveData<List<MyNews>> newsList = new MutableLiveData<>();


    void getVideoList() {
        videoModel.getMoreVideoNews(0, PAGE_COUNT, videoListGotCallBack);
    }

    /**
     * 获取视频
     * @param index 起始位置
     */
    void getMoreVideoList(int index) {
        videoModel.getMoreVideoNews(index, PAGE_COUNT, videoListGotCallBack);
    }



    CommonVideoGotCallBack videoListGotCallBack = new CommonVideoGotCallBack() {
        @Override
        protected void onVideoGotSuccess(List<MyNews> newses) {
             newsList.postValue(newses);
        }

        @Override
        protected void onVideoNotExist() {
            Toasty.info(activity, R.string.video_play_no_more).show();
        }

        @Override
        protected void onRequestError(int errCode) {
            Toasty.error(activity, activity.getString(R.string.video_play_request_fail) + errCode).show();
        }
    };




    public MutableLiveData<List<MyNews>> getNewsList() {
        return newsList;
    }
}
