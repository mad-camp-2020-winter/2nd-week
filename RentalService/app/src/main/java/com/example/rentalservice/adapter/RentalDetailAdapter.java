package com.example.rentalservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rentalservice.InstitutionItem;
import com.example.rentalservice.R;
import com.example.rentalservice.api.RetrofitAPI;
import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;
import com.example.rentalservice.models.RentalDetail;

import java.util.ArrayList;

import kotlin.reflect.KVisibility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RentalDetailAdapter extends BaseAdapter {
    private ArrayList<RentalDetail> RentalDetailItems = new ArrayList<RentalDetail>();

    @Override
    public int getCount() {
        return RentalDetailItems.size();
    }

    @Override
    public Object getItem(int position) {
        return RentalDetailItems.get(position);
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
            convertView = inflater.inflate(R.layout.admin_rentaldetailitem, parent, false);
        }

        TextView rental_date = convertView.findViewById(R.id.rental_item_date);
        TextView rental_institution = convertView.findViewById(R.id.rental_institution_name);
        TextView rental_item_info = convertView.findViewById(R.id.rental_item_info);
        TextView rental_approval = convertView.findViewById(R.id.rental_approval);
        TextView rental_order = convertView.findViewById(R.id.rental_order);
        RentalDetail rentalDetail = RentalDetailItems.get(position);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.18.173:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Institution> institutionCall = retrofitAPI.getInstitutionById(rentalDetail.getInstitution_id());
        institutionCall.enqueue(new Callback<Institution>() {
            @Override
            public void onResponse(Call<Institution> call, Response<Institution> response) {
                if(response.isSuccessful()){
                    rental_institution.setText(response.body().getName());
                    rentalDetail.setInstitution_name(response.body().getName());
                    Call<Item> itemCall = retrofitAPI.getItemById(rentalDetail.getItem_id());
                    itemCall.enqueue(new Callback<Item>() {
                        @Override
                        public void onResponse(Call<Item> call, Response<Item> response) {
                            if(response.isSuccessful()){
                                rental_item_info.setText("Item: " + response.body().getName() + "(" + rentalDetail.getCount() + ")");
                            }
                        }
                        @Override
                        public void onFailure(Call<Item> call, Throwable t) {
                        }
                    });

                    rental_order.setText(String.valueOf(position + 1));
                    rental_order.setTextColor(Color.parseColor("#000000"));
                    int date = rentalDetail.getRental_date();
                    int year = date/10000;
                    int month = (date-year*10000)/100;
                    int day = (date-year*10000-month*100);
                    rental_date.setText( year + "년 " + month + "월 " + day + "일");
                    int approval = rentalDetail.getApproval();
                    if(approval == 0){
                        rental_approval.setText("승인\n대기중");
                        rental_approval.setTextColor(Color.parseColor("#000000"));
                    }
                    else if(approval == 1){
                        rental_approval.setText("승인");
                        String green = "#17E209";
                        rental_approval.setTextColor(Color.parseColor(green));
                    }
                    else{
                        rental_approval.setText("반려");
                        String red = "#DB2B30";
                        rental_approval.setTextColor(Color.parseColor(red));
                    }
                }
            }
            @Override
            public void onFailure(Call<Institution> call, Throwable t) {
            }
        });



        return convertView;
    }

    public void addItem(RentalDetail item){
        RentalDetailItems.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        RentalDetailItems.remove(position);
    }

    public void removeAll() {RentalDetailItems = new ArrayList<RentalDetail>();}
}
