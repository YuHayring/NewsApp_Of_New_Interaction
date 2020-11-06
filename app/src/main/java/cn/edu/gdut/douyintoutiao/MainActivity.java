package cn.edu.gdut.douyintoutiao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import cn.edu.gdut.douyintoutiao.view.UserMainActivity;

public class MainActivity extends AppCompatActivity {


    Button userMainPageEnter;

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
}