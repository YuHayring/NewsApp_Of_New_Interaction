package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;

/**
 * @author hayring
 * @date 11/26/20 11:38 PM
 */
public class MagicVideoViewModel extends VideoViewModel {

    /**
     * 标题
     */
    LiveData<String> currentNews = new MutableLiveData<>();




    VideoModel videoModel = VideoModel.getInstance();



    public MagicVideoViewModel(){}

    public MagicVideoViewModel(Activity activity) {
        super(activity);
    }




    int upVideoIndex;

    LinkedList<MyNews> upNewses = new LinkedList<>();
    /**
     * 更新上滑的视频
     */
    void getUpVideo() {
        videoModel.getTagVideoNews("学习",upVideoGot);
    }
    CommonVideoGotCallBack upVideoGot = new  CommonVideoGotCallBack() {
        @Override
        protected void onVideoGotSuccess(List<MyNews> newses) {
            upNewses.addAll(newses);

        }

        @Override
        protected void onVideoNotExist() {

        }
    };





    LinkedList<MyNews> downNewses = new LinkedList<>();
    /**
     * 更新下滑的视频
     */
    void getDownVideo() {
        videoModel.getVideoNews(downVideoGot);
    }

    CommonVideoGotCallBack downVideoGot = new  CommonVideoGotCallBack() {
        @Override
        protected void onVideoGotSuccess(List<MyNews> newses) {
            downNewses.addAll(newses);
        }

        @Override
        protected void onVideoNotExist() {

        }
    };


    LinkedList<MyNews> leftNewses = new LinkedList<>();
    /**
     * 更新左滑的视频
     */
    void getLeftVideo() {
        videoModel.getTagVideoNews("csgo",leftVideoGot);
    }
    CommonVideoGotCallBack leftVideoGot = new  CommonVideoGotCallBack() {
        @Override
        protected void onVideoGotSuccess(List<MyNews> newses) {
            leftNewses.addAll(newses);
        }

        @Override
        protected void onVideoNotExist() {

        }
    };

    LinkedList<MyNews> rightNewses = new LinkedList<>();
    /**
     * 更新右滑的视频
     */
    void getRightVideo() {
        videoModel.getTagVideoNews("足球",rightVideoGot);
    }
    CommonVideoGotCallBack rightVideoGot = new  CommonVideoGotCallBack() {
        @Override
        protected void onVideoGotSuccess(List<MyNews> newses) {
            rightNewses.addAll(newses);
        }

        @Override
        protected void onVideoNotExist() {

        }
    };







    public LiveData<String> getCurrentNews() {
        return currentNews;
    }

}
