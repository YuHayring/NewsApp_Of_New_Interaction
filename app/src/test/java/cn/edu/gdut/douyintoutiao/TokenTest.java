package cn.edu.gdut.douyintoutiao;

import org.junit.Test;

import java.io.IOException;

import cn.edu.gdut.douyintoutiao.entity.Token;
import cn.edu.gdut.douyintoutiao.net.RetrofitSingleton;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/16/20 1:50 PM
 */
public class TokenTest {



    @Test
    public void tokenTest() {
        UserApi api = UserApi.getUserApi();
        Token token = new Token("token", null);
        try {
            token = UserApi.getUserApi().getToken(token).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (token != null) {
            System.out.println(token.getToken());
            System.out.println(token.getReFlashToken());
        }

    }

}
