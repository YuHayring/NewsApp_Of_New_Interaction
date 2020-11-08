package cn.edu.gdut.douyintoutiao.view;

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
import android.widget.Toast;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.viewmodel.VideoPlayerViewModel;
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

}