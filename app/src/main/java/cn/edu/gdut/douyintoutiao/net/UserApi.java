package cn.edu.gdut.douyintoutiao.net;

import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.Token;
import cn.edu.gdut.douyintoutiao.entity.User;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    @GET("user")
    Call<User> getUser(@Query("userId") String userId);

    /**
     * 检查登录数据是否有效接口
     * @author 彭俊源
     * @date 2020年11月8日 17:51:49
     * @return Result
     */
    @POST("checkValidate")
    Observable<Result<User>> validateUser(@Body User user);
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


    @POST("tokenreflashtest")
    Call<Token> getToken(@Body Token token);

    /**
     * @author dengJL
     * 编辑用户信息
     */
    @POST("updateUserInfo")
    Call<Result> updateUser(@Body Map<String,String> userInfoMap);

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

