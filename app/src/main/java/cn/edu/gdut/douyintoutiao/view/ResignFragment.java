package cn.edu.gdut.douyintoutiao.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import cn.edu.gdut.douyintoutiao.R;

/**
 * @author hudp
 * @date 2020/11/7 21:43
 */
public class ResignFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resign, container, false);
    }
}