package cn.edu.gdut.douyintoutiao.view.show.comment.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
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
    // 普通的item ViewType
    private static final int TYPE_ITEM = 1;
    // 空布局的ViewType
    private static final int TYPE_EMPTY = 2;

    private Context context;
    // 是否显示空布局，默认不显示
    private boolean showEmptyView = false;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setDiscussList(List<Discuss> discussList) {
        this.discussList = discussList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_EMPTY){
            return new ViewHolder(getEmptyView(parent));
        }else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_news_comment_list, parent, false);
            return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isEmptyPosition(position)){
            return ;
        }
        DateFormat dtf = DateFormat.getDateTimeInstance();
        Discuss cur = discussList.get(position);
        holder.textViewUsername.setText(cur.getUser().get(0).getUserName());
        holder.textViewCommentContent.setText(cur.getText());
        holder.textViewCommentTime.setText(dtf.format(cur.getTime()));
        if (cur.getUser().get(0).getUserImageUrl() != null)
        Glide.with(holder.itemView).load(Uri.parse(cur.getUser().get(0).getUserImageUrl())).placeholder(R.drawable.photo_placeholder).into(holder.imageViewUserPic);
    }

    @Override
    public int getItemCount() {
        int count = discussList != null ? discussList.size() : 0;
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
            return TYPE_ITEM;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewUserPic;
        private final TextView textViewUsername;
        private final TextView textViewCommentContent;
        private final TextView textViewCommentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUserPic = itemView.findViewById(R.id.imageViewUserImage);
            textViewUsername = itemView.findViewById(R.id.textView_item_author_name);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewCommentTime = itemView.findViewById(R.id.textViewCommentTime);
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
        int count = discussList != null ? discussList.size() : 0;
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
