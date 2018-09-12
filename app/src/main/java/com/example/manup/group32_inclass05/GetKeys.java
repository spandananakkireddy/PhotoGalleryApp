package com.example.manup.group32_inclass05;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by manup on 2/12/2018.
 */

public class GetKeys extends AsyncTask<String, Void,String[]> {

/*
    Ikeys ikeys;

    public GetKeys(Ikeys ikeys) {
        this.ikeys = ikeys;*/


    @Override
    protected String[] doInBackground(String... strings) {


        BufferedReader br;
        String[] str=null;

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String s = br.readLine();
            while (s != null) {
                str= s.split(";");
                Log.d("demo",str+"");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }





}
