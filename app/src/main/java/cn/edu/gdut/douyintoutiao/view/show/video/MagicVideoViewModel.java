package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
        SharedPreferences shp = activity.getSharedPreferences(MagicVideoPlayActivity.SHP_KEY, Context.MODE_PRIVATE);
        upTab = shp.getString(MagicVideoPlayActivity.UP,"");
        leftTab = shp.getString(MagicVideoPlayActivity.LEFT, "");
        rightTab = shp.getString(MagicVideoPlayActivity.RIGHT, "");
    }




    int upVideoIndex;

    LinkedList<MyNews> upNewses = new LinkedList<>();

    /**
     * 上滑 tab
     */
    String upTab;
    /**
     * 更新上滑的视频
     */
    void getUpVideo() {
        videoModel.getTagVideoNews(upTab, upVideoGot);
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
            if (downNewses.size() == newses.size())
                ((MagicVideoPlayActivity)activity).setCurrentNews(downNewses.pop());
        }

        @Override
        protected void onNotExist() {

        }
    };


    LinkedList<MyNews> leftNewses = new LinkedList<>();
    /**
     * 左滑 tab
     */
    String leftTab;

    /**
     * 更新左滑的视频
     */
    void getLeftVideo() {
        videoModel.getTagVideoNews(leftTab, leftVideoGot);
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
     * 右滑 tab
     */
    String rightTab;
    /**
     * 更新右滑的视频
     */
    void getRightVideo() {
        videoModel.getTagVideoNews(rightTab,rightVideoGot);
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
