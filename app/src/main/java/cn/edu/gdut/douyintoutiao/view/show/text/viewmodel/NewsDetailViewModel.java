package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import androidx.lifecycle.ViewModel;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.comment.model.CommentRepository;
import cn.edu.gdut.douyintoutiao.view.show.text.model.NewsRepository;

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

  //  private Callback<Result> checkCallback;


//    public void init(Callback<Result> callback) {
//       newsRepository  = NewsRepository.getInstance();
//        checkCallback = callback;
//    }

    public NewsDetailViewModel() {
        repository = new CommentRepository(CommentApi.getCommentApi());
        newsRepository = new NewsRepository(NewsApi.getNewsApi());
    }

    public void postComment(String newsID, String userID, String content) {
        repository.postComment(newsID, userID, content);
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
    public void insertTagsFollowByNewsIdUserId(String newsId,String userId){
        newsRepository.insertTagsFollowByNewsIdUserId(newsId,userId);
    }

    public void deleteTagsFollowByNewsIdUserId(String newsId,String userId){
        newsRepository.deleteTagsFollowByNewsIdUserId(newsId,userId);
    }

//    public boolean checkTagsFollowByNewsIdUserId (String newsId,String userId){
//       return newsRepository.checkTagsFollowByNewsIdUserId(newsId,userId);
//    }

//    public boolean checkTagsFollowByNewsIdUserId(String newsId,String userId){
//        boolean flag;
//        newsRepository.checkTagsFollowByNewsIdUserId1(newsId,userId).subscribeOn(Schedulers.io())//check方法放到子线程
//                .observeOn(AndroidSchedulers.mainThread())//把下面操作切换到主线程
//                .subscribe(new Observer< Result >() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.d(TAG,"onSubscribe run");
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Result response) {
//                        Log.d(TAG,response.getMsg());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d(TAG,"onError run");
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG,"onComplete run");
//
//                    }
//                });
//
//                return false;
//    }

//    public interface CheckFollowCallBack {
//        void onSuccess(String msg);
//
//        void onFaile(String errorInfo);
//    }
}
