package cn.edu.gdut.douyintoutiao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        NavController controller = Navigation.findNavController(this, R.id.fragment2);
        NavigationUI.setupActionBarWithNavController(this, controller);

    }

    /*
    * @desc 用于在顶栏中显示返回按钮
    */
    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this, R.id.fragment2);
        return controller.navigateUp();
    }
}