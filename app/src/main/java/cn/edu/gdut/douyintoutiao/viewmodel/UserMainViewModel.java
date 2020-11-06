package cn.edu.gdut.douyintoutiao.viewmodel;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

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


    public UserMainModel model;

    private int num=1;

    public UserMainViewModel(UserMainActivity activity) {
        this.activity = activity;

        model = new UserMainModel();

        model.getUser(new UserMainModel.OnUserGotCallBack() {
            @Override
            public void onSuccess(User user) {
                Resources resources = activity.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.avatar);
                activity.getAvatar().setImageDrawable(drawable);
                activity.getUserNameTag().setText(user.getUserName());
                activity.getUserDescriptionTag().setText(user.getUserDescription());

            }

            @Override
            public void onFaile(String errorInfo) {

            }
        });
    }

}
