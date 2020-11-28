package cn.edu.gdut.douyintoutiao.view.show.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.CallSuper;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.FullScreenActivity;
import cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo.CommentFragmentContainerActivity;
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;

/**
 * @author hayring
 * @date 11/27/20 4:07 PM
 */
public class VideoPlayActivity extends FullScreenActivity {


    /**
     * 多久没操作悬浮窗就隐藏
     */
    private static final long FLOAT_BUTTON_HIDE_TIME = 5000l;

    /**
     * 当前正在播放的新闻
     */
    MyNews currentNews;

    /**
     * 举报按钮
     */
    FloatingActionButton reportButton;


    /**
     * 点赞按钮
     */
    FloatingActionButton likeButton;

    /**
     * 不感兴趣按钮
     */
    FloatingActionButton uninteredtedButton;

    /**
     * 作者按钮
     */
    FloatingActionButton authorButton;

    /**
     * 关注按钮
     */
    FloatingActionButton followButton;

    /**
     * 转换按钮
     */
    FloatingActionButton transformButton;

    /**
     * 评论按钮
     */
    FloatingActionButton commentButton;

    /**
     * 悬浮窗整体
     */
    RelativeLayout fullScreenFloatButton;

    /**
     * 悬浮窗唤醒区域
     */
    View floatButtonWaker;

    /**
     * 举报监听器
     */
    View.OnClickListener ReportButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toasty.success(VideoPlayActivity.this, "按了举报按钮！", Toasty.LENGTH_SHORT, true).show();
        }
    };

    /**
     * 点赞监听器
     */
    View.OnClickListener LikeButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v instanceof FloatingActionButton) {
                boolean flag = true;
                if(flag){
                    ((FloatingActionButton)v).setIcon(R.drawable.red_dianzan);
                    flag=false;
                }else{
                    flag=true;
                    ((FloatingActionButton)v).setIcon(R.drawable.red_dianzan);
                }
                Toasty.success(VideoPlayActivity.this, "点赞按钮！", Toasty.LENGTH_SHORT, true).show();
            } else {
                throw new IllegalStateException(v.toString() + "is not an instance of" + FloatingActionButton.class.toString());
            }
        }
    };


    View.OnClickListener UninterestedButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toasty.success(VideoPlayActivity.this, "不感兴趣！", Toasty.LENGTH_SHORT, true).show();
        }
    };

    View.OnClickListener AuthorButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toasty.success(VideoPlayActivity.this, "作者！", Toasty.LENGTH_SHORT, true).show();
        }
    };

    View.OnClickListener FollowButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v instanceof FloatingActionButton) {

//                String userId = "5fa9f7f63d18b202258b5daf";
//                verticalVideoPlayerViewModel.insertTagsFollowByNewsIdUserId(newses.get(0).get_id(), userId);
//                // binding.actionGuanzhu.setIcon(yellow_guanzhu);
//                v.setIcon(yellow_guanzhu);
                Toasty.success(VideoPlayActivity.this, "关注了" + currentNews.getNewsName(), Toasty.LENGTH_SHORT, true).show();
            }
        }
    };

    View.OnClickListener TransFormButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toasty.success(VideoPlayActivity.this, "功能开发中", Toasty.LENGTH_SHORT, true).show();
        }
    };

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






    private Handler hiddenHandler = new Handler(Looper.myLooper());

    private Runnable hideFloatButtonFunction = new Runnable() {
        @Override
        public void run() {
            fullScreenFloatButton.setVisibility(View.INVISIBLE);
            floatButtonWaker.setClickable(true);
        }
    };


    @Override
    @CallSuper
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        //隐藏计时器
        hideFloatButtonFunction.run();

    }















    public MyNews getCurrentNews() {
        return currentNews;
    }

    public void setCurrentNews(MyNews currentNews) {
        this.currentNews = currentNews;
    }
}
