package cn.edu.gdut.douyintoutiao.view.show.text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityNewsBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.News;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.NewsSAdapter;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;

public class NewsActivity extends AppCompatActivity {

    ActivityNewsBinding binding;
    NewsViewModel viewModel;
    NewsSAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        binding = ActivityNewsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adapter = new NewsSAdapter();
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding.recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewNews.setAdapter(adapter);
        viewModel.getAllNewsLive().observe(this, new Observer<List<MyNews>>() {
            @Override
            public void onChanged(List<MyNews> news) {
                adapter.setNewsList(news);
                adapter.notifyDataSetChanged();
            }
        });
    }



}