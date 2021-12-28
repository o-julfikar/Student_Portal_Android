package com.zulfikar.studentportal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.google.android.material.imageview.ShapeableImageView;
import java.io.InputStream;
import java.net.URL;

public class FetchImage extends Thread {

    Bitmap bitmap;
    Handler handler;
    String imageURL;
    ShapeableImageView shapeableImageView;

    public FetchImage (ShapeableImageView shapeableImageView, String imageURL, Handler handler) {
        this.shapeableImageView = shapeableImageView;
        this.imageURL = imageURL;
        this.handler = handler;
    }

    @Override
    public void run() {
        if (imageURL != null && imageURL.length() > 0) {
            try {
                InputStream inputStream = new URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bitmap == null) {
                    shapeableImageView.setImageResource(R.drawable.userphoto);
                } else {
                    shapeableImageView.setImageBitmap(bitmap);
                }
            }
        });
    }
}
