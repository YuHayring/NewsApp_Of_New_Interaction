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
import cn.edu.gdut.douyintoutiao.entity.User;
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
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
                //上面那样吗是错的，那个用新的 activity 入栈，不是出栈返回
                //因为你上面调用了updateUserInfo，是更新到服务端，这里就不用返回本地数据
                // RESULT_OK 表示保存了，需要刷新，RESULT_OK 这个值没有特殊含义，是你赋予它含义，也可以赋予其他意思
                setResult(RESULT_OK);

                //如果返回本地数据，就要这样写：
//                Intent intent = new Intent();
//                User user = new User();
//                user.setUserName(name);
//                user.setUserDescription(describe);
//                //数据放进去
//                intent.putExtra("user",user);
//                // RESULT_OK 表示保存了，需要刷新，RESULT_OK 这个值没有特殊含义，是你赋予它含义，也可以赋予其他意思
//                setResult(RESULT_OK,intent);
//                //结束 activity
//                finish();

            }
        });

    }


    @Override
    public void finish() {
        //cancled 表示没有按保存键，UserMainFragment 不需要刷新
        setResult(RESULT_CANCELED);
        super.finish();
    }
}