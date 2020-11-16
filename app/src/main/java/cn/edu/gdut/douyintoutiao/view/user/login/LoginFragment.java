package cn.edu.gdut.douyintoutiao.view.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentLoginBinding;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.view.MainActivity;
import es.dmoral.toasty.Toasty;

/**
 * @author cypang
 * @date 2020年11月11日20:18:05
 */
public class LoginFragment extends Fragment implements Callback<Result<User>> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "myTag";
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //

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
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        viewModel = provider.get(LoginViewModel.class);
        viewModel.init(LoginFragment.this);
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //跳转到注册页面的点击事件
        binding.button2.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_loginFragment_to_resign);
        });
        //登陆按钮
        binding.button.setOnClickListener(v -> {
            viewModel.login();
        });
    }

    @Override
    public void returnResult(Result<User> result) {
        if (result.getLogin()) {
            Toasty.success(getContext(), result.getMsg(), Toasty.LENGTH_SHORT, true).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            Toasty.error(getContext(), result.getMsg(), Toasty.LENGTH_SHORT, true).show();
        }
    }
}