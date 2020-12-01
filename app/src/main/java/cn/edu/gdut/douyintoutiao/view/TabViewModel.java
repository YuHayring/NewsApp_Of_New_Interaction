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
public class TabViewModel extends ViewModel {
    TabModel tabModel = TabModel.getInstance();

    MutableLiveData<String[]> tabs = new MutableLiveData<>();

    public void getTabsFromNet() {
        tabModel.getTabs(new Callback<String[]>() {
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

    MutableLiveData<Boolean> tabFollowUpdate = new MutableLiveData<>();

    public void setTabFollow(String userId, int tabFollow) {
        tabModel.setTabFollow(tabFollow,userId,new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204) {
                    tabFollowUpdate.postValue(true);
                } else {
                    tabFollowUpdate.postValue(false);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    public MutableLiveData<String[]> getTabs() {
        return tabs;
    }

    public MutableLiveData<Boolean> getTabFollowUpdate() {
        return tabFollowUpdate;
    }
}
