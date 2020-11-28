package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/27/20 4:04 PM
 */
public class VideoViewModel extends ViewModel {



    public VideoViewModel() {}

    public VideoViewModel(Activity activity) {
        this.activity = activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    protected Activity activity;

    public static abstract class CommonVideoGotCallBack implements Callback<List<MyNews>> {

        protected abstract void onVideoGotSuccess(List<MyNews> newses);

        protected abstract void onVideoNotExist();

        protected void onRequestError(int errCode) {}


        @Override
        public void onResponse(Call<List<MyNews>> call, Response<List<MyNews>> response) {
            if (response.code() == 200) {
                onVideoGotSuccess(response.body());
            } else if (response.code() == 404) {
                onVideoNotExist();
            } else {
                onRequestError(response.code());
            }
        }

        @Override
        public void onFailure(Call<List<MyNews>> call, Throwable t) {
            throw new RuntimeException(t);
        }
    }




}
