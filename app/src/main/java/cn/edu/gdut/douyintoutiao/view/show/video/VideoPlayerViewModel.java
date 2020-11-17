package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.News;

/**
 * @author hayring
 * @date 2020/11/7 16:09
 */
public class VideoPlayerViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public interface OnVideoGotCallBack {
        void onSuccess(List<News> newses);

        void onFaile(String errorInfo);
    }

    public FullscreenActivity activity;

    private VideoPlayerModel videoPlayerModel;


    private OnVideoGotCallBack videoGotCallBack = new OnVideoGotCallBack() {
        @Override
        public void onSuccess(List<News> newses) {
            activity.getAdapter().addAllAndNotify(newses);
        }

        @Override
        public void onFaile(String errorInfo) {

        }
    };

    public VideoPlayerViewModel(FullscreenActivity activity) {
        this.activity = activity;
    }

    public void setVideoPlayerModel(VideoPlayerModel videoPlayerModel) {
        this.videoPlayerModel = videoPlayerModel;
        videoPlayerModel.setOnVideoGotHandler(videoGotHandler);
    }


    private Handler videoGotHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<News> newses = (List)msg.obj;
            activity.getAdapter().addAllAndNotify(newses);
        }
    };


}