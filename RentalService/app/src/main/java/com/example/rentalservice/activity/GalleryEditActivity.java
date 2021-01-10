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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class GalleryEditActivity extends Activity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;
    private Uri selectedImageUri;

    //Intent로 받아오는 값
    private int getPosition;
    private String getId;
    private String getName;
    private String getCount;
    private String getPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        getPosition = i.getIntExtra("position",0);
        getId = i.getStringExtra("id");
        getName = i.getStringExtra("name");
        getCount = i.getStringExtra("count");
        getPhoto = i.getStringExtra("photo");

        setContentView(R.layout.admin_gallery_edit);

        imageview = (ImageView)findViewById(R.id.gallery_edit_button);
        if(getPhoto == null) {
            imageview.setImageResource(R.drawable.contruction); // 테스트용 임의 설정
        }
        else {
            //bitmap -> stream -> image 변환
            byte[] bytePlainOrg = Base64.decode(getPhoto, 0);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytePlainOrg);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageview.setImageBitmap(bitmap);
        }

        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        EditText galleryName = findViewById(R.id.gallery_edit_name);
        EditText galleryCount = findViewById(R.id.gallery_edit_count);

        galleryName.setText(getName);
        galleryCount.setText(String.valueOf(getCount));

        Button save = findViewById(R.id.gallery_edit_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 Base64 Bit 변환
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                Resources res= getResources();

                String profileImageBase64;
                Bitmap bitmap = null;
                if (selectedImageUri == null) {
                    profileImageBase64 = getPhoto;

                }
                else{
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(GalleryEditActivity.this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                    bmpCompressed.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                    byte[] image = outStream.toByteArray();
                    profileImageBase64 = Base64.encodeToString(image, 0);
                }

                String name = galleryName.getText().toString();
                String count = galleryCount.getText().toString();

                Intent i = new Intent();
                i.putExtra("id", getId);
                i.putExtra("name",name);
                i.putExtra("count", count);
                i.putExtra("photo", profileImageBase64);
                i.putExtra("position",getPosition);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        Button cancel = findViewById(R.id.gallery_edit_cancel);
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

            selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }
}