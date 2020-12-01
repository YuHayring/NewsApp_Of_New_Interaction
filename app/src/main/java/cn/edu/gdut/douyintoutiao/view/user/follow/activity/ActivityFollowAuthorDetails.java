package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorDetailsViewModel;

public class ActivityFollowAuthorDetails extends AppCompatActivity  {

    private String userId;
    private String followId;
    private Boolean isFollow;

    private TextView textView;
    private FollowAuthorDetailsViewModel followAuthorDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_author_details);
        getUserInfo();

    }

    //拿到被访问的userId
    private  void getUserInfo(){
        Intent in = getIntent();
       userId = in.getStringExtra("userId");
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

    public Boolean getFollow() {
        Intent in = getIntent();
        isFollow = in.getExtras().getBoolean("isFollow");
        return isFollow;
    }


}