package cn.edu.gdut.douyintoutiao.view;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.viewmodel.VideoPlayerViewModel;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayerFragment extends Fragment {

    private VideoPlayerViewModel mViewModel;

    private TextureView textureView;

    private Surface mSurface;

    private IjkMediaPlayer mPlayer;

    private Context context;

    boolean playing = false;

    public VideoPlayerFragment(Context context) {
        super();
        this.context = context;
    }






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_player_fragment, container, false);
        textureView = view.findViewById(R.id.video_player_window);
        return view;
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
        }
    };


    private void createPlayer() {
        if (mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnVideoSizeChangedListener(videoSizeChangedListener);
            try {
                mPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
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
            textureView.setSurfaceTextureListener(null);
            textureView = null;
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
        pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        play();
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
        float devicePercent = (float) parentWidth / (float) parentHeight; //竖屏状态下宽度小与高度,求比

        if (videoWidth > videoHeight){ //判断视频的宽大于高,那么我们就优先满足视频的宽度铺满屏幕的宽度,然后在按比例求出合适比例的高度
            videoWidth = parentWidth;//将视频宽度等于设备宽度,让视频的宽铺满屏幕
            videoHeight = (int)(parentWidth*devicePercent);//设置了视频宽度后,在按比例算出视频高度

        }else {  //判断视频的高大于宽,那么我们就优先满足视频的高度铺满屏幕的高度,然后在按比例求出合适比例的宽度
            videoHeight = parentHeight;
            /**
             * 接受在宽度的轻微拉伸来满足视频铺满屏幕的优化
             */
            float videoPercent = (float) videoWidth / (float) videoHeight;//求视频比例 注意是宽除高 与 上面的devicePercent 保持一致
            float differenceValue = Math.abs(videoPercent - devicePercent);//相减求绝对值
            if (differenceValue < 0.3){ //如果小于0.3比例,那么就放弃按比例计算宽度直接使用屏幕宽度
                videoWidth = parentWidth;
            }else {
                videoWidth = (int)(videoWidth/devicePercent);//注意这里是用视频宽度来除
            }

        }

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textureView.getLayoutParams();
        layoutParams.width = videoWidth;
        layoutParams.height = videoHeight;
//        layoutParams.verticalBias = 0.5f;
//        layoutParams.horizontalBias = 0.5f;
        textureView.setLayoutParams(layoutParams);

    }

}