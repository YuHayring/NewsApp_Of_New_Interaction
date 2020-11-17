package cn.edu.gdut.douyintoutiao.view.show.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.afollestad.materialdialogs.MaterialDialog;

import org.jetbrains.annotations.NotNull;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.NewsDetailFragmentBinding;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsDetailViewModel;
import es.dmoral.toasty.Toasty;

public class NewsDetailFragment extends Fragment {


    private NewsDetailFragmentBinding binding;
    private WebSettings webSettings;
    private NewsDetailViewModel viewModel;

    public static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewsDetailFragmentBinding.inflate(inflater);
        String uri = requireActivity().getIntent().getStringExtra("uri");
        init(uri);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
        // 提取用户登录的id
        String newsId = requireActivity().getIntent().getStringExtra("newsId");
        SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        String userId = shp.getString("userId", "noContent");

        //评论页面跳转
        binding.buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("newsId", newsId);
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.commentFragment, bundle);
            }
        });
        //设置tag
        binding.buttonSeeTags.setText(requireActivity().getIntent().getStringExtra("tag"));
        //tag页面跳转
        binding.buttonSeeTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.normal(requireContext(), "你点击了tag按钮", Toasty.LENGTH_SHORT).show();
            }
        });

        binding.buttonPostComment.setOnClickListener((v) -> {
            new MaterialDialog.Builder(requireContext())
                    .title("评论发送")
                    .input("请输入评论内容", "", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NotNull MaterialDialog dialog, CharSequence input) {
                            // Do something
                            String content = input.toString();
                            if (content.length() == 0) {
                                Toasty.warning(requireContext(), "请输入内容", Toasty.LENGTH_SHORT).show();
                                return;
                            }
                            viewModel.postComment(newsId, userId, content);
                            Toasty.success(requireContext(), "发送成功", Toasty.LENGTH_SHORT, true).show();
                        }
                    }).show();
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(String uri) {
        webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        binding.webView.loadUrl(uri);
    }
}