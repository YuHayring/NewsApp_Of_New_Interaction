package cn.edu.gdut.douyintoutiao.view.show.mix;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.util.CommonDataGotCallBack;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 * @date 11/29/20 2:32 PM
 */
@Deprecated
public class MixNewsViewModel extends ViewModel {

    public MixNewsViewModel(){}

    public MixNewsViewModel(Activity activity) {
        this.activity = activity;
    }



    Activity activity;


    MixNewsModel model = MixNewsModel.getInstance();

    private static final int PAGE_COUNT = 16;

    MutableLiveData<List<MyNews>> newsList = new MutableLiveData<>();

    void getList() {
        model.getMoreNews(0, PAGE_COUNT, newsGotCallBack);
    }

    /**
     * 获取视频
     * @param index 起始位置
     */
    void getMoreVideoList(int index) {
        model.getMoreNews(index, PAGE_COUNT, newsGotCallBack);
    }



    CommonDataGotCallBack newsGotCallBack = new CommonDataGotCallBack<MyNews>() {
        @Override
        protected void onGotSuccess(List<MyNews> newses) {
            newsList.postValue(newses);
        }

        @Override
        protected void onNotExist() {
            Toasty.info(activity, R.string.mix_list_no_more).show();
        }

        @Override
        protected void onRequestError(int errCode) {
            Toasty.error(activity, activity.getString(R.string.mix_list_request_fail) + errCode).show();
        }
    };




    public MutableLiveData<List<MyNews>> getNewsList() {
        return newsList;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
