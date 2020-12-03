package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentFollowAuthorDetailsBinding;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.ActivityFollowAuthorDetails;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorDetailsViewModel;

/**
 * @author : DengJL
 *@description : 被关注者详细信息的Fragment
 */
public class FragmentFollowAuthorDetails extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FollowAuthorDetailsViewModel followAuthorDetailsViewModel;
    private @NonNull FragmentFollowAuthorDetailsBinding fragmentFollowAuthorDetailsBinding;
    private String userId;
    private String followId;
    private Boolean isFollow;

    private OnFragmentListener mOnFragmentListener;

    public FragmentFollowAuthorDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_follow_author_detail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFollowAuthorDetails newInstance(String param1, String param2) {
        FragmentFollowAuthorDetails fragment = new FragmentFollowAuthorDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentFollowAuthorDetailsBinding =  FragmentFollowAuthorDetailsBinding.inflate(inflater);
        return fragmentFollowAuthorDetailsBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        followAuthorDetailsViewModel = new ViewModelProvider(this).get(FollowAuthorDetailsViewModel.class);

    followAuthorDetailsViewModel.queryUserByUserId(userId).observe(getViewLifecycleOwner(), new Observer< List< User > >() {
        @Override
        public void onChanged(List< User > list) {
            //获取当前作者页的userId-"被关注者",初始化ui
            User thisUser = list.get(0);
            initUi(thisUser);
            //获取当前操作的用户的userId-"主动关注者"
            SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
            String followerId = shp.getString("userId", "noContent");

            //关注按钮点击事件
            fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //根据关注关系判断字符isFollow决定点击事件
                    if(isFollow){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("取消关注?")
                            .setMessage("确定要取消关注"+list.get(0).getUserName()+"吗")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    followAuthorDetailsViewModel.deleteFollowListByFollowId(followId);
                                    Toast.makeText(getContext(), "已取消关注" + list.get(0).getUserName(), Toast.LENGTH_SHORT).show();
                                    fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setText("关注");
                                    //关注列表发生改变，传信息到act
                                    if(mOnFragmentListener != null){
                                        mOnFragmentListener.onFragmentGetChange(true);
                                    }
                                    //改变关注关系判断字符
                                    isFollow = false;
                                }
                            })
                            .create().show(); }
                    else{
                        followAuthorDetailsViewModel.insertUserFollowList(followerId,userId);
                            Toast.makeText(getContext(), "已关注" + list.get(0).getUserName(), Toast.LENGTH_SHORT).show();
                            fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setText("已关注");
                        //关注列表发生改变，传信息到act
                        if(mOnFragmentListener != null){
                            mOnFragmentListener.onFragmentGetChange(true);
                        }
                        //改变关注关系判断字符
                        isFollow = true;
                    }
                }
            });
        }
    });
}

    //初始化ui
    private  void initUi(User user){
        fragmentFollowAuthorDetailsBinding.textViewAuthorName.setText(user.getUserName());
        fragmentFollowAuthorDetailsBinding.textViewAuthorDetailsDescribe.setText("个性签名：" + user.getUserDescription());
        fragmentFollowAuthorDetailsBinding.textViewAuthorPhoneId.setText("id:"+user.getUserTelephone());
        fragmentFollowAuthorDetailsBinding.textViewAuthorNumber.setText("粉丝："+user.getFans()+"   |关注："+user.getTabs()+"   |获赞:"+user.getLikeNumber());
        String buttonText = "关注";
        if(isFollow){ buttonText = "已关注"; }
        fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setText(buttonText);

        Glide.with(FragmentFollowAuthorDetails.this)//当前类
                .load(user.getUserImageUrl())// 请求图片的路径,可以是网络图片
                .placeholder(R.drawable.photo_placeholder)//加载过程显示的图片
                .error(R.drawable.friends) // 出错加载的图片
                .into(fragmentFollowAuthorDetailsBinding.authorDetailsImage);// 显示到ImageView控件的对象

    }

    /**
     * fragment与activity产生关联的方法
     **/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userId = ((ActivityFollowAuthorDetails)context).getUserId();
        followId = ((ActivityFollowAuthorDetails)context).getFollowId();
        isFollow= ((ActivityFollowAuthorDetails)context).getFollow();

        //当前fragment从activity重写了回调接口  得到接口的实例化对象(很重要！)
        mOnFragmentListener = (OnFragmentListener) getActivity();
    }

    /**
     * fragment给activity回传值的接口
     **/
    public interface OnFragmentListener{
        /**
         * object需要实现Serializable或Parcelable接口
         **/
        void onFragmentGetChange(Boolean change);
    }


}