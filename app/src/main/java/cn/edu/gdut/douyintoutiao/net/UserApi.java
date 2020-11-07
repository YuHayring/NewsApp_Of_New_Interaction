package cn.edu.gdut.douyintoutiao.net;

import cn.edu.gdut.douyintoutiao.entity.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {
    @GET("user")
    Call<User> getUser();









    /**
     * 单例内部类
     */
    class Singleton {
        static UserApi userApi = RetrofitSingleton.getInstance().create(UserApi.class);
    }

    static UserApi getUserApi() {
        return Singleton.userApi;
    }
}

