package cn.edu.gdut.douyintoutiao.tmp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.show.video.FullscreenActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    Button playVideoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        playVideoButton = findViewById(R.id.play_video_button);


        playVideoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        startActivity(intent);
    }
}