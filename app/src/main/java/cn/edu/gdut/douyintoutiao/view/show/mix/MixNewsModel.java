package cn.edu.gdut.douyintoutiao.view.show.mix;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.util.CommonDataGotCallBack;

/**
 * @author hayring
 * @date 11/29/20 2:31 PM
 */
@Deprecated
public class MixNewsModel {

    /**
     * 模拟数据
     * @return
     */
    public void getNews(CommonDataGotCallBack<MyNews> commonGotCallBack) {
        NewsApi.getNewsApi().getMixList(0,16).enqueue(commonGotCallBack);
    }



    /**
     * 模拟数据
     * @return
     */
    public void getMoreNews(int index, int pageCount, CommonDataGotCallBack<MyNews> commonGotCallBack) {
        NewsApi.getNewsApi().getMixList(index, pageCount).enqueue(commonGotCallBack);
    }

    static class Singleton {
        static MixNewsModel instance = new MixNewsModel();
    }

    private MixNewsModel(){}

    public static MixNewsModel getInstance() {
        return Singleton.instance;
    }

}
