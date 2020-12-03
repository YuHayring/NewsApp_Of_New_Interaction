package cn.edu.gdut.douyintoutiao.view.user.login;

import android.os.StrictMode;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/9 20:55
 */
public class LoginUserModel {

    private static volatile LoginUserModel instance;
    private static final String TAG = "loginUserModel";
    private UserApi api;

    public static LoginUserModel getInstance(){
        if(instance == null){
            synchronized (LoginUserModel.class) {
                if(instance == null) {
                    instance = new LoginUserModel();
                }
            }
        }
        return instance;
    }

    private LoginUserModel() {
    }

    //使用网络通讯获取内容
    public Observable<Result<User>> postLogin(User user){
        api = UserApi.getUserApi();
        return api.validateUser(user);
    }
}
