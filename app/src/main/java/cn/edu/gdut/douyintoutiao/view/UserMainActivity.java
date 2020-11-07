package cn.edu.gdut.douyintoutiao.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityUserMainBinding;
import cn.edu.gdut.douyintoutiao.viewmodel.UserMainViewModel;

/***
 * @author hayring
 * @date 2020/11/3 19:08
 * 个人资料
 */
public class UserMainActivity extends AppCompatActivity {

//    TextView userNameTag;
//    TextView userDescriptionTag;
//    ImageView avatar;
//    Button help;
//    Button setting;

    private ActivityUserMainBinding mUserInfoBinding;

    UserMainViewModel userMainViewModel = new UserMainViewModel(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        mUserInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_main);
        mUserInfoBinding.setUserMainViewModel(userMainViewModel);
        userMainViewModel.userMainModel.getUser("");


//
//
//        userNameTag = findViewById(R.id.user_name_tag);
//        userDescriptionTag = findViewById(R.id.user_description_tag);
//        avatar = findViewById(R.id.user_avatars);
//        help = findViewById(R.id.help_button);
//        setting = findViewById(R.id.setting_button);
//
//        UserMainViewModel viewModel = new UserMainViewModel(this);
    }


//    public TextView getUserNameTag() {
//        return userNameTag;
//    }
//
//    public void setUserNameTag(TextView userNameTag) {
//        this.userNameTag = userNameTag;
//    }
//
//    public TextView getUserDescriptionTag() {
//        return userDescriptionTag;
//    }
//
//    public void setUserDescriptionTag(TextView userDescriptionTag) {
//        this.userDescriptionTag = userDescriptionTag;
//    }
//
//    public ImageView getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(ImageView avatar) {
//        this.avatar = avatar;
//    }
//
//    public Button getHelp() {
//        return help;
//    }
//
//    public void setHelp(Button help) {
//        this.help = help;
//    }
//
//    public Button getSetting() {
//        return setting;
//    }
//
//    public void setSetting(Button setting) {
//        this.setting = setting;
//    }
}