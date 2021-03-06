package cn.edu.gdut.douyintoutiao.view.user.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentUserMainBinding;
import cn.edu.gdut.douyintoutiao.entity.User;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 * @date 2020/11/6 20:51
 */
public class UserMainViewModel {

    public UserMainModel userMainModel;

    private Context context;

    private FragmentUserMainBinding binding;


    OnGotCallBack<User> userGotCallBack = new OnGotCallBack<User>() {

        /**
         * 获取到用户信息，将其显示在界面上
         * @param user
         */
        @Override
        public void onSuccess(User user) {
            binding.userNameTag.setText(user.getUserName());
            binding.userDescriptionTag.setText(user.getUserDescription());
            binding.userPhoneIdText.setText("id:"+user.getUserTelephone());
            binding.textViewUserNum.setText("粉丝："+user.getFans()+"   |关注："+user.getTabs()+"   |获赞:"+user.getLikeNumber());
        
            if (user.getUserImageUrl() != null)
            Glide.with(binding.userAvatars)
                    .load(Uri.parse(user.getUserImageUrl()))
                    .placeholder(R.drawable.photo_placeholder)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))//设置圆形
                    .into(binding.userAvatars);
            //userMainModel.getImage(user.getUserImageUrl());
        }

        @Override
        public void onFaile(String errorInfo) {
            gotFail("用户信息获取错误 ErrorCode: " + errorInfo);
            //TODO
        }
    };


    OnGotCallBack<Bitmap> onImageGotCallBack = new OnGotCallBack<Bitmap>() {
        @Override
        public void onSuccess(Bitmap bitmap) {
            binding.userAvatars.setImageBitmap(bitmap);
        }

        @Override
        public void onFaile(String errorInfo) {
            gotFail("头像获取错误, errorCode: " + errorInfo);
        }
    };

    public UserMainViewModel(FragmentUserMainBinding binding, Context context) {
        this.userMainModel = new UserMainModel(this);
        this.context = context;
        this.binding = binding;
    }


    public interface OnGotCallBack<T> {
        void onSuccess(T t);

        void onFaile(String errorInfo);
    }



    private void gotFail(String err) {
        Toasty.error(context, err);
    }

}
