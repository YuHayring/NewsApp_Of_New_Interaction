package cn.edu.gdut.douyintoutiao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.News;


public class FollowAuthorListAdapter extends RecyclerView.Adapter<FollowAuthorListAdapter.ViewHolder>{

    private List<Follow> authorList;

    //首先定义了一个内部类ViewHolder , ViewHolder 要继承自RecyclerView.ViewHolder 。然后
    //ViewHolder 的构造函数中要传入一个View 参数， 这个参数通常就是RecyclerView 子项的最外
    //层布局， 那么我们就可以通过findViewByid()方法来获取到布局中的ImageView 和TextView
    //的实例了
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView authorImage;
        TextView authoId;

        public ViewHolder(View view) {
            super(view);
            authorImage = (ImageView) view.findViewById(R.id.test_friends_image);
            authoId = (TextView) view.findViewById(R.id.author_name);
        }
    }

    //FruitAdapter中也有一个构造函数， 这个方法用于把要展示的数据源传进来，
    //并赋值给一个全局变量mFru过List , 我们后续的操作都将在这个数据源的基础上进行
    public FollowAuthorListAdapter(List<Follow> list) {
        authorList = list;
    }

    //由于FruitAdapter 是继承自RecyclerView.Adapter 的， 那么就必须重写
    //onCreateViewHolder()、onBindViewHolder() 和getitemCount() 这3个方法。onCreateViewHolder() 方法
    // 是用于创建ViewHolder实例的， 我们在这个方法中将fruit_item 布局加载
    //进来， 然后创建一个ViewHolder 实例， 并把加载出来的布局传入到构造函数当中， 最后将
    //ViewHolder 的实例返回
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow_author_list, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {//对加载的子项注册监听事件
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Follow s = authorList.get(position);
                Toast.makeText(view.getContext(), " 你点击了" + s.getAuthorId(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {//对子项里的Image注册监听事件
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Follow s   = authorList.get(position);
                Toast.makeText(view.getContext(), " 你点击了" + s.getAuthorId(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    //onBindViewHolder() 方法是用于对RecyclerView 子项的数据进行赋值的，
    //会在每个子项被滚动到屏幕内的时候执行， 这里我们通过position 参数得到当前项的Fruit
    //实例， 然后再将数据设置到ViewHolder 的ImageView 和TextView 当中即可
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow fruit = authorList.get(position);
        holder.authoId.setText(fruit.getAuthorId());
    }

    //getItemCount() 方法就非常简单了， 它用于告诉RecyclerView 一共有多少子项， 直接返回数据源的长度就可以了
    @Override
    public int getItemCount() {
        return authorList.size();
    }
}
