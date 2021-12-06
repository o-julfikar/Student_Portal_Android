package com.zulfikar.studentportal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
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
        try {
            InputStream inputStream = new URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                shapeableImageView.setImageBitmap(bitmap);
            }
        });
    }
}
