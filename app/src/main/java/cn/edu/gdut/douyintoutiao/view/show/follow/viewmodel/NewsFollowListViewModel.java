package cn.edu.gdut.douyintoutiao.view.show.follow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.follow.model.FollowNewsRepository;

public class NewsFollowListViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final FollowNewsRepository newsRepository;

    public NewsFollowListViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new FollowNewsRepository(NewsApi.getNewsApi());
    }

    public LiveData<List<MyNews>> getAllFollowLive(String tag) {
        return newsRepository.getAllFollowLive(tag);
    }


}