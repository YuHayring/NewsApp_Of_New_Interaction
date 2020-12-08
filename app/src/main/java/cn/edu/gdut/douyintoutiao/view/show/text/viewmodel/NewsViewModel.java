package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.Objects;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.follow.model.FollowNewsRepository;
import cn.edu.gdut.douyintoutiao.view.show.text.dataSource.NewsDataSource;
import cn.edu.gdut.douyintoutiao.view.show.text.dataSource.NewsDataSourceFactory;
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

    private NewsDataSourceFactory newsDataSourceFactory;

    public LiveData<PagedList<MyNews>> newsPagedList;

    public LiveData<NewsDataSource.NetWorkStatus> netWorkStatus;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(NewsApi.getNewsApi());
        followNewsRepository = new FollowNewsRepository(NewsApi.getNewsApi());
        PagedList.Config config = new PagedList.Config.Builder().
                //设置占位符
                setEnablePlaceholders(true).
                        //设置每一页的大小
                setPageSize(NewsDataSource.PAGE_SIZE).
                        //设置距离页脚还有多少数据时开始加载
                setPrefetchDistance(3).
                        //设置初始获取数据大小
                setInitialLoadSizeHint(NewsDataSource.PAGE_SIZE * 2).
                        //设置最大容量
                setMaxSize(65536 * NewsDataSource.PAGE_SIZE).build();
        newsDataSourceFactory = new NewsDataSourceFactory();
        newsPagedList = new LivePagedListBuilder<>(newsDataSourceFactory, config).build();
        netWorkStatus = Transformations.switchMap(newsDataSourceFactory.getLiveDataSource(), NewsDataSource::getNetWorkStatusMutableLiveData);
    }


    public void reset(){
        newsPagedList.getValue().getDataSource().invalidate();
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

    public void retry(){
        Objects.requireNonNull(newsDataSourceFactory.getLiveDataSource().getValue()).retry();
    }

}
