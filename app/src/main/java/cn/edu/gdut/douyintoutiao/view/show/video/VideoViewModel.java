package cn.edu.gdut.douyintoutiao.view.show.video;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
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





}
