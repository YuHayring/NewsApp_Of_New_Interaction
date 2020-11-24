package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import androidx.lifecycle.ViewModel;

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

    public boolean checkTagsFollowByNewsIdUserId (String newsId,String userId){
        return newsRepository.checkTagsFollowByNewsIdUserId(newsId,userId);
    }
}
