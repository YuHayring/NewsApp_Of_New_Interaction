package cn.edu.gdut.douyintoutiao.view.user.resign;

import android.os.StrictMode;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author hudp
 */
public class ResignModel {
    private static ResignModel instance;
    private static final String TAG = "loginUserModel";
    private UserApi api;

    public static ResignModel getInstance(){
        if(instance == null){
            instance = new ResignModel();
        }
        return instance;
    }


    //检查该用户是否注册过
    public MutableLiveData<Result<User>> check_user(User user){
        //解决网络通讯不能在主线程运行的问题
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //调用api
        api = UserApi.getUserApi();
        Call<Result<User>> check = api.check_Resign(user);
        MutableLiveData<Result<User>> mutableLiveData = new MutableLiveData<>();
        Log.d(TAG, "注册数据: " + user.toString());
        //开始调用
        try {
            Response<Result<User>> response = check.execute();
            mutableLiveData.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mutableLiveData;
    }

    //插入该用户的注册数据
    public MutableLiveData<Result<User>> insert_user(User user){
        //解决网络通讯不能在主线程运行的问题
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //调用api
        api = UserApi.getUserApi();
        Call<Result<User>> insert= api.insertUser(user);
        MutableLiveData<Result<User>> mutableLiveData = new MutableLiveData<>();
        Log.d(TAG, "数据: " + user.toString());
        //开始调用
        try {
            Response<Result<User>> response = insert.execute();
            mutableLiveData.setValue(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mutableLiveData;
    }



}
