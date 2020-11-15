package cn.edu.gdut.douyintoutiao.view.user.follow.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;

public class FollowAuthorListAdapter extends RecyclerView.Adapter<FollowAuthorListAdapter.ViewHolder>{
    private final Activity activity;
    private List< Follow > follows = new ArrayList<Follow>();
    FollowAuthorViewModel authorViewModel;

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView followImage;
        TextView followName;

        public ViewHolder(View view) {
            super(view);
            followImage = (ImageView) view.findViewById(R.id.test_friends_image);
            followName = (TextView) view.findViewById(R.id.author_name);


        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow_author_list, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {//对加载的子项注册监听事件
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Follow follow = follows.get(position);
                //Toast.makeText(view.getContext(), " 你点击了" + follow.getAuthor().getUserName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {//对子项里的Image注册监听事件
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Follow follow = follows.get(position);
               // Toast.makeText(view.getContext(), " 你点击了" + follow.getAuthor().getUserName(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow s = follows.get(position);
        holder.followName.setText("用户名：" +s.getAuthor().get(0).getUserName());
    }

    //getItemCount() 方法就非常简单了， 它用于告诉RecyclerView 一共有多少子项， 直接返回数据源的长度就可以了
    @Override
    public int getItemCount() {

        return follows.size();
    }




}
