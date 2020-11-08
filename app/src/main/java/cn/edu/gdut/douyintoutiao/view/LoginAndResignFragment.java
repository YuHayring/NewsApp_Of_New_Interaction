package cn.edu.gdut.douyintoutiao.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import cn.edu.gdut.douyintoutiao.R;
/**
 * @author hudp
 * @date 2020/11/7 21:43
 */
public class LoginAndResignFragment extends Fragment {
    private String type;
    public LoginAndResignFragment(String type){
        this.type = type;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =null;
        if(this.type=="resign"){
            v=inflater.inflate(R.layout.fragment_resign, container, false);
            ResignView();
        }else {
            v=inflater.inflate(R.layout.fragment_login, container, false);
            LoginView();
        }
        return v;
    }
    //注册页面需要的数据在这注入
    public void ResignView(){

    }
    //登陆页面需要的数据在这注入
    public void LoginView(){

    }
}
