package cn.edu.gdut.douyintoutiao.view.user.main;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.UserApi;
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


    public UserMainModel(OnUserGotCallBack callBack) {
        this.callBack = callBack;
    }

    private UserApi userApi = UserApi.getUserApi();


    //获取用户信息
    public void getUser(String userId) {
        //执行网络请求，输入回调接口
        userApi.getUser().enqueue(netCallBack);
    }




    public interface OnUserGotCallBack {
        void onSuccess(User user);

        void onFaile(String errorInfo);
    }


    /**
     * 网络请求-用户信息-回调
     */
    Callback<User> netCallBack = new Callback<User>() {

        @Override
        public void onResponse(Call call, Response response) {
            //回调 ViewModel 模块
            callBack.onSuccess((User) response.body());
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };



    //Handler 机制
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
