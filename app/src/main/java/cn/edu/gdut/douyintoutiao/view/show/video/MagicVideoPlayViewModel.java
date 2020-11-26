package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;

/**
 * @author hayring
 * @date 11/26/20 11:38 PM
 */
public class MagicVideoPlayViewModel {

    VideoPlayerModel videoPlayerModel = VideoPlayerModel.getInstance();


    VideoPlayerFragment videoPlayerFragment;


    public MagicVideoPlayViewModel(VideoPlayerFragment videoPlayerFragment) {
        this.videoPlayerFragment = videoPlayerFragment;
    }

    LinkedList<MyNews> upNewses = new LinkedList<>();
    /**
     * 更新上滑的视频
     */
    void getUpVideo() {

    }





    LinkedList<MyNews> downNewses = new LinkedList<>();
    /**
     * 更新下滑的视频
     */
    void getDownVideo() {

    }


    LinkedList<MyNews> leftNewses = new LinkedList<>();
    /**
     * 更新左滑的视频
     */
    void getLeftVideo() {

    }

    LinkedList<MyNews> rightNewses = new LinkedList<>();
    /**
     * 更新右滑的视频
     */
    void getRightVideo() {

    }



    void getVideoTest() {
        videoPlayerModel.getUpVideoNews(handler);
    }

    private TestHandler handler = new TestHandler(Looper.myLooper());

    private class TestHandler extends Handler {

        public TestHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.arg1 == 200) {
                List<MyNews> data = (List<MyNews>) msg.obj;
                upNewses.addAll(data);
                downNewses.addAll(data);
                leftNewses.addAll(data);
                rightNewses.addAll(data);

                videoPlayerFragment.setMyNews(upNewses.pop());
            }
        }
    }





}
