package cn.edu.gdut.douyintoutiao.model;

import android.os.StrictMode;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.RetrofitSingleton;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/9 20:55
 */
public class LoginUserModel {

    private static LoginUserModel instance;
    private static final String TAG = "loginUserModel";
    private UserApi api;

    public static LoginUserModel getInstance(){
        if(instance == null){
            instance = new LoginUserModel();
        }
        return instance;
    }

    //使用网络通讯获取内容
    public MutableLiveData<Result> postLogin(User user){
        //解决网络通讯不能在主线程运行的问题
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        api = UserApi.getUserApi();
        Call<Result> stringCall = api.validateUser(user);
        MutableLiveData<Result> mutableLiveData = new MutableLiveData<>();
        Log.d(TAG, "postLogin: " + user.toString());
        //异步方法，会导致空指针
 /*       stringCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                assert response.body() != null;
                Log.d(TAG, "onResponse: " + response.body().toString());
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败 ");
                t.printStackTrace();
            }
        });*/
        //同步的方法
        try {
            Response<Result> response = stringCall.execute();
            mutableLiveData.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mutableLiveData;
    }
}
