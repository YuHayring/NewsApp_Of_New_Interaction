package cn.edu.gdut.douyintoutiao.view.show.comment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import cn.edu.gdut.douyintoutiao.view.show.comment.model.CommentRepository;

/**
 * @author cypang
 * @date 2020年11月14日20:29:19
 */
public class CommentViewModel extends AndroidViewModel {
    private final CommentRepository repository;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        repository = new CommentRepository(CommentApi.getCommentApi());
    }
    // TODO: Implement the ViewModel

    public LiveData<List<Discuss>> getAllDiscussData(String newsId) {
        return repository.getAllDiscussLive(newsId);
    }

}