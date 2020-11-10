package cn.edu.gdut.douyintoutiao.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentResignBinding;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.RetrofitSingleton;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author hudp
 * @date 2020/11/7 21:43
 */
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResignFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "myTag";
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
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //注册按钮
        binding.resignEnter.setOnClickListener(v -> {
            String userPhone = binding.phone.getText().toString();
            String password = binding.password.getText().toString();
            String surepassword = binding.surePassword.getText().toString();
            //检查各个是否有输入
            if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(password )|| TextUtils.isEmpty(surepassword)) {
                Toasty.error(requireContext(), "请输入所需要的信息！", Toasty.LENGTH_SHORT, true).show();
                return;
            }
            //检查手机号是否是11位
            if(userPhone.length()!=11){
                Toasty.error(requireContext(), "非法手机号", Toasty.LENGTH_SHORT, true).show();
                return;
            }
            //检查两次输入密码是否一样
            if(!TextUtils.equals(password,surepassword)){
                Toasty.error(requireContext(), "确保两次输入密码相同！", Toasty.LENGTH_SHORT, true).show();
                return;
            }

            User user = new User();
            user.setUserTelephone(userPhone);
            user.setUserPassword(password);
            Retrofit retrofit = RetrofitSingleton.getInstance();
            UserApi api = retrofit.create(UserApi.class);


            Call<Result> check = api.check_Resign(user);
            check.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    assert response.body() != null;
                    Log.d(TAG, "检查手机号onResponse: " + response.body().toString()+"msg:"+response.body().getMsg());
                    if("false".equals(response.body().getMsg())){
                        Toasty.error(requireContext(), "该手机已经注册过！", Toasty.LENGTH_SHORT, true).show();
                        return;
                    }else{
                        //
                        Call<Result> stringCall = api.insertUser(user);
                        Log.d(TAG, "onClick: 注册手机号：" + userPhone + " " + password);
                        stringCall.enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                assert response.body() != null;
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                if ("true".equals(response.body().getMsg())) {
                                    Toasty.success(requireContext(), "注册成功!", Toast.LENGTH_SHORT, true).show();
                                    Navigation.findNavController(v).navigate(R.id.action_resignFragment_to_loginFragment);

                                } else {
                                    Toasty.error(requireContext(), "注册失败！", Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {

                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });


        });

        binding.backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

//
//        btnBack.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigateUp();
//            }
//        });

    }

}
