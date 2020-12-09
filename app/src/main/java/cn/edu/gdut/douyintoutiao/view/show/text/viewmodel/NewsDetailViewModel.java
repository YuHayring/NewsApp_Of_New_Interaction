package cn.edu.gdut.douyintoutiao.view.show.text.viewmodel;

import androidx.lifecycle.ViewModel;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.view.show.text.model.NewsRepository;

/**
 * @author : cypang
 * @description ：
 * @email : 516585610@qq.com
 * @date : 11/17/20 11:09
 */
public class NewsDetailViewModel extends ViewModel {
    private static final String TAG = "myTag";
    private final NewsRepository newsRepository;


    public NewsDetailViewModel() {
        newsRepository = new NewsRepository(NewsApi.getNewsApi());
    }


    public void newsLike(MyNews news){
        newsRepository.newsLike(news);
    }
    public void newsUnLike(MyNews news){
        newsRepository.newsUnLike(news);
    }


    /**
     * @DengJl
     * 关注事件功能
     */

    public void insertTagsFollowByNewsIdUserId(String newsId, String userId){
        newsRepository.insertTagsFollowByNewsIdUserId(newsId,userId);
    }

    public void deleteTagsFollowByNewsIdUserId(String newsId,String userId){
        newsRepository.deleteTagsFollowByNewsIdUserId(newsId,userId);
    }



}
