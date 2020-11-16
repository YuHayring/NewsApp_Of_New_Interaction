package cn.edu.gdut.douyintoutiao.net;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.DouYinTouTiaoApplication;
import cn.edu.gdut.douyintoutiao.entity.Token;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hayring
 * @date 2020/11/7 16:09
 */
public class RetrofitSingleton {


    public static final String BASE_URL = "https://af8b15.fn.thelarkcloud.com/";

    public static final String TOKEN = "token";

    public static final String REFLASH_TOKEN = "reFlashToken";

    /**
     * 单例内部类
     */
    private static class Singleton {
        public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  //rxjava转换器
                .build();

//        /***
//         * tokenRetrofit 字段
//         */
//        public static Retrofit tokenRetrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(getClient().build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  //rxjava转换器
//                .build();


        //在请求头里添加token的拦截器处理
        private static Interceptor tokenHeaderInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SharedPreferences sp = DouYinTouTiaoApplication.getInstance().getSharedPreferences(DouYinTouTiaoApplication.TOKEN, Context.MODE_PRIVATE);
                String token = sp.getString(TOKEN,"");
                String reFlashToken = sp.getString(REFLASH_TOKEN, "");
                Request originalRequest = chain.request();
                if (token.isEmpty()) {
                    //没有 token 则不添加 header
                    return chain.proceed(originalRequest);
                } else {
                    //添加 Token
                    Request request = originalRequest.newBuilder().header("token", token).build();
                    Response response = chain.proceed(request);
                    //如果token过期 再去重新请求token 然后设置token的请求头 重新发起请求 用户无感
                    if (response.code() == 401){
                        Token newToken = getNewToken();
                        //更新失败，因为 Reflashtoken 也失效了,直接将失败结果返回给 Model
                        if (newToken == null || newToken.getToken().isEmpty() || newToken.getReFlashToken().isEmpty()) return response;
                        sp.edit().putString(TOKEN, newToken.getToken()).commit();
                        sp.edit().putString(REFLASH_TOKEN, newToken.getReFlashToken()).commit();
                        //使用新的Token，创建新的请求
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", newToken.getToken())
                                .build();
                        return chain.proceed(newRequest);
                    }
                    return response;
                }
            }
        };




        //OkHttp 客户端
        private static OkHttpClient.Builder getClient() {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            //注册拦截器
            httpClientBuilder.addNetworkInterceptor(tokenHeaderInterceptor);
            return httpClientBuilder;
        }

    }

    /**
     * 普通 Retrofit 类，未登录的 API 使用
     * @return
     */
    public static Retrofit getInstance() {
        return Singleton.retrofit;
    }


//    /**
//     * 登录后 API 使用 的 Retrofit
//     * @param token
//     * @return
//     */
//    public static Retrofit getRetroofitByToken(String token) {
//        return Singleton.tokenRetrofit;
//    }


    /**
     * 同步请求方式，获取最新的Token
     * @author hayring
     * @return
     */
    public static Token getNewToken() throws IOException {
        //获取 sp
        SharedPreferences sp = DouYinTouTiaoApplication.getInstance().getSharedPreferences(DouYinTouTiaoApplication.TOKEN, Context.MODE_PRIVATE);
        //ReflashToken
        String reFlashToken = sp.getString(REFLASH_TOKEN,"");
        Token token = new Token(null, reFlashToken);
        //网络请求
        return UserApi.getUserApi().getToken(token).execute().body();
    }


}