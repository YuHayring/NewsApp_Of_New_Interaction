package cn.edu.gdut.douyintoutiao.view.show.text;

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
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.guanzhu;
import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;
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
        binding.floatButton.bringToFront();

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


        //悬浮窗测试
        //举报按钮
        binding.actionJinggao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(requireContext(), "按了举报按钮！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //点赞按钮
        binding.actionDianzan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(flag){
                    binding.actionDianzan.setIcon(red_dianzan);
                    flag=false;
                }else{
                    flag=true;
                    binding.actionDianzan.setIcon(red_dianzan);
                }
                Toasty.success(requireContext(), "点赞按钮！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //不感兴趣
        binding.actionBuganxingqu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(requireContext(), "不感兴趣！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //作者
        binding.actionZuozhe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(requireContext(), "作者！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //关注
        binding.actionGuanzhu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if(flag){
                    binding.actionGuanzhu.setIcon(yellow_guanzhu);
                    flag=false;
                }else{
                    flag=true;
                    binding.actionGuanzhu.setIcon(guanzhu);
                }

                Toasty.success(requireContext(), "关注！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //文字转视频
        binding.actionZhuanhuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(requireContext(), "文字转视频！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //评论
        binding.actionPinglun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(requireContext(), "评论！", Toasty.LENGTH_SHORT, true).show();
                Bundle bundle = new Bundle();
                bundle.putString("newsId", getActivity().getIntent().getStringExtra("newsId"));
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