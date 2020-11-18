package cn.edu.gdut.douyintoutiao.view.user.follow.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowAuthorListFragment;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;

/**
 * @author : DengJL
 * @description ： Fragment_follow_author_list 的 RV 适配器
 */

public class FollowAuthorListAdapter extends RecyclerView.Adapter<FollowAuthorListAdapter.ViewHolder>{
    private final Activity activity;
    private List< Follow > follows = new ArrayList<Follow>();
    private FollowAuthorViewModel viewModel;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public  FollowAuthorListAdapter(Activity activity, List< Follow > list) {
        this.activity = activity;
        follows = list;

    }
    public FollowAuthorListAdapter(Activity activity){
        this.activity = activity;
    }

    public void setFollows(List< Follow > follows) {
        this.follows = follows;
    }

    public List< Follow > getFollows() {
        return follows;
    }

    //声明自定义的监听接口
    private FollowAuthorListFragment.OnItemClickListener followAuthorItemClickListener;
    /**
     * 描述：提供set方法供Activity或Fragment调用
     * @param listener 监听器
     */
    public void setItemClickListener(FollowAuthorListFragment.OnItemClickListener listener){
        followAuthorItemClickListener=listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView followImage;
        TextView followName;
        ImageButton unfollowButton;
        TextView  followDescribe;

        public ViewHolder(View view) {
            super(view);
            //imgage尚未解决
            followImage = (ImageView) view.findViewById(R.id.author_image);
            followName = (TextView) view.findViewById(R.id.textView_item_author_name);
            unfollowButton = (ImageButton) view.findViewById(R.id.button_item_unfollow);
            followDescribe = (TextView) view.findViewById(R.id.textView_item_author_describe);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow_author_list, parent, false);
        ViewHolder holder = new ViewHolder(view);

        /**
         * 描述：将监听传递给自定义接口
         */
        holder.unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followAuthorItemClickListener!=null){
                    followAuthorItemClickListener.onUnFollowButtonClick(holder.getAdapterPosition());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followAuthorItemClickListener!=null){
                    followAuthorItemClickListener.onItemViewClick(holder.getAdapterPosition());
                }
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow s = follows.get(position);
        holder.followName.setText(s.getAuthor().get(0).getUserName());
        holder.followDescribe.setText(s.getAuthor().get(0).getUserDescription());
    }

    //getItemCount() 方法就非常简单了， 它用于告诉RecyclerView 一共有多少子项， 直接返回数据源的长度就可以了
    @Override
    public int getItemCount() {

        return follows.size();
    }



}
