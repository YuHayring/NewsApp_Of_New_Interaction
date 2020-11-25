package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.databinding.ActivityFullscreenBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.util.UIUtil;
import cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo.CommentFragmentContainerActivity;
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.guanzhu;
import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * @author hayring
 * @date 2020.11.9 15:00
 */
public class FullscreenActivity extends AppCompatActivity {


//    Fragment videoPlayerFragment;

    /**
     * 多久没操作悬浮窗就隐藏
     */
    private static final long FLOAT_BUTTON_HIDE_TIME = 5000l;



    private VideoStateAdapter adapter;

    List<Fragment> fragments = new ArrayList<>();

    List<MyNews> newses = new ArrayList<>();

    VideoPlayerModel videoPlayerModel;

    VideoPlayerViewModel videoPlayerViewModel;

    ActivityFullscreenBinding viewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        Log.d("systemUiVisibility pre",""+systemUiVisibility);
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_FULLSCREEN

                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        systemUiVisibility |= flags;
        decorView.setSystemUiVisibility(systemUiVisibility);
        Log.d("systemUiVisibility set",""+systemUiVisibility);

//        setContentView(R.layout.activity_fullscreen);
        //设置布局文件
        viewBinding = ActivityFullscreenBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());





        //标题 textview 显示大小修改
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewBinding.videoTitleTextView.getLayoutParams();
        layoutParams.width = UIUtil.getScreenWidth(this)
                - UIUtil.dip2px(this,150) - 1;
        viewBinding.videoTitleTextView.setLayoutParams(layoutParams);


//        controlView();
//        videoPlayerFragment = new VideoPlayerFragment(this);//MainFragment(this);
//
//
//        //显示 Fragment
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, videoPlayerFragment).show(videoPlayerFragment).commitAllowingStateLoss();

        videoPlayerModel = new VideoPlayerModel();
        videoPlayerViewModel = new VideoPlayerViewModel(this);
        videoPlayerViewModel.setVideoPlayerModel(videoPlayerModel);
        videoPlayerViewModel.setViewBinding(viewBinding);


        adapter = new VideoStateAdapter(this, fragments, newses);
        viewBinding.videoViewPager.setAdapter(adapter);
        viewBinding.videoViewPager.registerOnPageChangeCallback(videoPlayerViewModel.getOnPageChangeCallback());
        videoPlayerViewModel.setAdapter(adapter);




//        viewBinding.floatButton.bringToFront();



        //悬浮窗测试
        //举报按钮
        viewBinding.actionJinggao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(FullscreenActivity.this, "按了举报按钮！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //点赞按钮
        viewBinding.actionDianzan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(flag){
                    viewBinding.actionDianzan.setIcon(red_dianzan);
                    flag=false;
                }else{
                    flag=true;
                    viewBinding.actionDianzan.setIcon(red_dianzan);
                }
                Toasty.success(FullscreenActivity.this, "点赞按钮！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //不感兴趣
        viewBinding.actionBuganxingqu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(FullscreenActivity.this, "不感兴趣！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //作者
        viewBinding.actionZuozhe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(FullscreenActivity.this, "作者！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //关注
        viewBinding.actionGuanzhu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(flag){
                    viewBinding.actionGuanzhu.setIcon(yellow_guanzhu);
                    flag=false;
                }else{
                    flag=true;
                    viewBinding.actionGuanzhu.setIcon(guanzhu);
                }

                Toasty.success(FullscreenActivity.this, "关注！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //文字转视频
        viewBinding.actionZhuanhuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(FullscreenActivity.this, "功能开发中", Toasty.LENGTH_SHORT, true).show();
            }
        });

        //评论
        viewBinding.actionPinglun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullscreenActivity.this, CommentFragmentContainerActivity.class);
                intent.putExtra("newsId", newses.get(viewBinding.videoViewPager.getCurrentItem()).get_id());
                startActivity(intent);
            }
        });

        viewBinding.multipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                hiddenHandler.removeCallbacks(hideFloatButtonFunction);
            }

            @Override
            public void onMenuCollapsed() {
                hiddenHandler.postDelayed(hideFloatButtonFunction,  FLOAT_BUTTON_HIDE_TIME);
            }
        });

        viewBinding.floatButtonWaker.setOnClickListener(v -> {
            if (viewBinding.fullScreenFloatButton.getVisibility() == View.INVISIBLE) {
                viewBinding.fullScreenFloatButton.setVisibility(View.VISIBLE);
                viewBinding.floatButtonWaker.setClickable(false);
                hiddenHandler.postDelayed(hideFloatButtonFunction,  FLOAT_BUTTON_HIDE_TIME);
            }
        });

    }

    private Handler hiddenHandler = new Handler(Looper.myLooper());

    private Runnable hideFloatButtonFunction = new Runnable() {
        @Override
        public void run() {
            viewBinding.fullScreenFloatButton.setVisibility(View.INVISIBLE);
            viewBinding.floatButtonWaker.setClickable(true);
        }
    };










    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        videoPlayerModel.getVideoNews();
        hideFloatButtonFunction.run();

    }



    public VideoStateAdapter getAdapter() {
        return adapter;
    }


    public List<MyNews> getNewses() {
        return newses;
    }




}