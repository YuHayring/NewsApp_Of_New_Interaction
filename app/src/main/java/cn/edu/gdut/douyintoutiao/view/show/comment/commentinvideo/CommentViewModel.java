package cn.edu.gdut.douyintoutiao.view.show.comment.commentinvideo;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityCommentFragmentContainerBinding;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.view.show.comment.CommentFragment;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/24/20 10:43 PM
 */
public class CommentViewModel {

    CommentFragmentContainerActivity activity;

    public CommentViewModel(CommentFragmentContainerActivity activity) {
        this.activity = activity;
    }

    private Callback<Result<Void>> postCommentCallBack = new Callback<Result<Void>>() {
        @Override
        public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
            if (response.code() == 200) {
                activity.getFragment().updateComment();
            } else {
                Toasty.error(activity, activity.getString(R.string.video_comment_request_fail) + response.code(), Toasty.LENGTH_SHORT, true).show();
            }
        }

        @Override
        public void onFailure(Call<Result<Void>> call, Throwable t) {
            Toasty.error(activity, t.getCause().toString(), Toasty.LENGTH_SHORT, true).show();
        }
    };

    public Callback<Result<Void>> getPostCommentCallBack() {
        return postCommentCallBack;
    }
}
