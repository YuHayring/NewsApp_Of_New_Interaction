package cn.edu.gdut.douyintoutiao.view;

import android.content.Intent;
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

import cn.edu.gdut.douyintoutiao.databinding.FragmentLoginBinding;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "myTag";
    private FragmentLoginBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding = FragmentLoginBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button.setOnClickListener(v -> {
            String userName = binding.editTextTextPersonName.getText().toString();
            String password = binding.editTextTextPassword.getText().toString();
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                Toasty.error(requireContext(), "请输入所需要的信息！", Toasty.LENGTH_SHORT, true).show();
                return;
            }
            User user = new User();
            user.setUserName(userName);
            user.setUserPassword(password);
            Retrofit retrofit = RetrofitSingleton.getInstance();
            UserApi api = retrofit.create(UserApi.class);
            Call<Result> stringCall = api.validateUser(user);
            Log.d(TAG, "onClick: " + userName + " " + password);
            stringCall.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    if ("true".equals(response.body().getMsg())) {
                        Toasty.success(requireContext(), "登录成功!", Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toasty.error(requireContext(), "登录失败！", Toast.LENGTH_SHORT, true).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
        });

    }
}