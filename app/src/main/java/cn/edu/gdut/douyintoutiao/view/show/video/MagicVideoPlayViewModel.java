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
public class MagicVideoPlayViewModel extends VideoPlayViewModel {

    /**
     * 标题
     */
    LiveData<String> currentNews = new MutableLiveData<>();




    VideoPlayModel videoPlayModel = VideoPlayModel.getInstance();



    public MagicVideoPlayViewModel(){}

    public MagicVideoPlayViewModel(Activity activity) {
        super(activity);
    }




    int upVideoIndex;

    LinkedList<MyNews> upNewses = new LinkedList<>();
    /**
     * 更新上滑的视频
     */
    void getUpVideo() {
        videoPlayModel.getTagVideoNews("学习",upVideoGot);
    }
    CommonVideoGotCallBack upVideoGot = new  CommonVideoGotCallBack() {
        @Override
        void onVideoGotSuccess(List<MyNews> newses) {
            upNewses.addAll(newses);

        }

        @Override
        void onVideoNotExist() {

        }
    };





    LinkedList<MyNews> downNewses = new LinkedList<>();
    /**
     * 更新下滑的视频
     */
    void getDownVideo() {
        videoPlayModel.getVideoNews(downVideoGot);
    }

    CommonVideoGotCallBack downVideoGot = new  CommonVideoGotCallBack() {
        @Override
        void onVideoGotSuccess(List<MyNews> newses) {
            downNewses.addAll(newses);
        }

        @Override
        void onVideoNotExist() {

        }
    };


    LinkedList<MyNews> leftNewses = new LinkedList<>();
    /**
     * 更新左滑的视频
     */
    void getLeftVideo() {
        videoPlayModel.getTagVideoNews("csgo",leftVideoGot);
    }
    CommonVideoGotCallBack leftVideoGot = new  CommonVideoGotCallBack() {
        @Override
        void onVideoGotSuccess(List<MyNews> newses) {
            leftNewses.addAll(newses);
        }

        @Override
        void onVideoNotExist() {

        }
    };

    LinkedList<MyNews> rightNewses = new LinkedList<>();
    /**
     * 更新右滑的视频
     */
    void getRightVideo() {
        videoPlayModel.getTagVideoNews("足球",rightVideoGot);
    }
    CommonVideoGotCallBack rightVideoGot = new  CommonVideoGotCallBack() {
        @Override
        void onVideoGotSuccess(List<MyNews> newses) {
            rightNewses.addAll(newses);
        }

        @Override
        void onVideoNotExist() {

        }
    };







    public LiveData<String> getCurrentNews() {
        return currentNews;
    }

}
