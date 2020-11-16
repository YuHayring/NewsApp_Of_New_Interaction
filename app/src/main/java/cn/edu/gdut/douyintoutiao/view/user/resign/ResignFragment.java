package cn.edu.gdut.douyintoutiao.view.user.resign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import cn.edu.gdut.douyintoutiao.databinding.FragmentResignBinding;
import cn.edu.gdut.douyintoutiao.entity.User;
import es.dmoral.toasty.Toasty;

import static cn.edu.gdut.douyintoutiao.R.drawable.red_dianzan;
import static cn.edu.gdut.douyintoutiao.R.drawable.yellow_guanzhu;

/**
 * @author hudp
 * @date 2020/11/7 21:43
 */

public class ResignFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "myTag";

    private ResignViewModel viewModel;
    private FragmentResignBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //

    public ResignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResignFragment newInstance(String param1, String param2) {
        ResignFragment fragment = new ResignFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentResignBinding.inflate(inflater);
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        viewModel = provider.get(ResignViewModel.class);
        viewModel.init();
        binding = FragmentResignBinding.inflate(inflater);
        return binding.getRoot();
    }

    public void toasty(String flag){
        switch (flag){
            case "no_enough":{
                Toasty.error(requireContext(), "请输入所需要的信息！", Toasty.LENGTH_SHORT, true).show();
                break;
            }
            case "phone_number":{
                Toasty.error(requireContext(), "非法手机号！", Toasty.LENGTH_SHORT, true).show();
                break;
            }
            case "sure_password":{
                Toasty.error(requireContext(), "确保两次输入密码相同！", Toasty.LENGTH_SHORT, true).show();
                break;
            }
            case "check_true":{
                Toasty.error(requireContext(), "该用户已经注册过了！", Toasty.LENGTH_SHORT, true).show();
                break;
            }
            case "insert_false":{
                Toasty.error(requireContext(), "注册失败！", Toasty.LENGTH_SHORT, true).show();
                break;
            }
            case "insert_true":{
                Toasty.success(requireContext(), "注册成功！", Toasty.LENGTH_SHORT, true).show();
                break;
            }
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                binding.actionDianzan.setIcon(red_dianzan);
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
                binding.actionGuanzhu.setIcon(yellow_guanzhu);
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
            }
        });


        //使用ViewMoel的注册逻辑
        binding.resignEnter.setOnClickListener(v -> {
            String userPhone = binding.phone.getText().toString();
            String password = binding.password.getText().toString();
            String surepassword = binding.surePassword.getText().toString();
            User user = new User();
            user.setUserTelephone(userPhone);
            user.setUserPassword(password);
            //检查杂项
            viewModel.check_something(user,surepassword);
            this.toasty(viewModel.getFlag().getValue());
            //注册
            viewModel.resign(user);
            this.toasty(viewModel.getFlag().getValue());
        });
        //注册按钮
//        binding.resignEnter.setOnClickListener(v -> {
//            String userPhone = binding.phone.getText().toString();
//            String password = binding.password.getText().toString();
//            String surepassword = binding.surePassword.getText().toString();
//            //检查各个是否有输入
//            if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(password )|| TextUtils.isEmpty(surepassword)) {
//                Toasty.error(requireContext(), "请输入所需要的信息！", Toasty.LENGTH_SHORT, true).show();
//                return;
//            }
//            //检查手机号是否是11位
//            if(userPhone.length()!=11){
//                Toasty.error(requireContext(), "非法手机号", Toasty.LENGTH_SHORT, true).show();
//                return;
//            }
//            //检查两次输入密码是否一样
//            if(!TextUtils.equals(password,surepassword)){
//                Toasty.error(requireContext(), "确保两次输入密码相同！", Toasty.LENGTH_SHORT, true).show();
//                return;
//            }
//
//            User user = new User();
//            user.setUserTelephone(userPhone);
//            user.setUserPassword(password);
//            Retrofit retrofit = RetrofitSingleton.getInstance();
//            UserApi api = retrofit.create(UserApi.class);
//
//            //检查手机号是否注册过
//            Call<Result> check = api.check_Resign(user);
//            check.enqueue(new Callback<Result>() {
//                @Override
//                public void onResponse(Call<Result> call, Response<Result> response) {
//                    assert response.body() != null;
//                    Log.d(TAG, "检查手机号onResponse: " + response.body().toString()+"msg:"+response.body().getMsg());
//                    if("false".equals(response.body().getMsg())){
//                        Toasty.error(requireContext(), "该手机已经注册过！", Toasty.LENGTH_SHORT, true).show();
//                        return;
//                    }else{
//                        //将注册手机号插入
//                        Call<Result> stringCall = api.insertUser(user);
//                        Log.d(TAG, "onClick: 注册手机号：" + userPhone + " " + password);
//                        stringCall.enqueue(new Callback<Result>() {
//                            @Override
//                            public void onResponse(Call<Result> call, Response<Result> response) {
//                                assert response.body() != null;
//                                Log.d(TAG, "onResponse: " + response.body().toString());
//                                if ("true".equals(response.body().getMsg())) {
//                                    Toasty.success(requireContext(), "注册成功!", Toast.LENGTH_SHORT, true).show();
//                                    Navigation.findNavController(v).navigate(R.id.action_resignFragment_to_loginFragment);
//
//                                } else {
//                                    Toasty.error(requireContext(), "注册失败！", Toast.LENGTH_SHORT, true).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Result> call, Throwable t) {
//
//                            }
//                        });
//                    }
//                }
//                @Override
//                public void onFailure(Call<Result> call, Throwable t) {
//
//                }
//            });
//
//
//        });
//
        binding.backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

    }

}
