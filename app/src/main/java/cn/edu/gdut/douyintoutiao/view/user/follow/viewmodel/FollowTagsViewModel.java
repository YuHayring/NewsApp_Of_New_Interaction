package cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.net.FollowApi;
import cn.edu.gdut.douyintoutiao.view.user.follow.model.FollowRepository;

/**
 * @author : DengJL
 *@description : 关注事件fragment的ViewModel
 */
public class FollowTagsViewModel  extends AndroidViewModel {

    private FollowRepository followRepository;

    public FollowTagsViewModel(@NonNull Application application) {
        super(application);
        followRepository = new FollowRepository(FollowApi.getFollowApi());
    }


    public LiveData<List<FollowNews>> getFollowTagsList (String userId){

       //  String userId = "5fa9f7f63d18b202258b5daf";
       return followRepository.getFollowTagsList(userId);
    }

    public void deleteFollowTagsByFollowNewsId (String followNewsId){
        followRepository.deleteFollowTagsByFollowNewsId(followNewsId);
    }

}
