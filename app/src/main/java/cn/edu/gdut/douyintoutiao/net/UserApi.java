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
    Call<Result<User>> validateUser(@Body User user);
    /**
     * 插入注册数据
     * @author 胡庆鹏
     * @date
     * @return true or false
     */
    @POST("insert_user")
    Call<Result<User>> insertUser(@Body User user);
    /**
     * 检查该用户是否已经注册过
     * @author 胡庆鹏
     * @date
     * @return 查找到的用户
     */
    @POST("check_resign")
    Call<Result<User>> check_Resign(@Body User user);

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

