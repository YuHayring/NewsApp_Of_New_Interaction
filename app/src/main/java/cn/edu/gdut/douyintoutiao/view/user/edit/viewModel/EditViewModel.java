package cn.edu.gdut.douyintoutiao.view.user.edit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import cn.edu.gdut.douyintoutiao.net.UserApi;
import cn.edu.gdut.douyintoutiao.view.user.edit.model.EditRepository;

public class EditViewModel extends AndroidViewModel {

    private EditRepository editRepository;

    public EditViewModel(@NonNull Application application) {
        super(application);
        editRepository = new EditRepository(UserApi.getUserApi());
    }

    public void updateUserInfo(String userId , String userName ,String userDescribe){
        editRepository.updateUser(userId,userName,userDescribe);
    }
}
