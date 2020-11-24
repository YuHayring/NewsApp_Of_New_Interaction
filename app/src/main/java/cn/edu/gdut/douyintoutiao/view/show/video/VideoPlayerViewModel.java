package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityFullscreenBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 * @date 2020/11/7 16:09
 */
public class VideoPlayerViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public interface OnVideoGotCallBack {
        void onSuccess(List<MyNews> newses);

        void onFaile(String errorInfo);
    }


    int errorCode = 0;

    public FullscreenActivity activity;

    private VideoPlayerModel videoPlayerModel;

    private VideoStateAdapter adapter;

    ActivityFullscreenBinding viewBinding;


    private OnVideoGotCallBack videoGotCallBack = new OnVideoGotCallBack() {
        @Override
        public void onSuccess(List<MyNews> newses) {
            activity.getAdapter().addAllAndNotify(newses);
        }

        @Override
        public void onFaile(String errorInfo) {

        }
    };

    public VideoPlayerViewModel(FullscreenActivity activity) {
        this.activity = activity;
    }

    public void setVideoPlayerModel(VideoPlayerModel videoPlayerModel) {
        this.videoPlayerModel = videoPlayerModel;
        videoPlayerModel.setOnVideoGotHandler(videoGotHandler);
    }


    private Handler videoGotHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 200) {
                List<MyNews> newses = (List) msg.obj;
                activity.getAdapter().addAllAndNotify(newses);
            } else {
                errorCode = msg.arg1;
            }
        }
    };

    private ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 1) {
                if (adapter.getItemCount() == viewBinding.videoViewPager.getCurrentItem() + 1) {
                    if (errorCode == 0) {
                        Toasty.error(activity, R.string.video_play_requesting, Toasty.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(activity, activity.getString(R.string.video_play_request_fail) + errorCode, Toasty.LENGTH_SHORT, true).show();
                        errorCode = 0;
                    }

                }
            } else if (state == 0) {
                if (adapter.getItemCount() == viewBinding.videoViewPager.getCurrentItem() + 2) {
                    videoPlayerModel.getMoreVideoNews(activity.newses.size());
                }
            }
        }


        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            String title = activity.getNewses().get(position).getNewsName() + "\n"
                    + activity.getNewses().get(position).getNewsAbstract();
            viewBinding.videoTitleTextView.setText(title);
        }
    };


    public ViewPager2.OnPageChangeCallback getOnPageChangeCallback() {
        return onPageChangeCallback;
    }

    public void setAdapter(VideoStateAdapter adapter) {
        this.adapter = adapter;
    }

    public void setViewBinding(ActivityFullscreenBinding viewBinding) {
        this.viewBinding = viewBinding;
    }
}