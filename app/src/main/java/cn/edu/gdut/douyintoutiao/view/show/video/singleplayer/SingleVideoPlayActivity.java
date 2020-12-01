package cn.edu.gdut.douyintoutiao.view.show.video.singleplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.video.VerticalVideoViewModel;
import cn.edu.gdut.douyintoutiao.view.show.video.VideoPlayActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.VideoStateAdapter;

/**
 * @author hayring
 * @date 11/29/20 3:25 PM
 */
public class SingleVideoPlayActivity extends VideoPlayActivity {


    List<Fragment> fragments = new ArrayList<>();

    List<MyNews> singleNews = new ArrayList<>();

    VideoStateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super 中已经设置了 setContentView
        super.onCreate(savedInstanceState);


        adapter = new VideoStateAdapter(this, fragments, singleNews);
        viewBinding.videoViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewBinding.videoViewPager.setAdapter(adapter);
        //一个视频不允许滑动
        viewBinding.videoViewPager.setUserInputEnabled(false);

        viewBinding.videoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentNews(singleNews.get(0));
            }
        });




    }

    @Override
    public void removeVideo() {
        finish();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Intent intent = getIntent();
        MyNews news = (MyNews) intent.getSerializableExtra("news");
        adapter.addAndNotify(news);
    }
}
