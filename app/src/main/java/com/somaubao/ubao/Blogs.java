package com.somaubao.ubao;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Blogs extends Fragment {

    ListView blogList;
    String[] blogItems = {"MICHUZI BLOG", "BONGO 5", "SHAIFH DAUDA", "MISSIE POPULAR", "UBAO", "GSM ARENA", "THE VERGE", "MKBHD", "UNBOXTHERAPY"};

    public Blogs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blogs, container, false);
        blogList = (ListView) view.findViewById(R.id.blogList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, blogItems);
        blogList.setAdapter(arrayAdapter);
        return view;
    }


}

