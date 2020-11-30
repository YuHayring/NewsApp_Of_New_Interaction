package cn.edu.gdut.douyintoutiao.view.show.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.databinding.ActivityVerticalVideoPlayBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.util.UIUtil;
import cn.edu.gdut.douyintoutiao.view.FullScreenActivity;
import cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo.CommentFragmentContainerActivity;
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * @author hayring
 * @date 2020.11.9 15:00
 */
public class VerticalVideoPlayActivity extends FullScreenActivity {


//    Fragment videoPlayerFragment;

    /**
     * 多久没操作悬浮窗就隐藏
     */
    private static final long FLOAT_BUTTON_HIDE_TIME = 5000l;


    private String status;

    private String key;

    public static final String DEFAULT = "default";

    public static final String SEARCH = "search";



    private VideoStateAdapter adapter;

    List<Fragment> fragments = new ArrayList<>();

    List<MyNews> newses = new ArrayList<>();

    VideoPlayerModel videoPlayerModel;

    VerticalVideoPlayerViewModel verticalVideoPlayerViewModel;

    ActivityVerticalVideoPlayBinding viewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityVerticalVideoPlayBinding.inflate(LayoutInflater.from(this));
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

        videoPlayerModel = VideoPlayerModel.getInstance();
        verticalVideoPlayerViewModel = new VerticalVideoPlayerViewModel(this);
        verticalVideoPlayerViewModel.setVideoPlayerModel(videoPlayerModel);
        verticalVideoPlayerViewModel.setViewBinding(viewBinding);


        adapter = new VideoStateAdapter(this, fragments, newses);
        viewBinding.videoViewPager.setAdapter(adapter);
        viewBinding.videoViewPager.registerOnPageChangeCallback(verticalVideoPlayerViewModel.getOnPageChangeCallback());
        verticalVideoPlayerViewModel.setAdapter(adapter);




//        viewBinding.floatButton.bringToFront();



        //悬浮窗测试
        //举报按钮
        viewBinding.actionJinggao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(VerticalVideoPlayActivity.this, "按了举报按钮！", Toasty.LENGTH_SHORT, true).show();
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
                Toasty.success(VerticalVideoPlayActivity.this, "点赞按钮！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //不感兴趣
        viewBinding.actionBuganxingqu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(VerticalVideoPlayActivity.this, "不感兴趣！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //作者
        viewBinding.actionZuozhe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(VerticalVideoPlayActivity.this, "作者！", Toasty.LENGTH_SHORT, true).show();
            }
        });

        //关注
        viewBinding.actionGuanzhu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userId = "5fa9f7f63d18b202258b5daf";

                verticalVideoPlayerViewModel.insertTagsFollowByNewsIdUserId(newses.get(0).get_id(),userId);
                // binding.actionGuanzhu.setIcon(yellow_guanzhu);
                viewBinding.actionGuanzhu.setIcon(yellow_guanzhu);
                Toasty.success(VerticalVideoPlayActivity.this, "关注了"+newses.get(0).getNewsName(), Toasty.LENGTH_SHORT, true).show();
            }
        });
        //文字转视频
        viewBinding.actionZhuanhuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(VerticalVideoPlayActivity.this, "功能开发中", Toasty.LENGTH_SHORT, true).show();
            }
        });

        //评论
        viewBinding.actionPinglun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerticalVideoPlayActivity.this, CommentFragmentContainerActivity.class);
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
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type == null) {
            status = DEFAULT;
            videoPlayerModel.getVideoNews();
        } else if ("search".equals(type)){
            status = SEARCH;
            key = intent.getStringExtra("key");
            videoPlayerModel.searchVideoNews(key);
        }


        //隐藏计时器
        hideFloatButtonFunction.run();

    }



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
}