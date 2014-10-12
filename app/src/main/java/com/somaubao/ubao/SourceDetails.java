package com.somaubao.ubao;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.somaubao.ubao.R;

/**
 * Created by Tonny on 6/26/2014.
 */
public class SourceDetails extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(R.string.dialog_header);
        return inflater.inflate(R.layout.fragment_source_details, null);
    }
}
