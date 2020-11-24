package cn.edu.gdut.douyintoutiao.view.user.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.ImageApi;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 2020/11/6 20:48
 */
public class UserMainModel {

    private final UserApi userApi = UserApi.getUserApi();

    private final ImageApi imageApi = ImageApi.getImageApi();

//    Handler handler = new GetUserHandler();

    public static final String STATUS = "status";

    public static final String USER = "user";

    public static final int SUCCESS = 1;

    UserMainViewModel userMainViewModel;

    public UserMainModel(UserMainViewModel userMainViewModel) {
        this.userMainViewModel = userMainViewModel;
    }

    //获取用户信息
    public void getUser(String userId) {
        //执行网络请求，输入回调接口
        userApi.getUser(userId).enqueue(userInfoCallBack);
    }



    public void getImage(String url) {
        imageApi.getImg(url).enqueue(imageCallBack);
    }



    Callback<ResponseBody> imageCallBack = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200) {
                byte[] bytes = new byte[0];
                try {
                    bytes = response.body().bytes();
                } catch (IOException e) {
                    userMainViewModel.onImageGotCallBack.onFaile(e.getMessage());
                }
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                userMainViewModel.onImageGotCallBack.onSuccess(bmp);
            } else {
                userMainViewModel.onImageGotCallBack.onFaile(""+response.code());
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            //TODO
            throw new RuntimeException(t);
        }
    };



    /**
     * 网络请求-用户信息-回调
     */
    Callback<User> userInfoCallBack = new Callback<User>() {

        @Override
        public void onResponse(Call call, Response response) {
            if (response.code() == 200) {
                //回调 ViewModel 模块
                userMainViewModel.userGotCallBack.onSuccess((User) response.body());
            } else {
                userMainViewModel.userGotCallBack.onFaile(""+response.code());
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            //TODO
            throw new RuntimeException(t);
        }
    };


    //Handler 机制
//    private class GetUserHandler extends Handler {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//             int status = msg.getData().getInt(STATUS);
//             if (status == 1) {
//                 User user = (User)msg.getData().getSerializable(USER);
//                 callBack.onSuccess(user);
//             }
//        }
//
//
//    }



}
