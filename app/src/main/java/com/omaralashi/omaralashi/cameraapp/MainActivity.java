package com.omaralashi.omaralashi.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    static int REQUEST_IMAGE_CAPTURE = 1;
    static int SELECT_PHOTO = 3;
    static int REQUEST_VIDEO_CAPTURE = 5;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imageView2);

    }


    public void Shoot(View v) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
    }

    public void browseGallery(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv.setImageBitmap(imageBitmap);
            REQUEST_IMAGE_CAPTURE = 2;
        } else if (requestCode == SELECT_PHOTO && SELECT_PHOTO == 3) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    iv.setImageBitmap(yourSelectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                SELECT_PHOTO = 4;
            }
        } else if (requestCode == SELECT_PHOTO && SELECT_PHOTO == 4) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    iv.setImageBitmap(yourSelectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                SELECT_PHOTO = 3;
            }
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            Intent videoIntent = new Intent(Intent.ACTION_VIEW, videoUri);
            startActivity(videoIntent);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv.setImageBitmap(imageBitmap);
            REQUEST_IMAGE_CAPTURE = 1;
        }
    }
}
