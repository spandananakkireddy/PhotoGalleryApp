package com.example.manup.group32_inclass05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by manup on 2/12/2018.
 */

public class GetData extends AsyncTask<String,Void,Bitmap> {

    MainActivity activity;
    ImageView ivdisplay;
    Interimage im;


    public GetData(ImageView iv, Interimage img) {
        ivdisplay = iv;
        im= img;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap image = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(strings[0]);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int status_code = con.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                image = BitmapFactory.decodeStream(con.getInputStream());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        im.startprog();


        //progressDialog= progressDialog.show(GetData.this,"Loading","progress");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        im.image(bitmap);
        im.stopprog();

    }

    public static interface Interimage {
        public void image(Bitmap bitmap);
        public void startprog();
        public void stopprog();

    }
}