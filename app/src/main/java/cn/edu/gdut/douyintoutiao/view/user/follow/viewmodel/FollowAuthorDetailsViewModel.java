package cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.FollowApi;
import cn.edu.gdut.douyintoutiao.view.user.follow.model.FollowRepository;

/**
 * @author : DengJL
 *@description : 被关注者详细信息的Fragment的ViewModel
 */

public class FollowAuthorDetailsViewModel extends AndroidViewModel {

    private FollowRepository followAuthorDetailsRepository;
    private Boolean isChangeFollow;


    public FollowAuthorDetailsViewModel(@NonNull Application application) {
        super(application);
        followAuthorDetailsRepository =new FollowRepository(FollowApi.getFollowApi());
    }

    public LiveData< List< User > > queryUserByUserId (String userId){
        return followAuthorDetailsRepository.queryUserByUserId(userId);
    }

    public void deleteFollowListByFollowId(String followId){
        followAuthorDetailsRepository.deleteFollowListByFollowId(followId);
    }

    public void insertUserFollowList (String followerId,String authorId){
        followAuthorDetailsRepository.insertUserFollowList(followerId,authorId);
    }



}
