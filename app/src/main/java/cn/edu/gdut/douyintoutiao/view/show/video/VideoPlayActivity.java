package cn.edu.gdut.douyintoutiao.view.show.video;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.CallSuper;
import androidx.lifecycle.ViewModelProvider;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityVideoPlayBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.util.UIUtil;
import cn.edu.gdut.douyintoutiao.view.FullScreenActivity;
import cn.edu.gdut.douyintoutiao.view.show.WebViewActivity;
import cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo.CommentFragmentContainerActivity;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.ActivityFollowAuthorDetails;
import es.dmoral.toasty.Toasty;


/**
 * @author hayring
 * @date 11/27/20 4:07 PM
 */
public abstract class VideoPlayActivity extends FullScreenActivity {


    /**
     * 多久没操作悬浮窗就隐藏
     */
    private static final long FLOAT_BUTTON_HIDE_TIME = 5000L;

    /**
     * 当前正在播放的新闻
     */
    MyNews currentNews;

    VideoViewModel videoViewModel;

    /**
     * ViewBinding
     */
    protected ActivityVideoPlayBinding viewBinding;

    /**
     * 举报监听器
     */
    View.OnClickListener reportButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            removeVideo();
            Toasty.success(VideoPlayActivity.this, getString(R.string.video_play_reported), Toasty.LENGTH_SHORT, true).show();
        }
    };

    /**
     * 点赞监听器
     */
    View.OnClickListener likeButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            boolean flag = true;
            if(flag){
                viewBinding.actionDianzan.setIcon(R.drawable.red_dianzan);
                flag=false;
            }else{
                flag=true;
                viewBinding.actionDianzan.setIcon(R.drawable.red_dianzan);
            }
            Toasty.success(VideoPlayActivity.this, "点赞按钮！", Toasty.LENGTH_SHORT, true).show();

        }
    };

    /**
     * 不感兴趣监听器
     */
    View.OnClickListener uninterestedButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            removeVideo();
            Toasty.success(VideoPlayActivity.this, getString(R.string.video_play_uninterested), Toasty.LENGTH_SHORT, true).show();
        }
    };

    /**
     * 作者监听器
     */
    View.OnClickListener authorButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
//            Toasty.success(VideoPlayActivity.this, "作者！", Toasty.LENGTH_SHORT, true).show();
            String authorId = currentNews.getAuthor().get(0).getUserId();
            SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
            String userId = shp.getString("userId", "noContent");
            Intent intent = new Intent(VideoPlayActivity.this, ActivityFollowAuthorDetails.class);
            intent.putExtra("userId", authorId);
            intent.putExtra("isFollow",false);
            intent.putExtra("followId", userId);
            startActivity(intent);
        }
    };

    /**
     * 关注监听器
     */

    private Boolean isFollow;//初始化在onCreate
    View.OnClickListener followButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
                MyNews thisNews =  currentNews;
                SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
                String userId = shp.getString("userId", "noContent");

                if(isFollow){
                    videoViewModel.deleteTagsFollowByNewsIdUserId(thisNews.get_id(), userId);
                    viewBinding.actionGuanzhu.setIcon(R.drawable.guanzhu);
                    Toasty.success(VideoPlayActivity.this, getString(R.string.video_play_unFollowed) + currentNews.getNewsName(), Toasty.LENGTH_SHORT, true).show();
                    isFollow = false;
                }else {
                    videoViewModel.insertTagsFollowByNewsIdUserId(thisNews.get_id(), userId);
                    viewBinding.actionGuanzhu.setIcon(R.drawable.yellow_guanzhu);
                    Toasty.success(VideoPlayActivity.this, getString(R.string.video_play_followed) + currentNews.getNewsName(), Toasty.LENGTH_SHORT, true).show();
                    isFollow = true;
                }

        }
    };

    /**
     * 转换监听器
     */
    View.OnClickListener transFormButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
