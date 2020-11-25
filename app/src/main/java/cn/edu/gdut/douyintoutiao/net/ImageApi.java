package cn.edu.gdut.douyintoutiao.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ImageApi {


    @GET
    Call<ResponseBody> getImg(@Url String url);


    /**
     * 单例内部类
     */
    class Singleton {
        static ImageApi imageApi = RetrofitSingleton.getInstance().create(ImageApi.class);
    }

    static ImageApi getImageApi() {
        return ImageApi.Singleton.imageApi;
    }
}
