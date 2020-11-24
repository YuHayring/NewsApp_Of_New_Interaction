package cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/24/20 10:44 PM
 */
public class CommentModel {


    private CommentApi api;

    private CommentViewModel commentViewModel;

    public CommentModel(CommentViewModel commentViewModel) {
        this.commentViewModel = commentViewModel;
        this.api = CommentApi.getCommentApi();
    }

    private static final String TAG = "comment";




    public void postComment(String newsID, String userID, String content) {
        Log.d(TAG, "postComment: " + newsID + userID + content);
        Map<String, String> map = new HashMap<>();
        map.put("newsId", newsID);
        map.put("userId", userID);
        map.put("content", content);
        Call<Result<Void>> resultCall = api.postComment(map);
        resultCall.enqueue(commentViewModel.getPostCommentCallBack());
    }
}
