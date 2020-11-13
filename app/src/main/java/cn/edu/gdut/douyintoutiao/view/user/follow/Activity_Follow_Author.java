package cn.edu.gdut.douyintoutiao.view.user.follow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityFirstBinding;
import cn.edu.gdut.douyintoutiao.databinding.ActivityFollowAuthorBinding;
import cn.edu.gdut.douyintoutiao.databinding.ActivityNewsBinding;
import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.NewsSAdapter;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowAuthorListAdapter;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;

public class Activity_Follow_Author extends AppCompatActivity {

    FollowAuthorListAdapter authorListAdapter;
    FollowAuthorViewModel viewModel;
    ActivityFollowAuthorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__follow__author);
        binding = ActivityFollowAuthorBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


       // authorListAdapter = new FollowAuthorListAdapter();
        viewModel = new ViewModelProvider(this).get(FollowAuthorViewModel.class);
        binding.recyclerViewAuthor.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewAuthor.setAdapter(authorListAdapter);
        viewModel.getFollowList().observe(this, new Observer< List< Follow > >() {
            @Override
            public void onChanged(List<Follow> follows) {
                authorListAdapter.setFollows(follows);
                authorListAdapter.notifyDataSetChanged();
            }
        });
    }
}