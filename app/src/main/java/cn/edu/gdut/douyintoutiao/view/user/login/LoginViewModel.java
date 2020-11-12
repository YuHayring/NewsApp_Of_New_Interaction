package cn.edu.gdut.douyintoutiao.view.user.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cn.edu.gdut.douyintoutiao.base.ObserverManager;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @description ： TODO:类的作用
 * @author : 彭俊源
 * @email : 516585610@qq.com
 * @date : 2020年11月09日20:00:08
 */
public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> username;
    private MutableLiveData<String> password;
    private LoginUserModel loginUserModel;

    private static final String TAG = "TAG";


    public void init(){
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

    public Observable<Result<User>> login(){
        User user = new User();
        user.setUserName(username.getValue());
        user.setUserPassword(password.getValue());
        return loginUserModel.postLogin(user);
    }

}
