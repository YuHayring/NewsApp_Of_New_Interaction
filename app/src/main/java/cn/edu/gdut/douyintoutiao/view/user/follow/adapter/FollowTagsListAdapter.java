package cn.edu.gdut.douyintoutiao.view.user.follow.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowTagsListFragment;

public class FollowTagsListAdapter extends RecyclerView.Adapter<FollowTagsListAdapter.ViewHolder>{

    //private List<News> newsList;
    private List< FollowNews > dataList;
    private final Activity activity ;

    //声明自定义的监听接口
    private FollowTagsListFragment.OnItemClickListener followTagsItemClickListener;

    //首先定义了一个内部类ViewHolder , ViewHolder 要继承自RecyclerView.ViewHolder 。然后
    //ViewHolder 的构造函数中要传入一个View 参数， 这个参数通常就是RecyclerView 子项的最外
    //层布局， 那么我们就可以通过findViewByid()方法来获取到布局中的ImageView 和TextView
    //的实例了
    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView newsImage;
//        TextView newsName;
        TextView textViewHeader, textViewAbstract;
        ImageView imageViewPic;
        Button unFollowButton;
        public ViewHolder(View view) {
            super(view);
//            newsImage = (ImageView) view.findViewById(R.id.test_image);
//            newsName = (TextView) view.findViewById(R.id.news_name);

            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            textViewAbstract = itemView.findViewById(R.id.textViewAbstract);
            imageViewPic = itemView.findViewById(R.id.imageViewPic);
            unFollowButton = itemView.findViewById(R.id.button_unFollow_tags_list_);
        }
    }

    public void setDataList(List< FollowNews > dataList) {
        this.dataList = dataList;
    }

    public List< FollowNews > getDataList() {
        return dataList;
    }


    public void setOnItemClickListener(FollowTagsListFragment.OnItemClickListener onItemClickListener) {
        this.followTagsItemClickListener = onItemClickListener;
    }

    public FollowTagsListAdapter(Activity activity) {
        this.activity = activity;
    }

    //由于FruitAdapter 是继承自RecyclerView.Adapter 的， 那么就必须重写
    //onCreateViewHolder()、onBindViewHolder() 和getitemCount() 这3个方法。onCreateViewHolder() 方法
    // 是用于创建ViewHolder实例的， 我们在这个方法中将item 布局加载
    //进来， 然后创建一个ViewHolder 实例， 并把加载出来的布局传入到构造函数当中， 最后将
    //ViewHolder 的实例返回
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_follow_tags_list, parent, false);
        viewHolder = new ViewHolder(itemView);

        /**
         * 描述：将监听传递给自定义接口
         */
        viewHolder.unFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followTagsItemClickListener!=null){
                    followTagsItemClickListener.onUnFollowButtonClick(viewHolder.getAdapterPosition());
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followTagsItemClickListener!=null) {
                    followTagsItemClickListener.onItemViewClick(viewHolder.getAdapterPosition());
                }
            }
        });

        return viewHolder;
    }

    //onBindViewHolder() 方法是用于对RecyclerView 子项的数据进行赋值的，
    //会在每个子项被滚动到屏幕内的时候执行， 这里我们通过position 参数得到当前项的s
    //实例， 然后再将数据设置到ViewHolder 的ImageView 和TextView 当中即可
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        News s = newsList.get(position);
//        holder.newsName.setText(s.getNewsName());

        /* if (position == getItemCount() - 1) {
            return;
        }*/
        FollowNews data = dataList.get(position);
       holder.textViewHeader.setText(data.getFollowNews().get(0).getNewsName());
       holder.textViewAbstract.setText(data.getFollowNews().get(0).getNewsAbstract());
        //采用glide加载网络图片,采用了占位符方式优先展示。TODO 引入shimmerlayout做闪光效果
        Glide.with(holder.itemView).load(Uri.parse(data.getFollowNews().get(0).getNewsPhotoUrl())).placeholder(R.drawable.photo_placeholder).into(holder.imageViewPic);
        holder.unFollowButton.setText("已关注");
        //holder.unFollowButton.setBackgroundColor(R.color.light_blue_600);
    }

    //getItemCount() 方法就非常简单了， 它用于告诉RecyclerView 一共有多少子项， 直接返回数据源的长度就可以了
    @Override
    public int getItemCount() {
        if (dataList != null){
        //    System.out.println("getFollowTag失败");
        return dataList.size();}
        else {
            return 0;
        }
    }

}
