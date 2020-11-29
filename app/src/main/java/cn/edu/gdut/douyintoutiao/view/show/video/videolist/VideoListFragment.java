package cn.edu.gdut.douyintoutiao.view.show.video.videolist;

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
import androidx.recyclerview.widget.GridLayoutManager;
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
import cn.edu.gdut.douyintoutiao.view.show.video.VerticalVideoPlayActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.VideoViewModel;

/**
 * @author hayring
 * @date 11/29/20 2:38 AM
 */
public class VideoListFragment extends Fragment {


    /**
     * 行数
     */
    private static final int ROW_COUNT = 2;



    FragmentNewsListBinding viewBinding;



    private VideoListViewModel viewModel;
    private VideoGridAdapter adapter;

    public VideoListFragment() {
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
        adapter = new VideoGridAdapter();
        viewModel = new ViewModelProvider(this, videoViewModelFactory).get(VideoListViewModel.class);
        viewBinding.recyclerViewNews.setLayoutManager(new GridLayoutManager(getContext(), ROW_COUNT));
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
        viewModel.getVideoList();

    }




    class VideoGridAdapter extends RecyclerView.Adapter<SingleVideoViewHolder> {

        private List<MyNews> newsList = new ArrayList<>();



        public void setNewsList(List<MyNews> newsList) {
            this.newsList = newsList;
        }

        public void addToHead(List<MyNews> newList) {
            //TODO
            newsList.addAll(newList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SingleVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SingleVideoViewHolder viewHolder;
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.item_video_list, parent, false);
            viewHolder = new SingleVideoViewHolder(itemView);
            return viewHolder;
        }

        //处理对holder上的一些操作
        @Override
        public void onBindViewHolder(@NonNull SingleVideoViewHolder holder, int position) {

            MyNews cur = newsList.get(position);
            holder.videoTitle.setText(""+position+cur.getNewsName());
            //采用glide加载网络图片,采用了占位符方式优先展示。
            Glide.with(holder.videoPreview).load(Uri.parse(cur.getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(holder.videoPreview);
            //设置 index
            holder.position = position;

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                /**
                 * 进入视频播放界面
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VerticalVideoPlayActivity.class);
                    intent.putExtra("data", (Serializable) adapter.getNewsList());
                    intent.putExtra("index", position);
                    ((Activity)getContext()).startActivityForResult(intent, 1);
                }
            });
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
        int position;

        public SingleVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.text_view_single_video_title);
            videoPreview = itemView.findViewById(R.id.image_view_single_video_preview);
            videoPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }


    ViewModelProvider.Factory videoViewModelFactory = new ViewModelProvider.Factory() {

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


    /**
     * 获取 VideoPlay 中刷新的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从视频播放 activity 返回时刷新数据
        adapter.clear();
        adapter.addToHead((List)data.getSerializableExtra("data"));
        adapter.notifyDataSetChanged();
    }
}
