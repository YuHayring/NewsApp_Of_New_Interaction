package cn.edu.gdut.douyintoutiao.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import cn.edu.gdut.douyintoutiao.viewmodel.UserMainViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 2020/11/6 20:48
 */
public class UserMainModel {

    OnUserGotCallBack callBack;

    Handler handler = new GetUserHandler();

    public static final String STATUS = "status";

    public static final String USER = "user";

    public static final int SUCCESS = 1;

    Context context;

    public UserMainModel(Context context, OnUserGotCallBack callBack) {
        this.callBack = callBack;
        this.context = context;
    }

    private UserApi userApi = UserApi.getUserApi();


    public void getUser(String userId) {
        userApi.getUser().enqueue(netCallBack);
    }


    public interface OnUserGotCallBack {
        void onSuccess(User user);

        void onFaile(String errorInfo);
    }


    Callback<User> netCallBack = new Callback<User>() {

        @Override
        public void onResponse(Call call, Response response) {
            callBack.onSuccess((User) response.body());
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };



    private class GetUserHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
             int status = msg.getData().getInt(STATUS);
             if (status == 1) {
                 User user = (User)msg.getData().getSerializable(USER);
                 callBack.onSuccess(user);
             }
        }


    }
}
