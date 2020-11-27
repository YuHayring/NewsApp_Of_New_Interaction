package cn.edu.gdut.douyintoutiao.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.gdut.douyintoutiao.R;

/**
 * @author cypang
 * @date 2020年11月12日19:38:41
 */
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
//        NavController controller = Navigation.findNavController(this, R.id.fragment2);
//
//        NavigationUI.setupActionBarWithNavController(this, controller);

    }

    /* *//*
     * @desc 用于在顶栏中显示返回按钮
     *//*
    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this, R.id.fragment2);
        return controller.navigateUp();
    }*/
}