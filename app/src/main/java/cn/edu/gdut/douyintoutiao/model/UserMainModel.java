package cn.edu.gdut.douyintoutiao.model;

import cn.edu.gdut.douyintoutiao.entity.User;

/**
 * @author hayring
 * @date 2020/11/6 20:48
 */
public class UserMainModel {


    public void getUser(final OnUserGotCallBack callBack) {
        User user = new User();
        user.setUserName("特朗普");
        user.setUserDescription("美国总统");

        if (user == null) {
            callBack.onFaile("");
        } else {
            callBack.onSuccess(user);
        }
    }


    public interface OnUserGotCallBack {
        void onSuccess(User user);

        void onFaile(String errorInfo);
    }
}
