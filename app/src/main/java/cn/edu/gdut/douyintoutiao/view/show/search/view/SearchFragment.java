package cn.edu.gdut.douyintoutiao.view.show.search.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.edu.gdut.douyintoutiao.databinding.SearchFragmentBinding;
import es.dmoral.toasty.Toasty;

/**
 * @author cypang
 */
public class SearchFragment extends Fragment {


    private SearchFragmentBinding binding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //默认展开搜索框并且打开输入键盘
        binding.searchView.onActionViewExpanded();
        //显示搜索提示
        binding.searchView.setQueryHint("请输入你要搜索的信息");
        //显示搜索按键
        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    Toasty.warning(requireContext(), "请输入搜索内容").show();
                    return false;
                }
                Toasty.normal(requireContext(), query, Toasty.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}