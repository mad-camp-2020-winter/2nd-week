package com.example.rentalservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rentalservice.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class GalleryAddActivity extends Activity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;
    private Uri selectedImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_gallery_add);

        imageview = (ImageView)findViewById(R.id.gallery_add_button);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        EditText galleryName = findViewById(R.id.gallery_add_name);
        EditText galleryCount = findViewById(R.id.gallery_add_count);

        Button save = findViewById(R.id.gallery_add_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 Base64 Bit 변환
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                Resources res= getResources();

                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(GalleryAddActivity.this.getContentResolver(), selectedImageUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //test용
                bitmap =  BitmapFactory.decodeResource(res, R.drawable.contruction);
                Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                bmpCompressed.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                byte[] image = outStream.toByteArray();
                String profileImageBase64 = Base64.encodeToString(image, 0);

                String name = galleryName.getText().toString();
                String count = galleryCount.getText().toString();

                Intent i = new Intent();
                i.putExtra("name",name);
                i.putExtra("count", count);
                i.putExtra("photo", profileImageBase64);
                setResult(RESULT_FIRST_USER, i);
                finish();
            }
        });

        Button cancel = findViewById(R.id.gallery_add_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }
}