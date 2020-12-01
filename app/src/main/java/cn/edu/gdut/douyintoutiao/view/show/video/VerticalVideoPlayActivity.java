package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import es.dmoral.toasty.Toasty;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * @author hayring
 * @date 2020.11.9 15:00
 */
public class VerticalVideoPlayActivity extends VideoPlayActivity {

    private String status;

    private String key;

    public static final String DEFAULT = "default";

    public static final String SEARCH = "search";

    public static final String FLASHING_NO_SUPPORT = "flashingNoSupport";

    private int errorCode = 0;



    private VideoStateAdapter adapter;

    List<Fragment> fragments = new ArrayList<>();

    List<MyNews> newses = new ArrayList<>();



    VerticalVideoViewModel verticalVideoPlayViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super 中已经设置了 setContentView
        super.onCreate(savedInstanceState);

        verticalVideoPlayViewModel = new ViewModelProvider(this,videoViewModelFactory).get(VerticalVideoViewModel.class);

        adapter = new VideoStateAdapter(this, fragments, newses);
        viewBinding.videoViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewBinding.videoViewPager.setAdapter(adapter);
        viewBinding.videoViewPager.registerOnPageChangeCallback(onPageChangeCallback);


        //数据 Live data
        verticalVideoPlayViewModel.getNewsesFromServer().observe(this, newsListObserver);
        verticalVideoPlayViewModel.getErrCode().observe(this, errorCodeObserver);


    }








    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (!"search".equals(type)) {
            status = FLASHING_NO_SUPPORT.equals(type) ? FLASHING_NO_SUPPORT : DEFAULT;
            //获取附加的数据
            List<MyNews> data;
            if ((data = (List<MyNews>) intent.getSerializableExtra("data")) != null) {
                adapter.addAllAndNotify(data);
                int currentIndex = getIntent().getIntExtra("index",-1);
                viewBinding.videoViewPager.setCurrentItem(currentIndex, false);
            } else {
                //视频列表有多少个视频了
                int preCount = intent.getIntExtra("count",-1);
                if (preCount != -1) {
                    verticalVideoPlayViewModel.getFollowVideoNewsByCount(preCount + 2);
                } else {
                    verticalVideoPlayViewModel.getFollowVideoNewsByCount();
                }
            }
        } else {
            status = SEARCH;
            key = intent.getStringExtra("key");
            verticalVideoPlayViewModel.searchVideoNews(key);
        }
    }




    ViewModelProvider.Factory videoViewModelFactory = new ViewModelProvider.Factory() {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                Constructor constructor = modelClass.getConstructor(Activity.class);
                return (T) constructor.newInstance(VerticalVideoPlayActivity.this);
            } catch (Exception e) {
                IllegalArgumentException ile = new IllegalArgumentException("" + modelClass + "is not" + VideoViewModel.class);
                ile.initCause(e);
                throw ile;
            }

        }
    };


    private final ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (FLASHING_NO_SUPPORT.equals(status)) return;
            if (state == 1) {
                if (adapter.getItemCount() == viewBinding.videoViewPager.getCurrentItem() + 1) {
                    if (errorCode == 0) {
                        Toasty.info(VerticalVideoPlayActivity.this, R.string.video_play_requesting, Toasty.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(VerticalVideoPlayActivity.this,
                                getString(R.string.video_play_request_fail) + errorCode,
                                Toasty.LENGTH_SHORT, true).show();
                        errorCode = 0;
                    }

                }
            } else if (state == 0) {
                if (adapter.getItemCount() == viewBinding.videoViewPager.getCurrentItem() + 2) {
                    if (DEFAULT.equals(status)) {
                        verticalVideoPlayViewModel.getMoreFollowVideoNews(newses.size());
                    } else if (SEARCH.equals(status)) {
                        verticalVideoPlayViewModel.searchMoreVideoNews(key, newses.size());
                    }
                }
            }
        }


        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            MyNews news = getNewses().get(position);
            //调用父类方法
            setCurrentNews(news);
        }
    };




    private final Observer<List<MyNews>> newsListObserver = new Observer<List<MyNews>>() {
        @Override
        public void onChanged(List<MyNews> myNews) {
            adapter.addAllAndNotify(myNews);
            if (adapter.getItemCount() == myNews.size() && -1 != getIntent().getIntExtra("index",-1)) {
                    int currentIndex = getIntent().getIntExtra("index",-1);
                    viewBinding.videoViewPager.setCurrentItem(currentIndex, false);
            }
        }
    };

    private final Observer<Integer> errorCodeObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer errorCode) {
            VerticalVideoPlayActivity.this.errorCode = errorCode;
        }
    };


    //getter setter 区

    public VideoStateAdapter getAdapter() {
        return adapter;
    }


    public List<MyNews> getNewses() {
        return newses;
    }


    public String getStatus() {
        return status;
    }

    public String getKey() {
        return key;
    }


//    /**
//     * 给 VideoList返回更新的数据
//     */
//    @Override
//    public void finish() {
//        Intent innerIntent = getIntent();
//        if (innerIntent.getSerializableExtra("data") != null) {
//            Intent intent = new Intent();
//            intent.putExtra("data", (Serializable) newses);
//            setResult(RESULT_OK, intent);
//        }
//        super.finish();
//    }
}