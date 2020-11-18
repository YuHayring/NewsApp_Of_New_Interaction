package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorDetailsViewModel;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;
import io.reactivex.rxjava3.annotations.Nullable;

public class ActivityFollowAuthorDetails extends AppCompatActivity {

    private String userId;

    private TextView textView;
    private FollowAuthorDetailsViewModel followAuthorDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_author_details);
        init();
        getUserInfo();

        followAuthorDetailsViewModel = new ViewModelProvider(this).get(FollowAuthorDetailsViewModel.class);

        followAuthorDetailsViewModel.queryUserByUserId(userId).observe(this, new Observer< List< User > >() {
            @Override
            public void onChanged(List< User > list) {
                textView.setText("获得信息："+list.toString());
            }
        });


    }
    //初始化
    private void init(){
        textView = findViewById(R.id.author_details_textView);

    }
    //拿到被访问的userId
    private  void getUserInfo(){
        Intent in = getIntent();
       userId = in.getStringExtra("userId");
      // textView.setText("获得了用户信息："+userId);
    }
}