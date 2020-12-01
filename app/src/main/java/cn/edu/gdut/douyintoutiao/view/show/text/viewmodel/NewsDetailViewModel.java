package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsDetailFragment;
import cn.edu.gdut.douyintoutiao.view.show.text.model.NewsRepository;
import cn.edu.gdut.douyintoutiao.view.user.login.Callback;

/**
 * @author : cypang
 * @description ：
 * @email : 516585610@qq.com
 * @date : 11/17/20 11:09
 */
public class NewsDetailViewModel extends ViewModel {
    private static final String TAG = "myTag";
    private final NewsRepository newsRepository;
    private List<Boolean> checkData;
    Boolean checkFollowFlag = false;

    //  private Callback<Result> checkCallback;


//    public void init(Callback<Result> callback) {
//       newsRepository  = NewsRepository.getInstance();
//        checkCallback = callback;
//    }

    public NewsDetailViewModel() {
        newsRepository = new NewsRepository(NewsApi.getNewsApi());
    }





    public void newsLike(MyNews news){
        newsRepository.newsLike(news);
    }
    public void newsUnLike(MyNews news){
        newsRepository.newsUnLike(news);
    }

//    public String checkMsg ;
//
//    CheckFollowCallBack checkFollowCallBack = new CheckFollowCallBack() {
//        @Override
//        public void onSuccess(String msg) {
//            checkMsg = msg;
//        }
//
//        @Override
//        public void onFaile(String errorInfo) {
//
//        }
//    };


    /**
     * @DengJl
     * 关注事件功能
     */
    private NewsDetailFragment.CheckFollowListener checkFollowListener;

    private Callback<Result> mCallback;

    public Boolean getCheckFollowFlag() {
        return checkFollowFlag;
    }

    public void setCheckFollowFlag(Boolean checkFollowFlag) {
        this.checkFollowFlag = checkFollowFlag;
    }

    public void setCheckFollowListener(NewsDetailFragment.CheckFollowListener checkFollowListener) {
        this.checkFollowListener = checkFollowListener;
    }

    public void insertTagsFollowByNewsIdUserId(String newsId, String userId){
        newsRepository.insertTagsFollowByNewsIdUserId(newsId,userId);
    }

    public void deleteTagsFollowByNewsIdUserId(String newsId,String userId){
        newsRepository.deleteTagsFollowByNewsIdUserId(newsId,userId);
    }

    public LiveData<List<Boolean>> checkTagsFollowByNewsIdUserId (String newsId, String userId){
       return newsRepository.checkTagsFollowByNewsIdUserId(newsId,userId);
    }


}
