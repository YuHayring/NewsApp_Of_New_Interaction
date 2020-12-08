package cn.edu.gdut.douyintoutiao.view.show.text.dataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import cn.edu.gdut.douyintoutiao.entity.MyNews;

/**
 * @ProjectName: DouYinTouTiao
 * @Package: cn.edu.gdut.douyintoutiao.view.show.text.dataSource
 * @ClassName: NewsDataSourceFactory
 * @Description: java类作用描述
 * @Author: cypang
 * @CreateDate: 2020/12/1/0001 10:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/1/0001 10:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewsDataSourceFactory extends DataSource.Factory<Integer, MyNews> {

    private MutableLiveData<NewsDataSource> liveDataSource = new MutableLiveData<>();

    public MutableLiveData<NewsDataSource> getLiveDataSource() {
        return liveDataSource;
    }

    @NonNull
    @Override
    public DataSource<Integer, MyNews> create() {
        NewsDataSource source = new NewsDataSource();
        liveDataSource.postValue(source);
        return source;
    }
}
