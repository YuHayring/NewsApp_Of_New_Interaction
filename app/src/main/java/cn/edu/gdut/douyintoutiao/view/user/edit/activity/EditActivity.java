package cn.edu.gdut.douyintoutiao.view.user.edit.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityEditBinding;
import cn.edu.gdut.douyintoutiao.view.MainActivity;
import cn.edu.gdut.douyintoutiao.view.user.edit.viewModel.EditViewModel;

public class EditActivity extends AppCompatActivity {

    private String userId;
    private String name;
    private String describe;
    private ActivityEditBinding activityEditBinding;
    private EditViewModel editViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        userId = shp.getString("userId", "noContent");

        activityEditBinding= DataBindingUtil.setContentView(this, R.layout.activity_edit);
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);

        //设置按钮
        activityEditBinding.editSaveButton.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String describe =  activityEditBinding.editUserDescribeText.getText().toString().trim();
                    String name = activityEditBinding.editUserNameText.getText().toString().trim();
                    activityEditBinding.editSaveButton.setEnabled(!describe.isEmpty()||!name.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        activityEditBinding.editUserDescribeText.addTextChangedListener(textWatcher);

        activityEditBinding.editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                describe = activityEditBinding.editUserDescribeText.getText().toString();
                name = activityEditBinding.editUserNameText.getText().toString();
                editViewModel.updateUserInfo(userId,name,describe);
               Toast.makeText(EditActivity.this, "编辑信息已提交！", Toast.LENGTH_SHORT).show();
                //刷新页面,待修改
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }



}