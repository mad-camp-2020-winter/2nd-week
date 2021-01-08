package com.example.rentalservice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InfoDetailFragment extends Fragment {

    String _id;
    String name;
    String number;
    String location;
    MainActivity activity;

    public InfoDetailFragment(ListViewItem listViewItem) {
        _id = listViewItem.getInstitution_id();
        name = listViewItem.getInstitution_name();
        number = listViewItem.getInstitution_number();
        location = listViewItem.getInstitution_location();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
        System.out.println(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_detail, container, false);
        TextView inst_name = v.findViewById(R.id.info_detail_institution_name);
        TextView inst_location = v.findViewById(R.id.info_detail_institution_location);
        TextView inst_number = v.findViewById(R.id.info_detail_institution_number);
        ImageView inst_btn = v.findViewById(R.id.info_detail_btn);

        inst_name.setText(name);
        inst_location.setText(location);
        inst_number.setText(number);

        inst_btn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewItem listViewItem = new ListViewItem();
                activity.FragmentChange(0,listViewItem);
            }
        });

        return v;
    }


}