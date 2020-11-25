package cn.edu.gdut.douyintoutiao.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import cn.edu.gdut.douyintoutiao.R;

/**
 * @author hayring
 */
public abstract class SingleFragmentContainerActivity<T extends Fragment> extends AppCompatActivity {


    protected T fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_container);
        fragment = createFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).show(fragment).commitNow();
    }


    /**
     * 创建 Fragment
     */
    protected abstract T createFragment();


    public T getFragment() {
        return fragment;
    }
}