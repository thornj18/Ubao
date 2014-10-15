package com.somaubao.ubao;


import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Newspapers extends Fragment {

    ListView newspaperList;
    String[] newspaperItems = {"MWANANCHI", "MTANZANIA", "HABARI LEO", "NIPASHE", "TANZANIA DAIMA", "MAJIRA", "DAILY NEWS", "THE CITIZEN", "KULIKONI"};

    public Newspapers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_newspapers, container, false);
        newspaperList = (ListView) rootView.findViewById(R.id.newspaperList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, newspaperItems);
        newspaperList.setAdapter(arrayAdapter);

 
        return rootView;

    }
}