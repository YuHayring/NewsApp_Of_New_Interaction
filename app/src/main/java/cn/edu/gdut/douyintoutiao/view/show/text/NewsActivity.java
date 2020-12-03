package cn.edu.gdut.douyintoutiao.view.show.text;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.gdut.douyintoutiao.R;

public class NewsActivity extends AppCompatActivity implements NewsDetailFragment.OnFragmentListener{

    private Boolean isChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }


    /**
     * 拦截press事件，这里只对back键事件进行判断
     * @dengJL
     **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isChange == null){
            isChange = false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果关注列表发生了改变，则resultCode赋值为1
            if(isChange) {
                Intent i = new Intent();
                setResult(1, i);
            }
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onFragmentGetChange(Boolean change) {
        // System.out.println("isChange:"+change);
        isChange = change;
    }
}