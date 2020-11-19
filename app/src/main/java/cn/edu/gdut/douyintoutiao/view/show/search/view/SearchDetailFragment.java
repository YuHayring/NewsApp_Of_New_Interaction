package cn.edu.gdut.douyintoutiao.view.show.search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cn.edu.gdut.douyintoutiao.databinding.SearchDetailFragmentBinding;
import cn.edu.gdut.douyintoutiao.view.show.search.viewmodel.SearchDetailViewModel;

public class SearchDetailFragment extends Fragment {

    private SearchDetailViewModel mViewModel;
    private SearchDetailFragmentBinding binding;

    public static SearchDetailFragment newInstance() {
        return new SearchDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SearchDetailFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}