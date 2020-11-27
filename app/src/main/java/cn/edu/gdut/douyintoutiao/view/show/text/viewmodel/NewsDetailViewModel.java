package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.comment.model.CommentRepository;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsDetailFragment;
import cn.edu.gdut.douyintoutiao.view.show.text.model.NewsRepository;
import cn.edu.gdut.douyintoutiao.view.user.login.Callback;

/**
 * @author : cypang
 * @description ： TODO:类的作用
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

//    public void checkTagsFollowByNewsIdUserId(String newsId,String userId) {
//        newsRepository.checkTagsFollowByNewsIdUserId(newsId, userId).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//把下面操作切换到主线程
//                .subscribe(new Observer< Result >() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.d(TAG, "checkTagsFollowOnSubscribe run");
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Result response) {
//                        Log.d(TAG, response.getMsg() + response.getData());
//                        System.out.println("获取到data："+response.getData());
//                            checkFollowListener.checkFollow(response.getMsg());
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d(TAG, "checkTagsFollowOnError run");
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "checkTagsFollowOnComplete run");
//
//                    }
//                });
//
//    }

}
