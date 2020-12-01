package cn.edu.gdut.douyintoutiao.view;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 12/1/20 9:09 PM
 */
public class MainViewModel extends ViewModel {
    MainModel mainModel = MainModel.getInstance();

    MutableLiveData<String[]> tabs = new MutableLiveData<>();

    void getTabsFromNet() {
        mainModel.getTabs(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                tabs.postValue(response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    public MutableLiveData<String[]> getTabs() {
        return tabs;
    }
}
