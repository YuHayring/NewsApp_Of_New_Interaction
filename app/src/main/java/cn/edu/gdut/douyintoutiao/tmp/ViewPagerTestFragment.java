package cn.edu.gdut.douyintoutiao.tmp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.gdut.douyintoutiao.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 * @author hayring
 * @date 2020.11.13 14:57
 */
public class ViewPagerTestFragment extends Fragment {





    int index;

    public ViewPagerTestFragment(int index) {
        // Required empty public constructor
        this.index = index;
    }

    TextView textView;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager_test, container, false);
        textView = view.findViewById(R.id.index_text_view);
        textView.setText("Fragment index :" + index);
        return view;
    }
}