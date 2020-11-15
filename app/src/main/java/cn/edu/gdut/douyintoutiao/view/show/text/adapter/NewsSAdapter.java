package cn.edu.gdut.douyintoutiao.view.show.text.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsActivity;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/11 11:10
 */
public class NewsSAdapter extends RecyclerView.Adapter<NewsSAdapter.ViewHolder> {

    private final Activity activity;
    private List<MyNews> newsList = new ArrayList<>();

   /* private static final int NORMAL_VIEW_TYPE = 0;
    private static final int FOOTER_VIEW_TYPE = 1;*/

    public NewsSAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setNewsList(List<MyNews> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        /*if (viewType == NORMAL_VIEW_TYPE) {*/
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.item_news_list, parent, false);
            viewHolder = new ViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, NewsActivity.class);
                    intent.putExtra("uri", newsList.get(viewHolder.getAbsoluteAdapterPosition()).getNewsDetailUrl());
                    intent.putExtra("newsId", newsList.get(viewHolder.getAbsoluteAdapterPosition()).get_id());
                    activity.startActivity(intent);
                }
            });
        /*} else {
            viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_footer, parent, false));
        }*/
        return viewHolder;
    }

    //处理对holder上的一些操作
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* if (position == getItemCount() - 1) {
            return;
        }*/
        MyNews cur = newsList.get(position);
        holder.textViewHeader.setText(cur.getNewsName());
        holder.textViewAbstract.setText(cur.getNewsAbstract());
        //采用glide加载网络图片,采用了占位符方式优先展示。TODO 引入shimmerlayout做闪光效果
        Glide.with(holder.itemView).load(Uri.parse(cur.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(holder.imageViewPic);
    }

 /*   //TODO 增加无数据的提示
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTER_VIEW_TYPE;
        } else {
            return NORMAL_VIEW_TYPE;
        }
    }*/

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    //防止内存泄漏
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHeader, textViewAbstract;
        ImageView imageViewPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            textViewAbstract = itemView.findViewById(R.id.textViewAbstract);
            imageViewPic = itemView.findViewById(R.id.imageViewPic);
        }
    }
}
