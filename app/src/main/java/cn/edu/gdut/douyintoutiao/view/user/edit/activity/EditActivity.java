package cn.edu.gdut.douyintoutiao.view.user.edit.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityEditBinding;
import cn.edu.gdut.douyintoutiao.databinding.FragmentUserMainBinding;
import cn.edu.gdut.douyintoutiao.view.user.edit.viewModel.EditViewModel;
import cn.edu.gdut.douyintoutiao.view.user.main.UserMainViewModel;

public class EditActivity extends AppCompatActivity {

    private String userId;
    private String describe;
    private ActivityEditBinding activityEditBinding;
    private EditViewModel editViewModel;

    private FragmentUserMainBinding mUserInfoBinding;

    UserMainViewModel userMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        getUserInfo();

        activityEditBinding= DataBindingUtil.setContentView(this, R.layout.activity_edit);
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        userMainViewModel = new UserMainViewModel(mUserInfoBinding,getApplicationContext());

        //设置按钮
        activityEditBinding.editSaveButton.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String describe =  activityEditBinding.editUserDescribeText.getText().toString().trim();
                    activityEditBinding.editSaveButton.setEnabled(!describe.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        activityEditBinding.editUserDescribeText.addTextChangedListener(textWatcher);

        activityEditBinding.editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                describe =activityEditBinding.editUserDescribeText.getText().toString();

                editViewModel.updateUserInfo(userId,describe);
               // Toast.makeText(EditActivity.this, "按钮被点击"+describe, Toast.LENGTH_SHORT).show();
                //有问题
               // userMainViewModel.userMainModel.getUser(userId);
            }
        });


    }


    //拿到当前的userId
    private  void getUserInfo(){
        Intent in = getIntent();
        userId = in.getStringExtra("userId");
    }
}