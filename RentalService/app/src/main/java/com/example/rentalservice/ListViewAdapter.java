package com.example.rentalservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
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
            convertView = inflater.inflate(R.layout.listviewitem, parent, false);
        }

        TextView nameview = convertView.findViewById(R.id.list_item_name);
        TextView numberview = convertView.findViewById(R.id.list_item_number);
        TextView locationview = convertView.findViewById(R.id.list_item_location);

        ListViewItem listViewItem = listViewItems.get(position);

        nameview.setText(listViewItem.getInstitution_name());
        numberview.setText(listViewItem.getInstitution_number());
        locationview.setText(listViewItem.getInstitution_location());

        return convertView;
    }

    public void addItem(ListViewItem item){
        listViewItems.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        listViewItems.remove(position);
    }
}
