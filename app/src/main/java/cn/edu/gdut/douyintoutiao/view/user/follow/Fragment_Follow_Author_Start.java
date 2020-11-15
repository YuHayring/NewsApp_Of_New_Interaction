package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.Activity_Follow_Author;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Follow_Author_Start#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Follow_Author_Start extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Follow_Author_Start() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Follow_Author.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Follow_Author_Start newInstance(String param1, String param2) {
        Fragment_Follow_Author_Start fragment = new Fragment_Follow_Author_Start();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Intent intent = new Intent(getActivity(), Activity_Follow_Author.class);
        startActivity(intent);

        return inflater.inflate(R.layout.fragment__follow__author_start, container, false);
    }
}