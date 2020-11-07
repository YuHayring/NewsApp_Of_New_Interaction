package cn.edu.gdut.douyintoutiao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import cn.edu.gdut.douyintoutiao.view.UserMainActivity;

public class MainActivity extends AppCompatActivity {


    Button userMainPageEnter;
    Button resignPageEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userMainPageEnter = findViewById(R.id.user_main_page_enter);
        userMainPageEnter.setOnClickListener((view -> {
            Intent intent = new Intent();
            intent.setClass(this, UserMainActivity.class);
            MainActivity.this.startActivity(intent);
        }));


    }

    //@author:huqp
    //跳转登陆的一个方法
    private void enter(String page){
        switch (page){
            case "user_resign_page_enter": {
                resignPageEnter = findViewById(R.id.user_resign_page_enter);
                resignPageEnter.setOnClickListener((view -> {
                    Intent intent = new Intent();
                    intent.setClass(this, ResignActivity.class);
                    MainActivity.this.startActivity(intent);
                }));
            }
        }
    }
}