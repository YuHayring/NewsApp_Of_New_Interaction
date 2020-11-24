package cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo;

/**
 * @author hayring
 * @date 11/24/20 9:52 PM
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.lifecycle.ViewModelProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.jetbrains.annotations.NotNull;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityCommentFragmentContainerBinding;
import cn.edu.gdut.douyintoutiao.view.SingleFragmentContainerActivity;
import cn.edu.gdut.douyintoutiao.view.show.comment.CommentFragment;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsDetailViewModel;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 */
public class CommentFragmentContainerActivity extends SingleFragmentContainerActivity<CommentFragment> {

    ActivityCommentFragmentContainerBinding viewBinding;

    //评论 m
    private CommentModel model;
    //评论 vm
    private CommentViewModel viewModel;

    //新闻 id
    private String newsId;
    //用户 id
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCommentFragmentContainerBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
        fragment = createFragment();

        SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        userId = shp.getString("userId", "noContent");

        viewModel = new CommentViewModel(this);
        model = new CommentModel(viewModel);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).show(fragment).commitNow();
        viewBinding.buttonAddComment.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                new MaterialDialog.Builder(CommentFragmentContainerActivity.this)
                        .title("评论发送")
                        .input("请输入评论内容", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NotNull MaterialDialog dialog, CharSequence input) {
                                // Do something
                                String content = input.toString();
                                if (content.length() == 0) {
                                    Toasty.warning(CommentFragmentContainerActivity.this, "请输入内容", Toasty.LENGTH_SHORT).show();
                                    return;
                                }
                                model.postComment(newsId, userId, content);
                                Toasty.success(CommentFragmentContainerActivity.this, "发送成功", Toasty.LENGTH_SHORT, true).show();
                                viewBinding.buttonAddComment.collapse();
                            }
                        }).show();
            }

            @Override
            public void onMenuCollapsed() {

            }
        });
    }



    @Override
    protected CommentFragment createFragment() {
        newsId = getIntent().getStringExtra("newsId");
        fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("newsId", newsId);
        fragment.setArguments(bundle);
        return fragment;
    }
}