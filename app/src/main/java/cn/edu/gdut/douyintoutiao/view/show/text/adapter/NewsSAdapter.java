package cn.edu.gdut.douyintoutiao.view.show.text.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import cn.edu.gdut.douyintoutiao.view.show.video.singleplayer.SingleVideoPlayActivity;

/**
 * @author : cypang
 * @description ：
 * @email : 516585610@qq.com
 * @date : 2020/11/11 11:10
 */
public class NewsSAdapter extends RecyclerView.Adapter {

    private List<MyNews> newsList = new ArrayList<>();

    // 文字 ViewType
    private static final int TYPE_TEXT = 0;
    // 视频 ViewType
    private static final int TYPE_VIDEO = 1;
    // 空布局的ViewType
    private static final int TYPE_EMPTY = 2;


    private Context context;
    // 是否显示空布局，默认不显示
    private boolean showEmptyView = false;

    public NewsSAdapter(Context context) {
        this.context = context;
    }

    public void setNewsList(List<MyNews> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_EMPTY){
            return new ViewHolder(getEmptyView(parent));
        }
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView ;
        if(viewType == TYPE_TEXT) {
            itemView = layoutInflater.inflate(R.layout.item_news_list, parent, false);
            viewHolder = new ViewHolder(itemView);
        }else {
            itemView = layoutInflater.inflate(R.layout.item_video_list, parent, false);
            viewHolder = new VideoViewHolder(itemView);
        }
        itemView.setOnClickListener(v -> {
            if(newsList.get(viewHolder.getAbsoluteAdapterPosition()).getType().equals(1)){
                Intent intent = new Intent(context, SingleVideoPlayActivity.class);
                intent.putExtra("news", newsList.get(viewHolder.getAbsoluteAdapterPosition()));
                context.startActivity(intent);
            }else {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("uri", newsList.get(viewHolder.getAbsoluteAdapterPosition()).getNewsDetailUrl());
                intent.putExtra("newsId", newsList.get(viewHolder.getAbsoluteAdapterPosition()).get_id());
                intent.putExtra("tag", newsList.get(viewHolder.getAbsoluteAdapterPosition()).getTag());
                intent.putExtra("authorId", newsList.get(viewHolder.getAbsoluteAdapterPosition()).getAuthor().get(0).getUserId());
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    //处理对holder上的一些操作
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isEmptyPosition(position)){
            return ;
        }
        MyNews cur = newsList.get(position);
        if(holder instanceof NewsSAdapter.ViewHolder) {
            NewsSAdapter.ViewHolder mHolder = (NewsSAdapter.ViewHolder)holder;
            mHolder.textViewHeader.setText(cur.getNewsName());
            mHolder.textViewAbstract.setText(cur.getNewsAbstract());
            //采用glide加载网络图片,采用了占位符方式优先展示。
            Glide.with(holder.itemView).load(Uri.parse(cur.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(mHolder.imageViewPic);
        }else {
            NewsSAdapter.VideoViewHolder mHolder = (NewsSAdapter.VideoViewHolder)holder;
            mHolder.videoTitle.setText(""+position+cur.getNewsName());
            //采用glide加载网络图片,采用了占位符方式优先展示。
            Glide.with(mHolder.videoPreview).load(Uri.parse(cur.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(mHolder.videoPreview);
        }
    }


    @Override
    public int getItemCount() {
        int count = newsList != null ? newsList.size() : 0;
        if (count > 0) {
            return count;
        } else if (showEmptyView) {
            // 显示空布局
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmptyPosition(position)) {
            // 空布局
            return TYPE_EMPTY;
        } else {
            return newsList.get(position).getType().equals(0)? TYPE_TEXT : TYPE_VIDEO;
        }
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

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView videoTitle;
        ImageView videoPreview;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.text_view_single_video_title);
            videoPreview = itemView.findViewById(R.id.image_view_single_video_preview);
            videoPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
    /**
     * 获取空布局
     */
    private View getEmptyView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_blank, parent, false);
    }

    /**
     * 判断是否是空布局
     */
    public boolean isEmptyPosition(int position) {
        int count = newsList != null ? newsList.size() : 0;
        return position == 0 && showEmptyView && count == 0;
    }

    /**
     * 设置空布局显示。默认不显示
     */
    public void showEmptyView(boolean isShow) {
        if (isShow != showEmptyView) {
            showEmptyView = isShow;
            notifyDataSetChanged();
        }
    }
}

