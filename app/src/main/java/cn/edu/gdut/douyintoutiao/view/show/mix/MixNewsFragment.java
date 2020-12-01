package cn.edu.gdut.douyintoutiao.view.show.mix;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentNewsListBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.VerticalVideoPlayActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.VideoViewModel;
import cn.edu.gdut.douyintoutiao.view.show.video.singleplayer.SingleVideoPlayActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.videolist.VideoListFragment;

/**
 * @author hayring
 * @date 11/29/20 3:05 PM
 */
@Deprecated
public class MixNewsFragment extends Fragment {



    FragmentNewsListBinding viewBinding;



    private MixNewsViewModel viewModel;
    private MixAdapter adapter;

    public MixNewsFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentNewsListBinding.inflate(inflater);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MixAdapter();
        viewModel = new ViewModelProvider(this, mixViewModelFactory).get(MixNewsViewModel.class);
        viewBinding.recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        viewBinding.recyclerViewNews.setAdapter(adapter);
        viewModel.getNewsList().observe(getViewLifecycleOwner(), new Observer<List<MyNews>>() {
            @Override
            public void onChanged(List<MyNews> news) {
                adapter.addToHead(news);
                viewBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        viewBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getMoreVideoList(adapter.getItemCount());
            }
        });

        //获取视频列表
        viewModel.getList();

    }




    class MixAdapter extends RecyclerView.Adapter {

        private static final int VIDEO = 1;

        private static final int TEXT = 0;

        private List<MyNews> newsList = new ArrayList<>();



        public void setNewsList(List<MyNews> newsList) {
            this.newsList = newsList;
        }

        public void addToHead(List<MyNews> newList) {
            //TODO
            newsList.addAll(newList);
            notifyDataSetChanged();
        }


        /**
         * 获取类型
         * @param position
         * @return
         */
        @Override public int getItemViewType(int position) {
            return newsList.get(position).getType();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == TEXT) {
                MixNewsFragment.SingleTextViewHolder viewHolder;
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View itemView = layoutInflater.inflate(R.layout.item_news_list, parent, false);
                viewHolder = new MixNewsFragment.SingleTextViewHolder(itemView);
                return viewHolder;
            } else {
                MixNewsFragment.SingleVideoViewHolder viewHolder;
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View itemView = layoutInflater.inflate(R.layout.item_video_list, parent, false);
                viewHolder = new MixNewsFragment.SingleVideoViewHolder(itemView);
                return viewHolder;
            }
        }

        //处理对holder上的一些操作
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int position) {
            if (h instanceof SingleTextViewHolder) {
                SingleTextViewHolder holder = (SingleTextViewHolder)h;
                MyNews cur = newsList.get(position);
                holder.textViewHeader.setText(cur.getNewsName());
                holder.textViewAbstract.setText(cur.getNewsAbstract());
                //采用glide加载网络图片,采用了占位符方式优先展示。
                if (cur.getNewsPhotoUrl() != null)
                Glide.with(holder.itemView).load(Uri.parse(cur.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(holder.imageViewPic);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), NewsActivity.class);
                        intent.putExtra("uri", newsList.get(holder.getAbsoluteAdapterPosition()).getNewsDetailUrl());
                        intent.putExtra("newsId", newsList.get(holder.getAbsoluteAdapterPosition()).get_id());
                        intent.putExtra("tag", newsList.get(holder.getAbsoluteAdapterPosition()).getTag());
                        intent.putExtra("authorId", newsList.get(holder.getAbsoluteAdapterPosition()).getAuthor().get(0).getUserId());
                        getContext().startActivity(intent);
                    }
                });
            } else {
                SingleVideoViewHolder holder = (SingleVideoViewHolder)h;
                MyNews cur = newsList.get(position);
                holder.videoTitle.setText(""+position+cur.getNewsName());
                //采用glide加载网络图片,采用了占位符方式优先展示。
                if (cur.getNewsPhotoUrl() != null)
                Glide.with(holder.videoPreview).load(Uri.parse(cur.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(holder.videoPreview);


                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    /**
                     * 进入视频播放界面
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), SingleVideoPlayActivity.class);
                        intent.putExtra("news", (Serializable) adapter.getNewsList().get(holder.getAbsoluteAdapterPosition()));
                        ((Activity)getContext()).startActivityForResult(intent, 1);
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return newsList.size();
        }


        /**
         * 清除
         */
        public void clear() {
            newsList.clear();
        }


        public List<MyNews> getNewsList() {
            return newsList;
        }
    }

    //防止内存泄漏
    static class SingleVideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle;
        ImageView videoPreview;

        public SingleVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.text_view_single_video_title);
            videoPreview = itemView.findViewById(R.id.image_view_single_video_preview);
            videoPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    //防止内存泄漏
    static class SingleTextViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHeader, textViewAbstract;
        ImageView imageViewPic;

        public SingleTextViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            textViewAbstract = itemView.findViewById(R.id.textViewAbstract);
            imageViewPic = itemView.findViewById(R.id.imageViewPic);
        }
    }


    ViewModelProvider.Factory mixViewModelFactory = new ViewModelProvider.Factory() {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                Constructor constructor = modelClass.getConstructor(Activity.class);
                return (T) constructor.newInstance(getActivity());
            } catch (Exception e) {
                IllegalArgumentException ile = new IllegalArgumentException("" + modelClass + "is not" + VideoViewModel.class);
                ile.initCause(e);
                throw ile;
            }

        }
    };

}
