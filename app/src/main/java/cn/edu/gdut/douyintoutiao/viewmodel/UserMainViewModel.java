package cn.edu.gdut.douyintoutiao.viewmodel;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.model.UserMainModel;
import cn.edu.gdut.douyintoutiao.view.UserMainActivity;

/**
 * @author hayring
 * @date 2020/11/6 20:51
 */
public class UserMainViewModel {

    public UserMainActivity activity;


    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> userDescription = new ObservableField<>();

    public UserMainModel userMainModel = new UserMainModel(activity, new UserMainModel.OnUserGotCallBack() {
        @Override
        public void onSuccess(User user) {
            userName.set(user.getUserName());
            userDescription.set(user.getUserDescription());
        }

        @Override
        public void onFaile(String errorInfo) {
            Toast.makeText(activity,"Failed",Toast.LENGTH_LONG).show();
        }
    });

    public UserMainViewModel(UserMainActivity activity) {
        this.activity = activity;
    }


}
