package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import androidx.lifecycle.ViewModel;

import cn.edu.gdut.douyintoutiao.net.CommentApi;
import cn.edu.gdut.douyintoutiao.view.show.comment.model.CommentRepository;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 11/17/20 11:09
 */
public class NewsDetailViewModel extends ViewModel {
    private static final String TAG = "myTag";
    private final CommentRepository repository;

    public NewsDetailViewModel() {
        repository = new CommentRepository(CommentApi.getCommentApi());
    }

    public void postComment(String newsID, String userID, String content) {
        repository.postComment(newsID, userID, content);
    }
}
