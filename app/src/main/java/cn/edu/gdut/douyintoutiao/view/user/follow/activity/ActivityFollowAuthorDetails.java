package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdut.douyintoutiao.R;

public class ActivityFollowAuthorDetails extends AppCompatActivity {

    private String userId;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_author_details);
        init();
        getUserInfo();
    }
    //初始化
    private void init(){
        textView = findViewById(R.id.author_details_textView);

    }
    //拿到被访问的userId
    private  void getUserInfo(){
        Intent in = getIntent();
       userId = in.getStringExtra("userId");
       textView.setText("获得了用户信息："+userId);
    }
}