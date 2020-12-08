package cn.edu.gdut.douyintoutiao.view.user.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.gdut.douyintoutiao.base.ObserverManager;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.util.MyUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author : 彭俊源
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020年11月09日20:00:08
 */
public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> telephone;
    private MutableLiveData<String> password;
    private LoginUserModel loginUserModel;

    private Callback<Result<User>> mCallback;

    private static final String TAG = "TAG";


    public void init(Callback<Result<User>> callback) {
        loginUserModel = LoginUserModel.getInstance();
        mCallback = callback;
    }

    public MutableLiveData<String> getTelephone() {
        if (telephone == null) {
            telephone = new MutableLiveData<>();
            telephone.setValue("13444444444");
        }
        return telephone;
    }

    public void setTelephone(MutableLiveData<String> username) {
        this.telephone = username;
    }

    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>("123456");
        }
        return password;
    }

    public void setPassword(MutableLiveData<String> password) {
        this.password = password;
    }

    public void login() {
        User user = new User();
        Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
        if(telephone.getValue() == null || telephone.getValue().length() == 0 || password.getValue() == null || password.getValue().length() == 0){
            mCallback.returnResult(new Result<User>("用户名或密码为空", "", false, new ArrayList<>()));
            return;
        }
        Matcher m = CHINA_PATTERN.matcher(telephone.getValue());
        if(!m.matches()){
            mCallback.returnResult(new Result<User>("手机号格式不正常", "", false, new ArrayList<>()));
            return;
        }
        user.setUserTelephone(telephone.getValue());
        user.setUserPassword(password.getValue());
        Observable<Result<User>> resultObservable = loginUserModel.postLogin(user);
        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverManager<Result<User>>() {
                    @Override
                    public void onSuccess(Result<User> userResult) {
                        Log.d(TAG, "onSuccess: " + userResult.toString());
                        mCallback.returnResult(userResult);
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        mCallback.returnResult(new Result<User>("网络请求失败", "400", false, new ArrayList<>()));
                    }

                    @Override
                    public void onFinish() {
                        Log.d(TAG, "onFinish: 请求完成");
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {

                    }
                });
        }

}
