package cn.edu.gdut.douyintoutiao.view.show.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import cn.edu.gdut.douyintoutiao.databinding.CommentFragmentBinding;
import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.view.show.comment.adapter.CommentAdapter;
import cn.edu.gdut.douyintoutiao.view.show.comment.viewmodel.CommentViewModel;
import es.dmoral.toasty.Toasty;

/**
 * @author cypang
 * @date 2020年11月13日11:05:44
 */
public class CommentFragment extends Fragment {

    private CommentViewModel mViewModel;
    private CommentAdapter adapter;
    private CommentFragmentBinding binding;

    /**
     * 新闻 id
     */
    private String newsId;
    private String userId;
    private static final String TAG = "comment";

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CommentFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        assert getArguments() != null;
        newsId = getArguments().getString("newsId");
        userId = getArguments().getString("userId");
        binding.commentRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter(requireContext());
        adapter.showEmptyView(true);
        binding.commentRecycleView.setAdapter(adapter);
        mViewModel.getAllDiscussData(newsId).observe(getViewLifecycleOwner(), discusses -> {
            adapter.setDiscussList(discusses);
            adapter.notifyDataSetChanged();
            binding.comentRefreshLayout.setRefreshing(false);
        });
        SwipeRefreshLayout.OnRefreshListener listener = () -> mViewModel.getAllDiscussData(newsId);
        binding.comentRefreshLayout.post(() -> binding.comentRefreshLayout.setRefreshing(true));
        listener.onRefresh();
        binding.comentRefreshLayout.setOnRefreshListener(listener);
        binding.postCommentButton.setOnClickListener(v -> new MaterialDialog.Builder(requireContext())
                .title("评论发送")
                .input("请输入评论内容", "", (dialog, input) -> {
                    String content = input.toString();
                    if (content.length() == 0) {
                        Toasty.warning(requireContext(), "请输入内容", Toasty.LENGTH_SHORT).show();
                        return;
                    }
                    mViewModel.postComment(newsId, userId, content);
                    Toasty.success(requireContext(), "发送成功", Toasty.LENGTH_SHORT, true).show();
                }).show());
    }


    public void updateComment() {
        mViewModel.getAllDiscussData(newsId).observe(getViewLifecycleOwner(), new Observer<List<Discuss>>() {
            @Override
            public void onChanged(List<Discuss> discusses) {
                adapter.setDiscussList(discusses);
                adapter.notifyDataSetChanged();
            }
        });
    }

}