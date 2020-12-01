package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.content.Context;
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
  //  private OnFragmentInteractionListener mListener;

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
//        Bundle bundle =this.getArguments();//得到从Activity传来的数据
//        if(bundle!=null){
//            userId = bundle.getString("UserId");
//        }
        return fragmentFollowAuthorDetailsBinding.getRoot();

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_follow_author_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        followAuthorDetailsViewModel = new ViewModelProvider(this).get(FollowAuthorDetailsViewModel.class);

        followAuthorDetailsViewModel.queryUserByUserId(userId).observe(getViewLifecycleOwner(), new Observer< List< User > >() {
            @Override
            public void onChanged(List< User > list) {

                fragmentFollowAuthorDetailsBinding.textViewAuthorName.setText(list.get(0).getUserName());
                fragmentFollowAuthorDetailsBinding.textViewAuthorDetailsDescribe.setText("个性签名："+list.get(0).getUserDescription());


                if (list.get(0).getUserImageUrl() != null)
                Glide.with(FragmentFollowAuthorDetails.this)//当前类
                        .load(list.get(0).getUserImageUrl())// 请求图片的路径,可以是网络图片
                        .placeholder(R.drawable.photo_placeholder)//加载过程显示的图片
                        .error(R.drawable.friends) // 出错加载的图片
                        .into(fragmentFollowAuthorDetailsBinding.authorDetailsImage);// 显示到ImageView控件的对象

                fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setText("已关注");
                fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        followAuthorDetailsViewModel.deleteFollowListByFollowId(followId);
                        Toast.makeText(getContext(),"已取消关注"+list.get(0).getUserName(), Toast.LENGTH_SHORT).show();
                        fragmentFollowAuthorDetailsBinding.buttonUnfollowAuhorDetails.setText("关注");
                    }
                });
            }

        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userId = ((ActivityFollowAuthorDetails)context).getUserId();
        followId = ((ActivityFollowAuthorDetails)context).getFollowId();
     //   System.out.println("onAttach:"+((ActivityFollowAuthorDetails)context).getUserId());
    }



}