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
public class VideoPlayerViewModel extends ViewModel {



    public VideoPlayerViewModel () {}

    public VideoPlayerViewModel(Activity activity) {
        this.activity = activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected Activity activity;

    abstract class CommonVideoGotCallBack implements Callback<List<MyNews>> {

        abstract void onVideoGotSuccess(List<MyNews> newses);

        abstract void onVideoNotExist();


        @Override
        public void onResponse(Call<List<MyNews>> call, Response<List<MyNews>> response) {
            if (response.code() == 200) {
                onVideoGotSuccess(response.body());
            } else if (response.code() == 404) {
                onVideoNotExist();
            } else {
                Toasty.error(activity, R.string.video_play_request_fail).show();
            }
        }

        @Override
        public void onFailure(Call<List<MyNews>> call, Throwable t) {
            throw new RuntimeException(t);
        }
    }




}
