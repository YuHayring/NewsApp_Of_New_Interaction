package cn.edu.gdut.douyintoutiao.view.show.text;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.NewsDetailFragmentBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsDetailViewModel;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.ActivityFollowAuthorDetails;
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;

public class NewsDetailFragment extends Fragment  {

    private NewsDetailFragmentBinding binding;
    private WebSettings webSettings;
    private NewsDetailViewModel viewModel;

    private OnFragmentListener mOnFragmentListener;


    public static NewsDetailFragment newInstance(String uri, String newsId, String tag, String authorId) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString("tag", tag);
        args.putString("uri", uri);
        args.putString("newsId", newsId);
        args.putString("authorId", authorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewsDetailFragmentBinding.inflate(inflater);
        String uri = ((MyNews)requireActivity().getIntent().getSerializableExtra("news")).getNewsDetailUrl();
        init(uri);
        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
        // 提取用户登录的id
        String newsId = ((MyNews)requireActivity().getIntent().getSerializableExtra("news")).get_id();
        SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        String userId = shp.getString("userId", "noContent");
        binding.floatButton.bringToFront();
        //设置tag
        binding.buttonSeeTags.setText(((MyNews)requireActivity().getIntent().getSerializableExtra("news")).getTag());

        //悬浮窗测试
        //举报按钮
        binding.actionJinggao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.success(requireContext(), "按了举报按钮！", Toasty.LENGTH_SHORT, true).show();
            }
        });
        //悬浮窗组件的状态
        final boolean[] float_botton_flag = {true,true};
        //点赞按钮
        binding.actionDianzan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyNews news = new MyNews();
                news.set_id(((MyNews)requireActivity().getIntent().getSerializableExtra("news")).get_id());
                //图标的变化
                if(float_botton_flag[0]){
                    //图标变红
                    binding.actionDianzan.setIcon(red_dianzan);
                    float_botton_flag[0] =false;
                    viewModel.newsLike(news);
                }else{
                    float_botton_flag[0] =true;
                    binding.actionDianzan.setIcon(dianzan);
                    viewModel.newsUnLike(news);
                }


            }
        });
        //不感兴趣
        binding.actionBuganxingqu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
        //作者
        binding.actionZuozhe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String authorId = ((MyNews)requireActivity().getIntent().getSerializableExtra("news")).getAuthor().get(0).getUserId();
                SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
                String userId = shp.getString("userId", "noContent");
                Intent intent = new Intent(requireActivity(), ActivityFollowAuthorDetails.class);
                intent.putExtra("userId", authorId);
                intent.putExtra("isFollow",false);
                intent.putExtra("followId", userId);
                startActivity(intent);
            }
        });
        //关注按钮
            binding.actionGuanzhu.setIcon(R.drawable.guanzhu);
        final Boolean[] isFollow = {requireActivity().getIntent().getExtras().getBoolean("isFollow")};
        String newsName = requireActivity().getIntent().getStringExtra("newsName");
            if(isFollow[0]){ binding.actionGuanzhu.setIcon(yellow_guanzhu); }
            binding.actionGuanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(!isFollow[0]){
                    viewModel.insertTagsFollowByNewsIdUserId(newsId,userId);
                       Toasty.success(getContext(), getString(R.string.toasty_follow_start) +newsName , Toasty.LENGTH_SHORT, true).show();
                       binding.actionGuanzhu.setIcon(yellow_guanzhu);
                        isFollow[0] = true;
                       //关注列表发生改变，传信息到act
                       if(mOnFragmentListener != null){
                           mOnFragmentListener.onFragmentGetChange(true);
                       }
                   }
                   else {
                       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                       builder.setIcon(R.drawable.ic_baseline_warning_24)
                               .setTitle(getString(R.string.alertDialog_follow_title))
                               .setMessage(getString(R.string.alertDialog_follow_message_start)+newsName+getString(R.string.alertDialog_follow_message_end))
                               .setNegativeButton(getString(R.string.alertDialog_follow_navigationButton), null)
                               .setPositiveButton(R.string.alertDialog_follow_positiveButton, new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       viewModel.deleteTagsFollowByNewsIdUserId(newsId,userId);
                                       Toasty.success(getContext(), getString(R.string.toasty_unFollow_start) +newsName , Toasty.LENGTH_SHORT, true).show();
                                       binding.actionGuanzhu.setIcon(R.drawable.guanzhu);
                                       isFollow[0] = false;
                                       //关注列表发生改变，传信息到act
                                       if(mOnFragmentListener != null){
                                           mOnFragmentListener.onFragmentGetChange(true);
                                       }
                                   }
                               })
                               .create().show();
                   }
                }
            });


        //文字转视频
        binding.actionZhuanhuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
        //评论
        binding.actionPinglun.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("newsId", ((MyNews)requireActivity().getIntent().getSerializableExtra("news")).get_id());
                bundle.putString("userId", userId);
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



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mOnFragmentListener = (NewsDetailFragment.OnFragmentListener) getActivity();
    }

    /**
     * fragment给activity回传值的接口
     **/
    public interface OnFragmentListener{

        void onFragmentGetChange(Boolean change);
    }


}