package cn.edu.gdut.douyintoutiao.view.show.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import cn.edu.gdut.douyintoutiao.databinding.CommentFragmentBinding;
import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.view.show.comment.adapter.CommentAdapter;
import cn.edu.gdut.douyintoutiao.view.show.comment.viewmodel.CommentViewModel;

/**
 * @author cypang
 * @date 2020年11月13日11:05:44
 */
public class CommentFragment extends Fragment {

    private CommentViewModel mViewModel;
    private CommentAdapter adapter;
    private CommentFragmentBinding binding;

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CommentFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        // TODO: Use the ViewModel
        adapter = new CommentAdapter();
        binding.commentRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commentRecycleView.setAdapter(adapter);
        mViewModel.getAllDiscussData().observe(getViewLifecycleOwner(), new Observer<List<Discuss>>() {
            @Override
            public void onChanged(List<Discuss> discusses) {
                adapter.setDiscussList(discusses);
                adapter.notifyDataSetChanged();
            }
        });
    }

}