package com.example.upfarm.sometools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getBitmap {

    public Bitmap returnBitmap(String url) {

        URL myFileUrl = null;

        Bitmap bitmap = null;

        try {

            myFileUrl = new URL(url);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        try {

            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();

            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

            is.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
//        Log.e("bitmap",bitmap.toString());

        return  bitmap;
    }
}
