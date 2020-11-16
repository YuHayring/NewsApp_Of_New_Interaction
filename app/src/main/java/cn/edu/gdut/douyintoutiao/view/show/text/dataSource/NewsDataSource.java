package cn.edu.gdut.douyintoutiao.view.show.text.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import cn.edu.gdut.douyintoutiao.entity.News;

/**
 * @author : cypang
 * @description ： 分页用数据源
 * @email : 516585610@qq.com
 * @date : 11/14/20 23:53
 */
public class NewsDataSource extends PageKeyedDataSource<Integer, News> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, News> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, News> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, News> callback) {

    }
}
