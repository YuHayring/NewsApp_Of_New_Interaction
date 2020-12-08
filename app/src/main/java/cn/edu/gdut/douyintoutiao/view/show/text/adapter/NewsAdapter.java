package cn.edu.gdut.douyintoutiao.view.show.text.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsActivity;
import cn.edu.gdut.douyintoutiao.view.show.text.dataSource.NewsDataSource;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;
import cn.edu.gdut.douyintoutiao.view.show.video.VerticalVideoPlayActivity;

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
    // 是否显示空布局，默认不显示
    private boolean showEmptyView = false;
    private NewsDataSource.NetWorkStatus netWorkStatus = null;

    private NewsViewModel mViewModel;


    public NewsAdapter(Context context, NewsViewModel mViewModel) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.mViewModel = mViewModel;
    }

    public void setNetWorkStatus(NewsDataSource.NetWorkStatus netWorkStatus) {
        this.netWorkStatus = netWorkStatus;
        if(netWorkStatus == NewsDataSource.NetWorkStatus.INITIAL){
            hideFooter();
        }else {
            showFooter();
        }
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
                    intent.putExtra("news", getItem(newsViewHolder.getAbsoluteAdapterPosition()));
                    intent.putExtra("isFollow",false);
                    context.startActivity(intent);
                }
            });
            return newsViewHolder;
        } else if (viewType == R.layout.news_footer) {
            FooterViewHolder viewHolder = FooterViewHolder.newInstance(parent);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewModel.retry();
                }
            });
            return viewHolder;
        } else if(viewType == R.layout.item_video_list){
            VideoViewHolder viewHolder = VideoViewHolder.newInstance(parent);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VerticalVideoPlayActivity.class);
                    int absoluteIndex = viewHolder.getAbsoluteAdapterPosition();
                    int index = videoNewsIndex.get(absoluteIndex);
                    Log.i("NewsAdapter", "Select absoluteIndex: " + absoluteIndex);
                    Log.i("NewsAdapter", "Select index: " + index);
                    if (index == -1) throw new IllegalStateException("this is not a video");
                    intent.putExtra("index", index);
                    intent.putExtra("count", videoCount);
                    intent.putExtra("isFollow",false);
                    context.startActivity(intent);
                }
            });
            return viewHolder;
        }else {
            return EmptyViewHolder.newInstance(parent);
        }
    }



    /**
     * newsList 中的视频在 videoList 中的下标
     */
    private List<Integer> videoNewsIndex = null;

    /**
     * 视频数量
     */
    private int videoCount = 0;

    /**
     * 清除
     */
    public void cleanVideoIndex() {
        videoCount = 0;
        videoNewsIndex = null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (videoNewsIndex == null) videoNewsIndex = new ArrayList<>();
        if (holder.getItemViewType() == R.layout.news_footer) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.bindWithNetWorkStatus(netWorkStatus);
        } else if (holder.getItemViewType() == R.layout.item_news_list) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            viewHolder.bindWithMyNews(getItem(position));
            if (videoNewsIndex.size() == position) videoNewsIndex.add(-1);
        } else if(holder.getItemViewType() == R.layout.item_video_list){
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.bindWithMyNews(getItem(position));
            if (videoNewsIndex.size() == position) videoNewsIndex.add(videoCount++);
        }else if(isEmptyPosition(position)){
            EmptyViewHolder viewHolder = (EmptyViewHolder)holder;
            viewHolder.bind();
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasFooter ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasFooter && position == getItemCount() - 1) {
            return R.layout.news_footer;
        }else if (isEmptyPosition(position)){
            return R.layout.fragment_blank;
        }else if (getItem(position).getType().equals(0)) {
            return R.layout.item_news_list;
        } else{
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
            if (myNews.getNewsPhotoUrl() != null)
            Glide.with(itemView).load(Uri.parse(myNews.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(imageViewPic);
        }

    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        ProgressBar progressBar;

        private FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textViewFooter);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        private static FooterViewHolder newInstance(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer, parent, false);
            return new FooterViewHolder(itemView);
        }

        public void bindWithNetWorkStatus(NewsDataSource.NetWorkStatus netWorkStatus) {
            if(netWorkStatus == NewsDataSource.NetWorkStatus.FAILED){
                mTextView.setText("点击重试");
                progressBar.setVisibility(View.GONE);
                itemView.setClickable(true);
            }else if(netWorkStatus == NewsDataSource.NetWorkStatus.COMPLETED){
                mTextView.setText("加载完毕");
                progressBar.setVisibility(View.GONE);
                itemView.setClickable(false);
            }else {
                mTextView.setText("正在加载");
                progressBar.setVisibility(View.VISIBLE);
                itemView.setClickable(false);
            }
        }
    }


    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle;
        ImageView videoPreview;

        private VideoViewHolder(@NonNull View itemView) {
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

            if (myNews.getNewsPhotoUrl() != null)
            Glide.with(itemView).load(Uri.parse(myNews.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(videoPreview);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder{

        private EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public static EmptyViewHolder newInstance(ViewGroup parent){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list, parent, false);
            return new EmptyViewHolder(itemView);
        }

        public void bind(){

        }
    }


    /**
     * 判断是否是空布局
     */
    public boolean isEmptyPosition(int position) {
        int count = getItemCount();
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
