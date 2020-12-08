package cn.edu.gdut.douyintoutiao.view.show.text.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ProjectName: DouYinTouTiao
 * @Package: cn.edu.gdut.douyintoutiao.view.show.text.dataSource
 * @ClassName: NewsDataSource
 * @Description: java类作用描述
 * @Author: cypang
 * @CreateDate: 2020/12/1/0001 10:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/1/0001 10:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewsDataSource extends PageKeyedDataSource<Integer, MyNews> {

    public enum NetWorkStatus{
        INITIAL,
        LOADING,
        FAILED,
        COMPLETED
    }


    public static final int FIRST_PAGE = 0;
    public static final int PAGE_SIZE = 10;
    private static final String TAG = "newsDataSource";
    private int type = 0;
    private LoadInitialParams<Integer> myParams;
    private LoadInitialCallback<Integer, MyNews> myCallback;
    private LoadParams<Integer> myParams2;
    private LoadCallback<Integer, MyNews> myCallback2;

    private MutableLiveData<NetWorkStatus> netWorkStatusMutableLiveData = new MutableLiveData<>();

    public LiveData<NetWorkStatus> getNetWorkStatusMutableLiveData() {
        return netWorkStatusMutableLiveData;
    }

    public void setNetWorkStatusMutableLiveData(MutableLiveData<NetWorkStatus> netWorkStatusMutableLiveData) {
        this.netWorkStatusMutableLiveData = netWorkStatusMutableLiveData;
    }

    public void retry(){
        if(type == 0){
            return;
        }else if(type == 1){
            loadInitial(myParams, myCallback);
        }else if(type == 2){
            loadAfter(myParams2, myCallback2);
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MyNews> callback) {
        netWorkStatusMutableLiveData.postValue(NetWorkStatus.INITIAL);
        type = 0;
        myParams = null;
        myCallback = null;
        NewsApi.getNewsApi().getNewsList(FIRST_PAGE, PAGE_SIZE).enqueue(new Callback<Result<MyNews>>() {
            @Override
            public void onResponse(Call<Result<MyNews>> call, Response<Result<MyNews>> response) {
                if ("200".equals(response.body().getCode()) && "查询成功".equals(response.body().getMsg())) {
                    callback.onResult(response.body().getData(), null, FIRST_PAGE + 1);

                }
            }

            @Override
            public void onFailure(Call<Result<MyNews>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                myParams = params;
                myCallback = callback;
                type = 1;
                netWorkStatusMutableLiveData.postValue(NetWorkStatus.FAILED);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MyNews> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MyNews> callback) {
        netWorkStatusMutableLiveData.postValue(NetWorkStatus.LOADING);
        type = 0;
        myParams2 = null;
        myCallback2 = null;
        NewsApi.getNewsApi().getNewsList(params.key, PAGE_SIZE).enqueue(new Callback<Result<MyNews>>() {
            @Override
            public void onResponse(Call<Result<MyNews>> call, @NotNull Response<Result<MyNews>> response) {
                if ("200".equals(response.body().getCode()) && "查询成功".equals(response.body().getMsg())) {
                    List<MyNews> data = response.body().getData();
                    boolean hasMoreData = data != null && data.size() >= PAGE_SIZE;
                    if(!hasMoreData){
                        netWorkStatusMutableLiveData.postValue(NetWorkStatus.COMPLETED);
                    }
                    callback.onResult(data, hasMoreData ? params.key + 1 : null);
                }
            }

            @Override
            public void onFailure(Call<Result<MyNews>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                myParams2 = params;
                myCallback2 = callback;
                type = 2;
                netWorkStatusMutableLiveData.postValue(NetWorkStatus.FAILED);
            }
        });
    }
}
