package cn.edu.gdut.douyintoutiao.view.user.main;

import androidx.databinding.ObservableField;

import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.view.user.main.UserMainModel;

/**
 * @author hayring
 * @date 2020/11/6 20:51
 */
public class UserMainViewModel {

    public UserMainViewModel() {
        this.userMainModel = new UserMainModel(userGotCallBack);
    }


    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> userDescription = new ObservableField<>();

    public UserMainModel userMainModel;

    public interface OnUserGotCallBack {
        void onSuccess(User user);

        void onFaile(String errorInfo);
    }


    OnUserGotCallBack userGotCallBack = new OnUserGotCallBack() {

        /**
         * 获取到用户信息，将其显示在界面上
         * @param user
         */
        @Override
        public void onSuccess(User user) {
            userName.set(user.getUserName());
            userDescription.set(user.getUserDescription());
        }

        @Override
        public void onFaile(String errorInfo) {
//            Toast.makeText(activity,"Failed",Toast.LENGTH_LONG).show();
            //TODO
        }
    };




}
