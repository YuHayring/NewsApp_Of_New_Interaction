package cn.edu.gdut.douyintoutiao.view.user.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityMyTabSettingBinding;
import cn.edu.gdut.douyintoutiao.view.TabViewModel;
import es.dmoral.toasty.Toasty;

/**
 * 设置关注 tab
 * @author hayring
 * @date 2020.12.01
 */
public class MyTabSettingActivity extends AppCompatActivity {

    ActivityMyTabSettingBinding viewBinding;

    TabViewModel tabViewModel;

    MyTabsAdapter adapter = new MyTabsAdapter();

    /**
     * 关注 tab
     */
    int tabFollow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMyTabSettingBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());

        //model observer 注册
        tabViewModel = new ViewModelProvider(this).get(TabViewModel.class);
        tabViewModel.getTabs().observe(this, tabsObserver);
        tabViewModel.getTabFollowUpdate().observe(this, tabsSaveObserver);

        //recycler view 初始化
        viewBinding.recyclerViewTabList.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.recyclerViewTabList.setAdapter(adapter);

        tabViewModel.getTabsFromNet();

        SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        tabFollow = shp.getInt("followTabs", 0);

    }


    /**
     * 适配器
     */
    class MyTabsAdapter extends RecyclerView.Adapter<TabSettingItemHolder> {

        List<String> tabs = new ArrayList<>();



        @NonNull
        @Override
        public TabSettingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SwitchCompat s = (SwitchCompat) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_setting,parent, false);
            return new TabSettingItemHolder(s);
        }

        @Override
        public void onBindViewHolder(@NonNull TabSettingItemHolder holder, int position) {
            int mod = 1 << position;
            holder.getSwitch().setText(tabs.get(position));
            holder.getSwitch().setChecked((mod & tabFollow) == mod);
            holder.getSwitch().setOnClickListener(new View.OnClickListener() {
                final int mode = mod;

                @Override
                public void onClick(View v) {
                    SwitchCompat switchCompat = (SwitchCompat) v;
                    if (switchCompat.isChecked()) {
                        tabFollow -= mode;
                    } else {
                        tabFollow += mode;
                    }
                    Log.i("tabFollow" , ""+ tabFollow);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }

        public void setTabs(List<String> tabs) {
            this.tabs = tabs;
        }


    };

    static class TabSettingItemHolder extends RecyclerView.ViewHolder {

        SwitchCompat getSwitch() {
            return (SwitchCompat)itemView;
        }

        public TabSettingItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    Observer<String[]> tabsObserver = new Observer<String[]>() {
        @Override
        public void onChanged(String[] strings) {
            adapter.setTabs(Arrays.asList(strings));
            adapter.notifyDataSetChanged();
        }
    };

    Observer<Boolean> tabsSaveObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean success) {
            if (success) {
                //保存关注 tabs
                SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shp.edit();
                editor.putInt("followTabs", tabFollow);
                editor.apply();
                //TODO 保存到 服务端
                MyTabSettingActivity.super.finish();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(MyTabSettingActivity.this)
                        .setTitle(R.string.tab_setting_save_failed_title)
                        .setMessage(R.string.tab_setting_save_failed)
                        .setPositiveButton(R.string.tab_setting_quit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MyTabSettingActivity.super.finish();
                            }
                        })
                        .setNegativeButton(R.string.tab_setting_stay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create();
                alertDialog.show();
            }
        }
    };

    @Override
    public void finish() {
        SharedPreferences shp = getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        String userId = shp.getString("userId","");
        tabViewModel.setTabFollow(userId, tabFollow);
//        Toasty.info(this,R.string.tab_setting_saving).show();
    }
}