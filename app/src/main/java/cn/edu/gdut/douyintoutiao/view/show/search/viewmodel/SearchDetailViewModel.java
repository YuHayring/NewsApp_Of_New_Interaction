package cn.edu.gdut.douyintoutiao.view.show.search.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.search.model.SearchRepository;

public class SearchDetailViewModel extends AndroidViewModel {
    private final SearchRepository searchRepository;

    public SearchDetailViewModel(@NonNull Application application) {
        super(application);
        searchRepository = new SearchRepository(NewsApi.getNewsApi());
    }

    public LiveData<List<MyNews>> getAllSearchNewsLive(String key) {
        return searchRepository.getSearchNewsList(key);
    }


}