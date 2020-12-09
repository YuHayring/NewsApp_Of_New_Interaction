package cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.net.FollowApi;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowCallBack;
import cn.edu.gdut.douyintoutiao.view.user.follow.model.FollowRepository;
//
public class FollowAuthorViewModel extends AndroidViewModel {

    private FollowRepository followRepository;


    public FollowAuthorViewModel(@NonNull Application application) {
        super(application);
        followRepository = new FollowRepository(FollowApi.getFollowApi());
    }

    public void setCallBack(FollowCallBack followCallBack){
        followRepository.setFollowCallBack(followCallBack);
    }

    public LiveData<List<Follow>> getFollowList(String userId) {return followRepository.getFollowList(userId);}


    public void deleteFollowListByFollowId(String followId){
       followRepository.deleteFollowListByFollowId(followId);
    }
}
