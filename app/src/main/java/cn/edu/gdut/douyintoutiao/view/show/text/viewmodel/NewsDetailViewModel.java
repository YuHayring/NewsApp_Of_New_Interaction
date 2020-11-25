package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import cn.edu.gdut.douyintoutiao.base.ObserverManager;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.comment.model.CommentRepository;
import cn.edu.gdut.douyintoutiao.view.show.text.model.NewsRepository;
import cn.edu.gdut.douyintoutiao.view.user.login.Callback;
import cn.edu.gdut.douyintoutiao.view.user.login.LoginUserModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 11/17/20 11:09
 */
public class NewsDetailViewModel extends ViewModel {
    private static final String TAG = "myTag";
    private final CommentRepository repository;
    private final NewsRepository newsRepository;

    private Callback<Result> callback;

    public NewsDetailViewModel() {
        repository = new CommentRepository(CommentApi.getCommentApi());
        newsRepository = new NewsRepository(NewsApi.getNewsApi());
    }

    public void postComment(String newsID, String userID, String content) {
        repository.postComment(newsID, userID, content);
    }


    /**
     * @DengJl
     * 关注事件功能
     */
    public void insertTagsFollowByNewsIdUserId(String newsId,String userId){
        newsRepository.insertTagsFollowByNewsIdUserId(newsId,userId);
    }

    public void deleteTagsFollowByNewsIdUserId(String newsId,String userId){
        newsRepository.deleteTagsFollowByNewsIdUserId(newsId,userId);
    }

//    public boolean checkTagsFollowByNewsIdUserId (String newsId,String userId){
//        return newsRepository.checkTagsFollowByNewsIdUserId(newsId,userId);
//    }

    public boolean checkTagsFollowByNewsIdUserId1(String newsId,String userId){
        boolean flag;
        newsRepository.checkTagsFollowByNewsIdUserId1(newsId,userId).subscribeOn(Schedulers.io())//check方法放到子线程
                .observeOn(AndroidSchedulers.mainThread())//把下面操作切换到主线程
                .subscribe(new Observer< Result >() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG,"onSubscribe run");
                    }

                    @Override
                    public void onNext(@NonNull Result response) {
                        Log.d(TAG,response.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG,"onError run");

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete run");

                    }
                });
//                .subscribe(new ObserverManager< Result >() {
//                    @Override
//                    public void onSuccess(Result response) {
//                        Log.d(TAG, "检查关注onSuccess: " + response.getMsg());
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        Log.d(TAG, "检查关注onFinish: 请求完成");
//                    }
//
//                    @Override
//                    public void onDisposable(Disposable disposable) {
//
//                    }
//                });//
//                    }
//
//                    @Override
//                    public void onFail(Throwable throwable) {
//                        Log.d(TAG, "onFail: 请求失败" );


                return false;
    }
}
