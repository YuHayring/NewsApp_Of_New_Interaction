package cn.edu.gdut.douyintoutiao.view.user.edit.model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRepository {

    private final UserApi userApi;
    private static final String updateTag = "updateUserInfo";

    public EditRepository(UserApi userApi) {
        this.userApi = userApi;

    }

    public void updateUser(String userId , String userDescribe) {
        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("userId",userId );
        userInfoMap.put("userDescription",userDescribe);
        Call< Result > call = userApi.updateUser(userInfoMap);
       call.enqueue(new Callback< Result >() {
           @Override
           public void onResponse(Call< Result > call, Response< Result > response) {
               Log.d(updateTag, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());

           }

           @Override
           public void onFailure(Call< Result > call, Throwable t) {
               Log.d(updateTag, "onFailure: updateUser请求失败 ");

           }
       });

    }
}
