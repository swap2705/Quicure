package com.swapnilshukla.quicure;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile extends Fragment {

    String str,em;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        TextView tvname2 = (TextView) view.findViewById(R.id.tvname);
        str = getArguments().getString("N");
        em = getArguments().getString("E");
        tvname2.setText(str);
        TextView textView= (TextView) view.findViewById(R.id.tvemail);
        textView.setText(em);
        return view;
    }
}
