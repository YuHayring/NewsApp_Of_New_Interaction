package cn.edu.gdut.douyintoutiao.view.user.resign;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
/**
 * @author hudp
 */
public class ResignViewModel extends ViewModel {
    private MutableLiveData<String> phone;
    private MutableLiveData<String> password;
    private MutableLiveData<String> surepassword;
    private MutableLiveData<Result<User>> result;
    private ResignModel resignModel;
    private MutableLiveData<String> flag;

    private static final String TAG = "TAG";

    public MutableLiveData<Result<User>> getResult() {
        if(result == null){
            result = new MutableLiveData<>();
        }
        return result;
    }

    public void init(){
        flag = new MutableLiveData<String>("");
        resignModel = ResignModel.getInstance();
    }

    public MutableLiveData<String> getPhone() {
        if(phone == null){
            phone = new MutableLiveData<>();
            phone.setValue("123455");
        }
        return phone;
    }

    public void setphone(MutableLiveData<String> username) {
        this.phone = username;
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
    //检查各数据
    public boolean check_something(User user,String surepassword){
        String userPhone = user.getUserTelephone();
        String password = user.getUserPassword();
        //检查各个是否有输入
            if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(password )|| TextUtils.isEmpty(surepassword)) {
                flag.setValue("no_enough");
                return false;
            }
            //检查手机号是否是11位
            if(userPhone.length()!=11){
                flag.setValue("phone_number");
                return false;
            }
            //检查两次输入密码是否一样
            if(!TextUtils.equals(password,surepassword)){
                flag.setValue("sure_password");
                return false;
            }
            return true;
    }
    public void resign(User user){
        result = resignModel.check_user(user);
        Log.d(TAG, "check: " + result.getValue().toString());
        if(result.getValue().getMsg().equals("true")){
//            user.setRsaPassword(Arrays.toString(resignModel.encrypt(user.getUserPassword())));
            user.setRsaPassword(resignModel.encrypt(user.getUserPassword()));
            result = resignModel.insert_user(user);
            Log.d(TAG, "insert: " + result.getValue().toString());
            Log.d(TAG, "user: " + user.toString());
            if(result.getValue().getMsg().equals("true")){
                flag.setValue("insert_true");
            }else{
                flag.setValue("insert_false");
            }
        }else{
            flag.setValue("check_true");
        }
    }

    public MutableLiveData<String> getFlag() {
        if(flag == null){
            flag = new MutableLiveData<String>("");
        }
        return flag;
    }

}
