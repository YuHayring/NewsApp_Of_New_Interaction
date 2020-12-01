package cn.edu.gdut.douyintoutiao.view.show.text.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.singleplayer.SingleVideoPlayActivity;

/**
 * @ProjectName: DouYinTouTiao
 * @Package: cn.edu.gdut.douyintoutiao.view.show.text.adapter
 * @ClassName: NewsAdapter
 * @Description: java类作用描述
 * @Author: cypang
 * @CreateDate: 2020/12/1/0001 12:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/1/0001 12:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NewsAdapter extends PagedListAdapter<MyNews, RecyclerView.ViewHolder> {

    private static final DiffUtil.ItemCallback<MyNews> DIFF_CALLBACK = new DiffUtil.ItemCallback<MyNews>() {
        @Override
        public boolean areItemsTheSame(@NonNull MyNews oldItem, @NonNull MyNews newItem) {
            return oldItem.get_id().equals(newItem.get_id());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MyNews oldItem, @NonNull MyNews newItem) {
            return oldItem.equals(newItem);
        }

    };
    private Context context;
    private boolean hasFooter = false;

    public NewsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        hideFooter();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == R.layout.item_news_list) {
            NewsViewHolder newsViewHolder = NewsViewHolder.newInstance(parent);
            newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("uri", getItem(newsViewHolder.getAbsoluteAdapterPosition()).getNewsDetailUrl());
                    intent.putExtra("newsId", getItem(newsViewHolder.getAbsoluteAdapterPosition()).get_id());
                    intent.putExtra("tag", getItem(newsViewHolder.getAbsoluteAdapterPosition()).getTag());
                    intent.putExtra("authorId", getItem(newsViewHolder.getAbsoluteAdapterPosition()).getAuthor().get(0).getUserId());
                    context.startActivity(intent);
                }
            });
            return newsViewHolder;
        } else if (viewType == R.layout.news_footer) {
            return FooterViewHolder.newInstance(parent);
        } else {
            VideoViewHolder viewHolder = VideoViewHolder.newInstance(parent);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleVideoPlayActivity.class);
                    intent.putExtra("news", getItem(viewHolder.getAbsoluteAdapterPosition()));
                    context.startActivity(intent);
                }
            });
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == R.layout.news_footer) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.bind();
        } else if (holder.getItemViewType() == R.layout.item_news_list) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.bindWithMyNews(getItem(position));
        } else {
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.bindWithMyNews(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasFooter ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasFooter && position == getItemCount() - 1) {
            showFooter();
            return R.layout.news_footer;
        } else if (getItem(position).getType().equals(0)) {
            hideFooter();
            return R.layout.item_news_list;
        } else {
            hideFooter();
            return R.layout.item_video_list;
        }
    }


    private void hideFooter() {
        if (hasFooter) {
            notifyItemRemoved(getItemCount() - 1);
        }
        hasFooter = false;
    }

    private void showFooter() {
        if (hasFooter) {
            notifyItemChanged(getItemCount() - 1);
        } else {
            hasFooter = true;
            notifyItemInserted(getItemCount() - 1);
        }

    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHeader, textViewAbstract;
        ImageView imageViewPic;

        private NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            textViewAbstract = itemView.findViewById(R.id.textViewAbstract);
            imageViewPic = itemView.findViewById(R.id.imageViewPic);
        }

        private static NewsViewHolder newInstance(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
            return new NewsViewHolder(itemView);
        }

        public void bindWithMyNews(MyNews myNews) {
            textViewHeader.setText(myNews.getNewsName());
            textViewAbstract.setText(myNews.getNewsAbstract());
            //采用glide加载网络图片,采用了占位符方式优先展示。
            Glide.with(itemView).load(Uri.parse(myNews.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(imageViewPic);
        }

    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textViewFooter);
        }

        private static FooterViewHolder newInstance(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer, parent, false);
            return new FooterViewHolder(itemView);
        }

        public void bind() {
            mTextView.setText("正在加载");
        }
    }


    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle;
        ImageView videoPreview;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.text_view_single_video_title);
            videoPreview = itemView.findViewById(R.id.image_view_single_video_preview);
            videoPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        private static VideoViewHolder newInstance(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list, parent, false);
            return new VideoViewHolder(itemView);
        }

        public void bindWithMyNews(MyNews myNews) {
            videoTitle.setText(myNews.getNewsName());
            //采用glide加载网络图片,采用了占位符方式优先展示。
            Glide.with(itemView).load(Uri.parse(myNews.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(videoPreview);
        }
    }
}