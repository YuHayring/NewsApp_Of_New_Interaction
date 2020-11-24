package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorDetailsViewModel;

public class ActivityFollowAuthorDetails extends AppCompatActivity  {

    private String userId;
    private String followId;

    private TextView textView;
    private FollowAuthorDetailsViewModel followAuthorDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_author_details);
       // init();
       // getUserInfo();

    }
    //初始化
    private void init(){
        //textView = findViewById(R.id.author_details_textView);

    }
    //拿到被访问的userId
    private  void getUserInfo(){
        Intent in = getIntent();
       userId = in.getStringExtra("userId");
      // textView.setText("获得了用户信息："+userId);
    }

    //给fragment提供userId
    public String getUserId() {
        getUserInfo();
        return userId;
    }

    public String getFollowId() {
        Intent in = getIntent();
        followId = in .getStringExtra("followId");
        return followId;
    }
}