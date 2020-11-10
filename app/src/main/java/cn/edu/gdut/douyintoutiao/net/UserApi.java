package cn.edu.gdut.douyintoutiao.net;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("user")
    Call<User> getUser();

    /**
     * 检查登录数据是否有效接口
     * @author 彭俊源
     * @date 2020年11月8日 17:51:49
     * @return Result
     */
    @POST("checkValidate")
    Call<Result> validateUser(@Body User user);






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

