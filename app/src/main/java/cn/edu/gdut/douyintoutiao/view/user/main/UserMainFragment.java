package cn.edu.gdut.douyintoutiao.view.user.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;

import java.io.File;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentUserMainBinding;
import cn.edu.gdut.douyintoutiao.view.MainActivity;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.FollowListActivity;
//import cn.edu.gdut.douyintoutiao.view.user.follow.FollowListActivity;

/**
 * @author hayring
 * @date 2020/11/7 21:25
 */
public class UserMainFragment extends Fragment {


    private final Context context;

    public UserMainFragment(Context context) {
        this.context = context;
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            activity.setFileCallbackFromUserMain(fileCallback);
        }
    }


    private FragmentUserMainBinding mUserInfoBinding;

    UserMainViewModel userMainViewModel = new UserMainViewModel();

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_user_main, container, false);

        mUserInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_main, container, false);
        mUserInfoBinding.setUserMainViewModel(userMainViewModel);

        return mUserInfoBinding.getRoot();

    }

    /**
     * 加载完成
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userMainViewModel.userMainModel.getUser("");

    }

    //跳转
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserInfoBinding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowListActivity.class);
                startActivity(intent);
            }
        });

        mUserInfoBinding.userAvatars.setOnClickListener(view1 -> {

            new FileChooserDialog.Builder(context)
                    .initialPath("/sdcard/Download")  // changes initial path, defaults to external storage directory
                    .mimeType("image/*") // Optional MIME type filter
                    .extensionsFilter(".png", ".jpg") // Optional extension filter, will override mimeType()
                    .tag("optional-identifier")
                    .goUpLabel("Up") // custom go up label, default label is "..."
                    .show((FragmentActivity)context); // an AppCompatActivity which implements FileCallback
        });

    }

//    static class MyFolderChooserDialog extends FolderChooserDialog {
//        @Override
//        public void onAttach(Context context) {
//            try {
//                super.onAttach(context);
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


    private FileChooserDialog.FileCallback fileCallback = new FileChooserDialog.FileCallback() {
        @Override
        public void onFileSelection(@NonNull FileChooserDialog dialog, @NonNull File file) {
            Log.i("UserMainFragment", file.toString());
        }

        @Override
        public void onFileChooserDismissed(@NonNull FileChooserDialog dialog) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            activity.setFileCallbackFromUserMain(null);
        }
    }
}