//            Toasty.success(VideoPlayActivity.this, "功能开发中", Toasty.LENGTH_SHORT, true).show();
            if (currentNews.getUrlOfTextOfVideo() == null
            || currentNews.getUrlOfTextOfVideo().isEmpty()) {
                Toasty.error(VideoPlayActivity.this, R.string.video_play_transform_no_support).show();
            } else {
                Intent intent = new Intent(VideoPlayActivity.this, WebViewActivity.class);
                intent.putExtra("url", currentNews.getUrlOfTextOfVideo());
                startActivity(intent);
            }
        }
    };

    /**
     * 评论监听器
     */
    View.OnClickListener commentButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(VideoPlayActivity.this, CommentFragmentContainerActivity.class);
            intent.putExtra("newsId", currentNews.get_id());
            startActivity(intent);
        }
    };


    FloatingActionsMenu.OnFloatingActionsMenuUpdateListener menuUpdateListener = new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
        @Override
        public void onMenuExpanded() {
            hiddenHandler.removeCallbacks(hideFloatButtonFunction);
        }

        @Override
        public void onMenuCollapsed() {
            hiddenHandler.postDelayed(hideFloatButtonFunction,  FLOAT_BUTTON_HIDE_TIME);
        }
    };


    View.OnClickListener floatButtonWakerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewBinding.fullScreenFloatButton.getVisibility() == View.INVISIBLE) {
                viewBinding.fullScreenFloatButton.setVisibility(View.VISIBLE);
                viewBinding.floatButtonWaker.setClickable(false);
                hiddenHandler.postDelayed(hideFloatButtonFunction, FLOAT_BUTTON_HIDE_TIME);
            }
        }
    };






    private final Handler hiddenHandler = new Handler(Looper.myLooper());

    private final Runnable hideFloatButtonFunction = new Runnable() {
        @Override
        public void run() {
            viewBinding.fullScreenFloatButton.setVisibility(View.INVISIBLE);
            viewBinding.floatButtonWaker.setClickable(true);
        }
    };




    @Override
    @CallSuper
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //隐藏计时器
        hideFloatButtonFunction.run();

    }


    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityVideoPlayBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
        //VM
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        //悬浮窗按钮监听器注册
        viewBinding.actionJinggao.setOnClickListener(reportButtonListener);
        viewBinding.actionDianzan.setOnClickListener(likeButtonListener);
        viewBinding.actionBuganxingqu.setOnClickListener(uninterestedButtonListener);
        viewBinding.actionZuozhe.setOnClickListener(authorButtonListener);
        viewBinding.actionGuanzhu.setOnClickListener(followButtonListener);
        viewBinding.actionZhuanhuan.setOnClickListener(transFormButtonListener);
        viewBinding.actionPinglun.setOnClickListener(commentButtonListener);
        viewBinding.multipleActions.setOnFloatingActionsMenuUpdateListener(menuUpdateListener);
        viewBinding.floatButtonWaker.setOnClickListener(floatButtonWakerListener);

        //标题 text view 显示大小修改
        int width = UIUtil.getScreenWidth(this) - UIUtil.dip2px(this,150) - 1;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewBinding.videoTitleTextView.getLayoutParams();
        layoutParams.width = width;
        viewBinding.videoTitleTextView.setLayoutParams(layoutParams);
        layoutParams = (RelativeLayout.LayoutParams) viewBinding.videoDescriptionTextView.getLayoutParams();
        layoutParams.width = width;
        viewBinding.videoDescriptionTextView.setLayoutParams(layoutParams);

        //初始化关注按钮
        viewBinding.actionGuanzhu.setIcon(R.drawable.guanzhu);
        isFollow = getIntent().getExtras().getBoolean("isFollow");
        if(isFollow){
            viewBinding.actionGuanzhu.setIcon(R.drawable.yellow_guanzhu);
        }
    }

    public MyNews getCurrentNews() {
        return currentNews;
    }

    @CallSuper
    public void setCurrentNews(MyNews currentNews) {
        this.currentNews = currentNews;
        //设置显示
        viewBinding.videoTitleTextView.setText(currentNews.getNewsName());
        viewBinding.videoDescriptionTextView.setText(currentNews.getNewsAbstract());
    }



    public abstract void removeVideo();





}
