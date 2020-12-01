package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.util.CommonDataGotCallBack;

/**
 * @author hayring
 * @date 2020/11/7 16:09
 */
public class VerticalVideoViewModel extends VideoViewModel {


    public VerticalVideoViewModel() {}


    public VerticalVideoViewModel(Activity activity) {
        super(activity);
    }


    private VideoModel videoModel = VideoModel.getInstance();

    private VideoStateAdapter adapter;


    MutableLiveData<List<MyNews>> newsesFromServer = new MutableLiveData<>();

    MutableLiveData<Integer> errCode = new MutableLiveData<>();


    /**
     * 获取关注视频
     */
    void getFollowVideoNewsByCount() {
        videoModel.getVideoNews(followVideoGot);
    }

    /**
     * 按已知数量获取视频
     */
    void getFollowVideoNewsByCount(int count) {
        videoModel.getMoreVideoNews(0, count, followVideoGot);
    }


    /**
     * 获取更多关注视频
     */
    void getMoreFollowVideoNews(int index) {
        videoModel.getMoreVideoNews(index, followVideoGot);
    }


    /**
     * 搜索视频
     */
    void searchVideoNews(String key) {
        videoModel.searchVideoNews(key, followVideoGot);
    }

    /**
     * 更多搜索视频结果
     */
    void searchMoreVideoNews(String key, int index) {
        videoModel.searchMoreVideoNews(index, key, followVideoGot);
    }

    //添加关注
    void insertFollowByNewsIdUserId(String newsId,String userId){
        videoModel.insertFollowByNewsIdUserId(newsId,userId);
    }

    CommonDataGotCallBack followVideoGot = new CommonDataGotCallBack<MyNews>() {
        @Override
        protected void onGotSuccess(List<MyNews> newses) {
            newsesFromServer.postValue(newses);
            errCode.postValue(0);
        }

        @Override
        protected void onNotExist() {
            errCode.postValue(404);
        }

        @Override
        protected void onRequestError(int errCode) {
            VerticalVideoViewModel.this.errCode.postValue(404);
        }
    };











    public LiveData<List<MyNews>> getNewsesFromServer() {
        return newsesFromServer;
    }

    public MutableLiveData<Integer> getErrCode() {
        return errCode;
    }
}