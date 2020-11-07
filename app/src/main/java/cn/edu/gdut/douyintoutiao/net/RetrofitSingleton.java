package cn.edu.gdut.douyintoutiao.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hayring
 * @date 2020/11/7 16:09
 */
public class RetrofitSingleton {
    private static class Singleton {
        public static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://af8b15.fn.thelarkcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return Singleton.retrofit;
    }
}
