package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityVideoPlayBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 * @date 2020/11/7 16:09
 */
public class VerticalVideoPlayViewModel extends VideoPlayViewModel {


    public VerticalVideoPlayViewModel() {}


    public VerticalVideoPlayViewModel(Activity activity) {
        super(activity);
    }


    private VideoPlayModel videoPlayModel = VideoPlayModel.getInstance();

    private VideoStateAdapter adapter;


    MutableLiveData<List<MyNews>> newsesFromServer = new MutableLiveData<>();

    MutableLiveData<Integer> errCode = new MutableLiveData<>();


    /**
     * 获取关注视频
     */
    void getFollowVideoNews() {
        videoPlayModel.getVideoNews(followVideoGot);
    }


    /**
     * 获取更多关注视频
     */
    void getMoreFollowVideoNews(int index) {
        videoPlayModel.getMoreVideoNews(index, followVideoGot);
    }


    /**
     * 搜索视频
     */
    void searchVideoNews(String key) {
        videoPlayModel.searchVideoNews(key, followVideoGot);
    }

    /**
     * 更多搜索视频结果
     */
    void searchMoreVideoNews(String key, int index) {
        videoPlayModel.searchMoreVideoNews(index, key, followVideoGot);
    }


    CommonVideoGotCallBack followVideoGot = new CommonVideoGotCallBack() {
        @Override
        void onVideoGotSuccess(List<MyNews> newses) {
            newsesFromServer.postValue(newses);
            errCode.postValue(0);
        }

        @Override
        void onVideoNotExist() {
            errCode.postValue(404);
        }

        @Override
        void onRequestError(int errCode) {
            VerticalVideoPlayViewModel.this.errCode.postValue(404);
        }
    };









    public void insertTagsFollowByNewsIdUserId(String newsId, String userId){
        videoPlayModel.insertTagsFollowByNewsIdUserId(newsId,userId);
    }


    public LiveData<List<MyNews>> getNewsesFromServer() {
        return newsesFromServer;
    }

    public MutableLiveData<Integer> getErrCode() {
        return errCode;
    }
}