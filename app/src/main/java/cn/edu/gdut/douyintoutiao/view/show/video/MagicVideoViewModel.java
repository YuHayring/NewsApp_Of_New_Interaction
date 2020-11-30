package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.util.CommonDataGotCallBack;

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
    CommonDataGotCallBack upVideoGot = new  CommonDataGotCallBack<MyNews>() {
        @Override
        protected void onGotSuccess(List<MyNews> newses) {
            upNewses.addAll(newses);

        }

        @Override
        protected void onNotExist() {

        }
    };





    LinkedList<MyNews> downNewses = new LinkedList<>();
    /**
     * 更新下滑的视频
     */
    void getDownVideo() {
        videoModel.getVideoNews(downVideoGot);
    }

    CommonDataGotCallBack downVideoGot = new  CommonDataGotCallBack<MyNews>() {
        @Override
        protected void onGotSuccess(List<MyNews> newses) {
            downNewses.addAll(newses);
        }

        @Override
        protected void onNotExist() {

        }
    };


    LinkedList<MyNews> leftNewses = new LinkedList<>();
    /**
     * 更新左滑的视频
     */
    void getLeftVideo() {
        videoModel.getTagVideoNews("csgo",leftVideoGot);
    }
    CommonDataGotCallBack leftVideoGot = new  CommonDataGotCallBack<MyNews>() {
        @Override
        protected void onGotSuccess(List<MyNews> newses) {
            leftNewses.addAll(newses);
        }

        @Override
        protected void onNotExist() {

        }
    };

    LinkedList<MyNews> rightNewses = new LinkedList<>();
    /**
     * 更新右滑的视频
     */
    void getRightVideo() {
        videoModel.getTagVideoNews("足球",rightVideoGot);
    }
    CommonDataGotCallBack rightVideoGot = new  CommonDataGotCallBack<MyNews>() {
        @Override
        protected void onGotSuccess(List<MyNews> newses) {
            rightNewses.addAll(newses);
        }

        @Override
        protected void onNotExist() {

        }
    };







    public LiveData<String> getCurrentNews() {
        return currentNews;
    }

}
