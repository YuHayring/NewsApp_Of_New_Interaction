package cn.edu.gdut.douyintoutiao.view.show.text.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.MyNews;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/11 11:10
 */
public class NewsSAdapter extends RecyclerView.Adapter<NewsSAdapter.ViewHolder> {

    List<MyNews> newsList = new ArrayList<>();

    public void setNewsList(List<MyNews> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_news_list, parent, false);
        return new ViewHolder(itemView);
    }

    //处理对holder上的一些操作
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyNews cur = newsList.get(position);
        holder.textViewHeader.setText(cur.getNewsName());
        holder.textViewAbstract.setText(cur.getNewsAbstract());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uri", cur.getNewsDetailUrl());
                NavController controller = Navigation.findNavController(holder.itemView);
                controller.navigate(R.id.newsDetailFragment, bundle);

               /* Uri uri = Uri.parse(cur.getNewsDetailUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    //防止内存泄漏
    static class ViewHolder extends RecyclerView.ViewHolder{
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
