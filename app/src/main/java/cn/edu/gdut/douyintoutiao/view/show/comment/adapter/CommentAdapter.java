package cn.edu.gdut.douyintoutiao.view.show.comment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.Discuss;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 11/13/20 10:38
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Discuss> discussList;


    public void setDiscussList(List<Discuss> discussList) {
        this.discussList = discussList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_news_comment_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discuss cur = discussList.get(position);
        holder.textViewUsername.setText(cur.getUser().get(0).getUserName());
        holder.textViewCommentContent.setText(cur.getText());
        holder.textViewCommentTime.setText(String.valueOf(cur.getTime()));
        //Glide.with(holder.itemView).load(Uri.parse())

    }

    @Override
    public int getItemCount() {
        return discussList == null ? 0 : discussList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewUserPic;
        private final TextView textViewUsername;
        private final TextView textViewCommentContent;
        private final TextView textViewCommentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUserPic = itemView.findViewById(R.id.imageViewUserImage);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewCommentTime = itemView.findViewById(R.id.textViewCommentTime);
        }
    }
}
