package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.FrameLayout;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayFragment extends Fragment {


    //重复使用
    private boolean recycleUse;

    private TextureView textureView;

    //绘制区域
    private Surface mSurface;

    //播放器
    private IjkMediaPlayer mPlayer;

    //context 引用
    private Context context;


    //当前新闻
    private MyNews news;

    public VideoPlayFragment() {}




    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        textureView = view.findViewById(R.id.video_player_window);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textureView.setSurfaceTextureListener(listener);
        textureView.setOnClickListener(playListener);
    }


    private View.OnClickListener playListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mPlayer.isPlaying()) {
                pause();
            } else {
                play();
            }

            Log.d("systemUiVisibility onClick",""+((AppCompatActivity)context).getWindow().getDecorView().getSystemUiVisibility());
        }
    };


    private void createPlayer() {
        if (mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnVideoSizeChangedListener(videoSizeChangedListener);
            if (news != null) {
                try {
//                mPlayer.setDataSource("http://v.ysbang.cn/data/video/2015/rkb/2015rkb01.mp4");
                    mPlayer.setDataSource(news.getNewsDetailUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Failed to set player src",Toast.LENGTH_LONG).show();
                    //TODO
                }
                mPlayer.prepareAsync();
            }
            Log.i("VideoPlayerFragment", "Player Created");
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }

    private TextureView.SurfaceTextureListener listener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            createPlayer();
            mSurface = new Surface(surface);
            mPlayer.setSurface(mSurface);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//            textureView.setSurfaceTextureListener(null);
//            textureView = null;
            if (recycleUse) {
                releasePlayer();
            }
            mSurface = null;
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
//        pause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        play();
    }

    /**
     * 播放
     */
    public void play() {
        if (mPlayer != null) {
            mPlayer.start();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        mPlayer.pause();
    }


    private IMediaPlayer.OnVideoSizeChangedListener videoSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
            changeVideoSize();
        }
    };


    /**
     * 修改预览View的大小,以用来适配屏幕
     */
    public void changeVideoSize() {
        int videoWidth = mPlayer.getVideoWidth();
        int videoHeight = mPlayer.getVideoHeight();
        int parentWidth = ((FrameLayout)textureView.getParent()).getWidth();
        int parentHeight = ((FrameLayout)textureView.getParent()).getHeight();

        //下面进行求屏幕比例,因为横竖屏会改变屏幕宽度值,所以为了保持更小的值除更大的值.
        float parentPercent = (float) parentWidth / (float) parentHeight; //不一定是整个屏幕，是父容器所允许的区域的比例
        float videoPercent = (float) videoWidth / (float) videoHeight;//求视频比例 注意是宽除高 与 上面的devicePercent 保持一致

        if (videoWidth > videoHeight ) {
            if (parentWidth < parentHeight || videoPercent > parentPercent) {
                //视频的宽大于高，父容器的宽小于高,优先满足视频的宽度铺满屏幕的宽度
                //或者都宽，但是视频更宽，也要满足视频的宽度铺满屏幕的宽度
                videoWidth = parentWidth;//将视频宽度等于父容器宽度,让视频的宽按比例换算
                videoHeight = (int) (parentWidth / videoPercent);//设置了视频宽度后,在按比例算出视频高度
            } else {
                videoHeight = parentHeight;//将视频高度等于父容器高度,让视频的高按比例换算
                videoWidth = (int) (parentHeight * videoPercent); //设置了视频高度后,在按比例算出视频宽度
            }
        }else {
            if (parentWidth > parentHeight || videoPercent > parentPercent) {
                //视频的宽小于高，父容器的宽大于高,优先满足视频的高度铺满屏幕的高度
                //或者都高，但是视频更高，也要满足视频的高度铺满屏幕的高度
                videoHeight = parentHeight;//将视频高度等于父容器高度,让视频的高按比例换算
                videoWidth = (int) (parentHeight * videoPercent); //设置了视频高度后,在按比例算出视频宽度
            } else {
                videoWidth = parentWidth;//将视频宽度等于父容器宽度,让视频的宽按比例换算
                videoHeight = (int) (parentWidth / videoPercent);//设置了视频宽度后,在按比例算出视频高度
            }

        }

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textureView.getLayoutParams();
        layoutParams.width = videoWidth;
        layoutParams.height = videoHeight;
        Log.d("宽度",""+videoWidth);
        Log.d("高度",""+videoHeight);
//        layoutParams.verticalBias = 0.5f;
//        layoutParams.horizontalBias = 0.5f;
        textureView.setLayoutParams(layoutParams);

    }





    public MyNews getMyNews() {
        return news;
    }


    /**
     * 仅限于更换 url 时使用
     * @param news
     */
    public void setMyNews(MyNews news) {
        this.news = news;
        if (mPlayer != null) {
            if (!recycleUse) {
                throw new IllegalStateException("The player is not supported to set datasource");
            }
            try {
//                mPlayer.setDataSource("http://v.ysbang.cn/data/video/2015/rkb/2015rkb01.mp4");
                mPlayer.setDataSource(news.getNewsDetailUrl());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,"Failed to set player src",Toast.LENGTH_LONG).show();
                //TODO
            }
            mPlayer.prepareAsync();
        }

    }


    public void setRecycleUse(boolean recycleUse) {
        this.recycleUse = recycleUse;
    }
}