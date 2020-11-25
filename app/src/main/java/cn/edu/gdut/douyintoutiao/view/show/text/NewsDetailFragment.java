package cn.edu.gdut.douyintoutiao.view.show.text;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Toast;

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
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.ActivityFollowAuthorDetails;
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.guanzhu;
import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;

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
        // TODO: Use the ViewModel
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
        //设置tag
        binding.buttonSeeTags.setText(requireActivity().getIntent().getStringExtra("tag"));

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
                String authorId = requireActivity().getIntent().getStringExtra("authorId");
                SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
                String userId = shp.getString("userId", "noContent");
                Intent intent = new Intent(requireActivity(), ActivityFollowAuthorDetails.class);
                intent.putExtra("userId", authorId);
                intent.putExtra("followId", userId);
                startActivity(intent);
            }
        });

        //检查关注
        Boolean flag = viewModel.checkTagsFollowByNewsIdUserId1(newsId,userId);
        if(flag){
            //已关注，点击执行取消关注
            binding.actionGuanzhu.setIcon(yellow_guanzhu);
            binding.actionGuanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消关注警告窗口
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("取消关注?")
                            .setMessage("确定要取消关注NewsId为"+newsId+"的资讯吗")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.deleteTagsFollowByNewsIdUserId(newsId,userId);
                                    Toast.makeText(getContext(),"取消关注了"+newsId, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create().show();
                  //  binding.actionGuanzhu.setIcon(guanzhu);
                }
            });
        }else{
            binding.actionGuanzhu.setIcon(guanzhu);
            binding.actionGuanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.insertTagsFollowByNewsIdUserId(newsId,userId);
                   // binding.actionGuanzhu.setIcon(yellow_guanzhu);
                    Toast.makeText(getContext(),"关注了"+newsId, Toast.LENGTH_SHORT).show();
                }
            });
        }
        //关注
//        binding.actionGuanzhu.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                boolean flag = true;
//
//                if(flag){
//                    viewModel.insertTagsFollowByNewsIdUserId(newsId,userId);
//                    binding.actionGuanzhu.setIcon(yellow_guanzhu);
//                    Toast.makeText(getContext(),"关注了"+newsId, Toast.LENGTH_SHORT).show();
//                    flag=false;
//                }else{
//                    flag=true;
//                    //取消关注警告窗口
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setIcon(R.drawable.ic_baseline_warning_24)
//                            .setTitle("取消关注?")
//                            .setMessage("确定要取消关注"+newsId+"吗")
//                            .setNegativeButton("取消", null)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    viewModel.deleteTagsFollowByNewsIdUserId(newsId,userId);
//                                    Toast.makeText(getContext(),"取消关注了"+newsId, Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .create().show();
//                    binding.actionGuanzhu.setIcon(guanzhu);
//                }
//
//             //   Toasty.success(requireContext(), "关注！", Toasty.LENGTH_SHORT, true).show();
//            }
//        });
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