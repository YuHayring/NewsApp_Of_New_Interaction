package cn.edu.gdut.douyintoutiao.util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/29/20 2:39 PM
 */
public abstract class CommonDataGotCallBack<T> implements Callback<List<T>> {

    protected abstract void onGotSuccess(List<T> newses);

    protected abstract void onNotExist();

    protected void onRequestError(int errCode) {}


    @Override
    public void onResponse(Call<List<T>> call, Response<List<T>> response) {
        if (response.code() == 200) {
            onGotSuccess(response.body());
        } else if (response.code() == 404) {
            onNotExist();
        } else {
            onRequestError(response.code());
        }
    }

    @Override
    public void onFailure(Call<List<T>> call, Throwable t) {
        throw new RuntimeException(t);
    }
}