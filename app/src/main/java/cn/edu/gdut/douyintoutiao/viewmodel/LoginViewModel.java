package cn.edu.gdut.douyintoutiao.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.model.LoginUserModel;

/**
 * @description ： TODO:类的作用
 * @author : 彭俊源
 * @email : 516585610@qq.com
 * @date : 2020年11月09日20:00:08
 */
public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> username;
    private MutableLiveData<String> password;
    private MutableLiveData<Result> result;
    private LoginUserModel loginUserModel;
    private MutableLiveData<Boolean> flag;

    private static final String TAG = "TAG";

    public MutableLiveData<Result> getResult() {
        return result;
    }

    public void init(){
        flag = new MutableLiveData<>(false);
        loginUserModel = LoginUserModel.getInstance();
    }

    public MutableLiveData<String> getUsername() {
        if(username == null){
            username = new MutableLiveData<>();
            username.setValue("admin");
        }
        return username;
    }

    public void setUsername(MutableLiveData<String> username) {
        this.username = username;
    }

    public MutableLiveData<String> getPassword() {
        if(password == null){
            password = new MutableLiveData<>("123456");
        }
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public void login(){
        User user = new User();
        user.setUserName(username.getValue());
        user.setUserPassword(password.getValue());
        result = loginUserModel.postLogin(user);
        Log.d(TAG, "login: " + result.getValue().toString());
        flag.setValue(result.getValue().getLogin());
    }

    public MutableLiveData<Boolean> getFlag() {
        if(flag == null){
            flag = new MutableLiveData<>(false);
        }
        return flag;
    }
}
