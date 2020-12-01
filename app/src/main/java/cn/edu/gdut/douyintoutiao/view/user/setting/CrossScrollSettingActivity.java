package cn.edu.gdut.douyintoutiao.view.user.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityCrossScrollSettingBinding;

public class CrossScrollSettingActivity extends AppCompatActivity {

    ActivityCrossScrollSettingBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCrossScrollSettingBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
    }
}