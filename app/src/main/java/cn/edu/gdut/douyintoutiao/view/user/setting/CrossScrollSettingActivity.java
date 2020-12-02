package cn.edu.gdut.douyintoutiao.view.user.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityCrossScrollSettingBinding;
import cn.edu.gdut.douyintoutiao.view.TabViewModel;
import cn.edu.gdut.douyintoutiao.view.show.video.MagicVideoPlayActivity;

public class CrossScrollSettingActivity extends AppCompatActivity {

    ActivityCrossScrollSettingBinding viewBinding;



    /**
     * 关注的 Tab
     */
    int tabFollowInt = 0;

    /**
     * 适配器
     */
    CrossScrollSettingAdapter adapter;

    RadioButton selectedButton;


    TabViewModel tabViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCrossScrollSettingBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());

        //model observer 注册
        tabViewModel = new ViewModelProvider(this).get(TabViewModel.class);
        tabViewModel.getTabs().observe(this, tabsObserver);




        SharedPreferences shp = getSharedPreferences(MagicVideoPlayActivity.SHP_KEY,MODE_PRIVATE);
        viewBinding.leftButton.setText(shp.getString(MagicVideoPlayActivity.LEFT,""));
        viewBinding.rightButton.setText(shp.getString(MagicVideoPlayActivity.RIGHT,""));
        viewBinding.upButton.setText(shp.getString(MagicVideoPlayActivity.UP,""));

        SharedPreferences userSHP = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        tabFollowInt = userSHP.getInt("followTabs", 0);

        //RecyclerView 配置
        viewBinding.recyclerViewMyTabToSelect.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        adapter = new CrossScrollSettingAdapter();
        viewBinding.recyclerViewMyTabToSelect.setAdapter(adapter);


        //三个按钮设置
        viewBinding.leftButton.setSelected(false);
        viewBinding.upButton.setSelected(true);
        viewBinding.rightButton.setSelected(false);

        viewBinding.leftButton.setOnClickListener(radioButtonSelectListener);
        viewBinding.rightButton.setOnClickListener(radioButtonSelectListener);
        viewBinding.upButton.setOnClickListener(radioButtonSelectListener);

        selectedButton = viewBinding.upButton;

        tabViewModel.getTabsFromNet();


    }



    class CrossScrollSettingViewHolder extends RecyclerView.ViewHolder {

        /**
         * 将 root 返回
         * @return
         */
        Button getButton() {
            return (Button)itemView;
        }

        public CrossScrollSettingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class CrossScrollSettingAdapter extends RecyclerView.Adapter<CrossScrollSettingViewHolder> {


        List<String> followTabs = new ArrayList<>();


        @NonNull
        @Override
        public CrossScrollSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CrossScrollSettingActivity.this).inflate(R.layout.item_single_tab_selection, parent, false);
            view.setOnClickListener(tabSelectButtonListener);
            return new CrossScrollSettingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CrossScrollSettingViewHolder holder, int position) {
            holder.getButton().setText(followTabs.get(position));
        }

        @Override
        public int getItemCount() {
            return followTabs.size();
        }

        void add(String tab) {
            followTabs.add(tab);
        }


    }


    View.OnClickListener tabSelectButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            selectedButton.setText(button.getText());
        }
    };





    Observer<String[]> tabsObserver = new Observer<String[]>() {
        @Override
        public void onChanged(String[] strings) {
            //添加 tabs
            int mode = 1;
            for (int i = 0; i < strings.length; i++) {
                if ((mode & tabFollowInt) == mode) {
                    adapter.add(strings[i]);
                }
            }
            adapter.notifyDataSetChanged();
        }
    };


    View.OnClickListener radioButtonSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(selectedButton)) return;
            selectedButton = (RadioButton) v;
            //取消选中
            if (!selectedButton.equals(viewBinding.upButton)) viewBinding.upButton.setSelected(false);
            if (!selectedButton.equals(viewBinding.leftButton)) viewBinding.leftButton.setSelected(false);
            if (!selectedButton.equals(viewBinding.rightButton)) viewBinding.rightButton.setSelected(false);
            //选中
            selectedButton.setSelected(true);
        }
    };


    @Override
    public void finish() {
        //保存设置
        SharedPreferences shp = getSharedPreferences(MagicVideoPlayActivity.SHP_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString(MagicVideoPlayActivity.UP,(String)viewBinding.upButton.getText());
        editor.putString(MagicVideoPlayActivity.LEFT,(String)viewBinding.leftButton.getText());
        editor.putString(MagicVideoPlayActivity.RIGHT,(String)viewBinding.rightButton.getText());
        editor.apply();
        super.finish();
    }
}