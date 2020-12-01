package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.edu.gdut.douyintoutiao.R;

/**
 * @author hayring
 * @date 11/26/20 5:20 PM
 */
public class MagicVideoPlayEmptySpaceFragment extends Fragment {

    int index;

    public void setIndex(int index) {
        this.index = index;
    }


//    TextView indexTag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_magic_video_empty_space, container, false);

//        indexTag = view.findViewById(R.id.index_tag);
//        indexTag.setText("index : " + index);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("ShowIndexFragment","index:" + index +" OnResume");
    }
}
