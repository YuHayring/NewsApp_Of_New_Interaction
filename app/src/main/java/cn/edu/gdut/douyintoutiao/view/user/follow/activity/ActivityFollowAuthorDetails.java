package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.user.follow.FragmentFollowAuthorDetails;

 public class ActivityFollowAuthorDetails extends AppCompatActivity implements FragmentFollowAuthorDetails.OnFragmentListener  {

    private String userId;
    private String followId;
    private Boolean isFollow;
    private Boolean isChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_author_details);
        getUserInfo();

    }

    //拿到被访问的userId
    private  void getUserInfo(){
        Intent in = getIntent();
       userId = in.getStringExtra("userId");
    }

    //给fragment提供userId
    public String getUserId() {
        getUserInfo();
        return userId;
    }

    public String getFollowId() {
        Intent in = getIntent();
        followId = in .getStringExtra("followId");
        return followId;
    }

    public Boolean getFollow() {
        Intent in = getIntent();
        isFollow = in.getExtras().getBoolean("isFollow");
        return isFollow;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent();
//        this.setResult(1,intent);
//        finish();
    }


    @Override
    public void finish() {
        super.finish();

    }


     /**
      * 拦截press事件，这里只对back键进行判断
      *
      **/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isChange == null){
            isChange = false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //System.out.println("isChange:"+isChange);
            //如果关注列表发生了改变，则resultCode赋值为1
            if(isChange) {
                Intent i = new Intent();
                setResult(1, i);
            }
            onBackPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    /**
     * object需要实现Serializable或Parcelable接口
     * 获取change判断关注关系是否发生了变化
     * @param change
     **/
    @Override
    public void onFragmentGetChange(Boolean change) {
       // System.out.println("isChange:"+change);
        isChange = change;
    }
}