package cn.edu.gdut.douyintoutiao.view.user.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentUserMainBinding;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.FollowListActivity;
//import cn.edu.gdut.douyintoutiao.view.user.follow.FollowListActivity;

/**
 * @author hayring
 * @date 2020/11/7 21:25
 */
public class UserMainFragment extends Fragment {


    private final Context context;

    public UserMainFragment(Context context) {
        this.context = context;
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
        //View view = inflater.inflate(R.layout.fragment_user_main, container, false);

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

    }

    //跳转
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserInfoBinding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowListActivity.class);
                startActivity(intent);
            }
        });


    }


}
