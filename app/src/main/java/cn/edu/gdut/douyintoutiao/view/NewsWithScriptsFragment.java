package cn.edu.gdut.douyintoutiao.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.NewsWithScriptsFragmentBinding;
import cn.edu.gdut.douyintoutiao.viewmodel.NewsWithScriptsViewModel;

/**
 * @author cypang
 * @date 2020年11月10日21:19:34
 * @description 文字资讯展示列表
 */
public class NewsWithScriptsFragment extends Fragment {

    private NewsWithScriptsViewModel mViewModel;
    private NewsWithScriptsFragmentBinding binding;

    public static NewsWithScriptsFragment newInstance() {
        return new NewsWithScriptsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.news_with_scripts_fragment);
        return inflater.inflate(R.layout.news_with_scripts_fragment, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsWithScriptsViewModel.class);
        // TODO: Use the ViewModel
        binding.setData(mViewModel);
        binding.setLifecycleOwner(this);
    }

}