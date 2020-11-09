package cn.edu.gdut.douyintoutiao.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import cn.edu.gdut.douyintoutiao.MainActivity;
import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentUserMainBinding;
import cn.edu.gdut.douyintoutiao.viewmodel.UserMainViewModel;

/**
 * @author hayring
 * @date 2020/11/7 21:25
 */
public class UserMainFragment extends Fragment {


    private MainActivity activity;

    public UserMainFragment(MainActivity activity) {
        this.activity = activity;
    }

    private FragmentUserMainBinding mUserInfoBinding;

    UserMainViewModel userMainViewModel = new UserMainViewModel();

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_user_main, container, false);

        mUserInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_main, container, false);
        mUserInfoBinding.setUserMainViewModel(userMainViewModel);

        return mUserInfoBinding.getRoot();

    }

    /**
     * 加载完成
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userMainViewModel.userMainModel.getUser("");

        //跳转到关注act
       // getView().findViewById(R.id.buttonFollow).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userMainFragment_to_followListActivity ));

    }
}
