package cn.edu.gdut.douyintoutiao.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import cn.edu.gdut.douyintoutiao.R;

/**
 * @author hayring
 * @date 2020/11/7 21:43
 */
public class MainFragment extends Fragment {

    Context context;



    Button playVideoButton;


    public MainFragment(Context context) {
        this.context = context;
    }

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        playVideoButton = view.findViewById(R.id.play_video_button);
        playVideoButton.setOnClickListener(playVideoListener);
        return view;
    }




    private View.OnClickListener playVideoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, FullscreenActivity.class);
            context.startActivity(intent);
        }
    };
}
