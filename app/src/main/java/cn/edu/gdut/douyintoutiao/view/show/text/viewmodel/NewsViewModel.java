package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.follow.model.FollowNewsRepository;
import cn.edu.gdut.douyintoutiao.view.show.text.model.NewsRepository;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/11 16:52
 */
public class NewsViewModel extends AndroidViewModel {
    private final NewsRepository newsRepository;
    private final FollowNewsRepository followNewsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(NewsApi.getNewsApi());
        followNewsRepository = new FollowNewsRepository(NewsApi.getNewsApi());
    }

    public LiveData<List<MyNews>> getAllNewsLive() {
        return newsRepository.getAllNewsLive();
    }

    public LiveData<List<MyNews>> getAllFollowLive(String tag) {
        return followNewsRepository.getAllFollowLive(tag);
    }

    public LiveData<List<MyNews>> getAllAuthorNews(String userId){
        return followNewsRepository.getAuthorNewsLive(userId);
    }

    public void newsLike(MyNews news){
        newsRepository.newsLike(news);
    }






}
