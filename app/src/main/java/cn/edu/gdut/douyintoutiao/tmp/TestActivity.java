package cn.edu.gdut.douyintoutiao.tmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.FullscreenActivity;

public class TestActivity extends AppCompatActivity {


    Button playVideoButton;;

    Button buttonToNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        playVideoButton = findViewById(R.id.play_video_button);

        buttonToNews = findViewById(R.id.buttonToNews);

        buttonToNews.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        });

        playVideoButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, FullscreenActivity.class);
            startActivity(intent);
        });

    }
}