package cn.edu.gdut.douyintoutiao.view.show.text;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import cn.edu.gdut.douyintoutiao.R;

public class NewsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        NavController controller = Navigation.findNavController(this, R.id.fragmentNewsList);
        NavigationUI.setupActionBarWithNavController(this, controller);
    }

    /*
     * @desc 用于在顶栏中显示返回按钮
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this, R.id.fragmentNewsList);
        return controller.navigateUp();
    }


}