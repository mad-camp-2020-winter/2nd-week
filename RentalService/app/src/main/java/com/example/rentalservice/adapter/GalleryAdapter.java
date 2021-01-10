package com.example.rentalservice.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rentalservice.R;
import com.example.rentalservice.models.Item;
import com.example.rentalservice.ui.main.PageViewModel;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.CustomViewHolder> {

    private OnListItemLongSelectedInterface mLongListener;
    private OnListItemSelectedInterface mListener;

    //누를 때, 길게 누를때 상황에 맞춰 override
    public interface OnListItemLongSelectedInterface{
        void onItemLongSelected(View v, int position);
    }
    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }


    private Context context;
    private ArrayList<Item> mList = new ArrayList<Item>();

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView count;
        protected ImageView photo;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.count = (TextView) view.findViewById(R.id.count);
            this.photo = (ImageView) view.findViewById(R.id.photo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemSelected(v, getAdapterPosition());
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongListener.onItemLongSelected(v, getAdapterPosition());
                    return false;
                }
            });
        }
    }


    public GalleryAdapter(ArrayList<Item> list, OnListItemSelectedInterface listener, OnListItemLongSelectedInterface longListener) {
        this.mList = list;
        this.mListener = listener;
        this.mLongListener = longListener;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.admin_activity_info_gallery, viewGroup, false);

        context = view.getContext();
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        //화면 크기 맞춰 사진크기 조정
        DisplayMetrics displayMetrics= context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels / 3 - 10 ;
        int height = displayMetrics.heightPixels / 5 - 20;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

        viewholder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        viewholder.count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        viewholder.photo.setLayoutParams(params);

        viewholder.name.setGravity(Gravity.CENTER);
        viewholder.count.setGravity(Gravity.CENTER);


        viewholder.name.setText(mList.get(position).getName());
        viewholder.count.setText("수량 : " + mList.get(position).getCount());

        String data = mList.get(position).getPhoto();
        if(data == null) {
            viewholder.photo.setImageResource(R.drawable.phone); // 테스트용 임의 설정
        }
        else {
            //bitmap -> stream -> image 변환
            byte[] bytePlainOrg = Base64.decode(data, 0);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytePlainOrg);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            viewholder.photo.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public void addItem(Item item){
        mList.add(item);
        notifyDataSetChanged();
    }

    public Item getItem(int position){
        Item item = mList.get(position);
        return item;
    }

    public void removeItem(int position){
        mList.remove(position);
    }
}
