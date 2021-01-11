package com.example.rentalservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rentalservice.InstitutionItem;
import com.example.rentalservice.R;

import java.util.ArrayList;

public class InstitutionAdapter extends BaseAdapter {
    private ArrayList<InstitutionItem> institutionItems = new ArrayList<InstitutionItem>();

    @Override
    public int getCount() {
        return institutionItems.size();
    }

    @Override
    public Object getItem(int position) {
        return institutionItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admin_listviewitem, parent, false);
        }

        TextView nameview = convertView.findViewById(R.id.list_item_name);
        TextView numberview = convertView.findViewById(R.id.list_item_number);
        TextView locationview = convertView.findViewById(R.id.list_item_location);

        InstitutionItem institutionItem = institutionItems.get(position);

        nameview.setText(institutionItem.getInstitution_name());
        numberview.setText(institutionItem.getInstitution_number());
        locationview.setText(institutionItem.getInstitution_location());

        return convertView;
    }

    public void addItem(InstitutionItem item){
        institutionItems.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        institutionItems.remove(position);
    }
}
