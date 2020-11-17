package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.util.UIUtil;
import cn.edu.gdut.douyintoutiao.view.MainActivity;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayerFragment extends Fragment {

    private VideoPlayerViewModel mViewModel;

    private TextureView textureView;

    private Surface mSurface;

    private IjkMediaPlayer mPlayer;

    private Context context;

    boolean playing = false;

    boolean fullScreen;

    ViewPager2 viewPager2;

    int topToBottom;

    public VideoPlayerFragment(Context context, boolean fullScreen) {
        super();
        this.context = context;
        this.fullScreen = fullScreen;
        decorView = ((Activity)context).getWindow().getDecorView();


        if (fullScreen && !(context instanceof FullscreenActivity)) {
            otherSystemUIFlag = decorView.getSystemUiVisibility();
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            mySystemUIFlag |= flags;
        }
    }

    //我的页面 flag 存储
    int mySystemUIFlag;

    //正常页面的 flag 存储
    int otherSystemUIFlag;


    View decorView;

    View contentView;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.video_player_fragment, container, false);
        textureView = contentView.findViewById(R.id.video_player_window);
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VideoPlayerViewModel.class);
        textureView.setSurfaceTextureListener(listener);
        textureView.setOnClickListener(playListener);
    }


    private View.OnClickListener playListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (playing) {
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
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnVideoSizeChangedListener(videoSizeChangedListener);
            try {
                mPlayer.setDataSource("http://v.ysbang.cn/data/video/2015/rkb/2015rkb01.mp4");
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
                //TODO
            }
            mPlayer.prepareAsync();
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
        //恢复非全屏
        if (fullScreen && !(context instanceof FullscreenActivity)) {
            decorView.setSystemUiVisibility(otherSystemUIFlag);
        }
        //恢复布局
//        if (context instanceof MainActivity) {
//            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)viewPager2.getLayoutParams();
//            layoutParams.topToBottom = topToBottom;
//            layoutParams.topToTop = -1;
//            viewPager2.setLayoutParams(layoutParams);
//        }
        //暂停播放
        if (playing) {
            playing = false;
            mPlayer.pause();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //全屏
        if (fullScreen && !(context instanceof FullscreenActivity)) {
            decorView.setSystemUiVisibility(mySystemUIFlag);
        }
        //视频覆盖
//        if (context instanceof MainActivity) {
//            viewPager2 = (ViewPager2)contentView.getParent().getParent().getParent();
//            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)viewPager2.getLayoutParams();
//            topToBottom = layoutParams.topToBottom;
//            layoutParams.topToBottom = -1;
//            layoutParams.topToTop = 0;
//            viewPager2.setLayoutParams(layoutParams);
//        }
    }

    /**
     * 播放
     */
    public void play() {
        if (mPlayer != null) {
            mPlayer.start();
            playing = true;
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        mPlayer.pause();
        playing = false;
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
        int parentWidth = ((ConstraintLayout)textureView.getParent()).getWidth();
        int parentHeight = ((ConstraintLayout)textureView.getParent()).getHeight();

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

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textureView.getLayoutParams();
        layoutParams.width = videoWidth;
        layoutParams.height = videoHeight;
        Log.d("宽度",""+videoWidth);
        Log.d("高度",""+videoHeight);
//        layoutParams.verticalBias = 0.5f;
//        layoutParams.horizontalBias = 0.5f;
        textureView.setLayoutParams(layoutParams);

    }

}