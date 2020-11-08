package cn.edu.gdut.douyintoutiao;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.gdut.douyintoutiao.view.LoginFragment;
import cn.edu.gdut.douyintoutiao.view.ResignFragment;
/**
 * @author hudp
 * @date 2020/11/7 21:43
 */
public class LoginAndResignActivity extends AppCompatActivity {
    private ResignFragment resignFragment;
    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_LoginAndResign);
    }

    //UI组件初始化与事件绑定
    private void bindViews() {

    }
}