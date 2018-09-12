// Group32_Inclass05
// Priyanka Manusanipally - 801017222
// Sai Spandana Nakireddy - 801023658


package com.example.manup.group32_inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements GetData.Interimage {


    TextView tvsearch;
    Button btngo;
    ImageView ivback;
    ImageView ivnext;
    ImageView ivdisplay;
    AlertDialog alertDialog;
    AlertDialog.Builder keyselect;
    ArrayList<String> urls = new ArrayList<String>();
    HashMap<String, ArrayList<String>> furls;
    ArrayList<String> str;
    int flag = 0;
    ProgressDialog progressDialog;
    ProgressDialog prog;
   String keyword;
    int end = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Main Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvsearch = (TextView) findViewById(R.id.tvsearch);
        btngo = (Button) findViewById(R.id.btngo);
        ivback = (ImageView) findViewById(R.id.ivback);
        ivnext = (ImageView) findViewById(R.id.ivnext);
        ivdisplay = (ImageView) findViewById(R.id.ivdisplay);
        progressDialog = new ProgressDialog(MainActivity.this);
        prog= new ProgressDialog(MainActivity.this);


        if (isConnected()) {


                    new getKeys().execute("http://dev.theappsdr.com/apis/photos/keywords.php");

                    Log.d("data", "turned execution");

           /*     }
            });*/
        } else {
            Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

        ivnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str != null) {
                    if (flag <= str.size()) {
                        flag++;
                        if (flag == str.size()) {
                            //new GetData((ImageView) ivdisplay, MainActivity.this).execute(str.get(flag));
                            flag = 0;
                        }

                        new GetData((ImageView) ivdisplay, MainActivity.this).execute(str.get(flag));

                    }
                    // new GetData((ImageView) ivdisplay, MainActivity.this).execute(str.get(flag));
                } else {
                    Toast.makeText(MainActivity.this, "Please select a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str != null) {
                    if (flag >= 0) {
                        if (flag == 0) {
                            //new GetData((ImageView) ivdisplay, MainActivity.this).execute(str.get(flag));
                            flag = str.size();
                        }
                        flag--;
                        new GetData((ImageView) ivdisplay, MainActivity.this).execute(str.get(flag));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please select a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void image(Bitmap bitmap) {
        if(isConnected()) {
            Log.d("image", "entered");
            ivdisplay.setImageBitmap(bitmap);
        }

    }

    @Override
    public void startprog() {
        progressDialog.setTitle("Loading Photo");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void stopprog() {
        progressDialog.dismiss();

    }


    public class getUrl extends AsyncTask<String, Void, ArrayList<String>> {
        BufferedReader br ;
        String x = "";
        ArrayList<String> str1 = new ArrayList<String>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prog.setTitle("Loading Dictionary");
            prog.setCancelable(false);
            prog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                Log.d("demo","url"+strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String s = "";
                    Log.d("strrr",s);
                    while ((s = br.readLine()) != null) {
                        sb.append(s);
                        str1.add(s);
                        Log.d("urlss", str1 + "");

                    }

                    x = sb.toString();
                }
                } catch(MalformedURLException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }

            return str1;

        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            //super.onPostExecute(strings);
            if(isConnected()) {
                prog.dismiss();
                str = strings;
                Log.d("urls", str + "");
//            Log.d("urlsspost", str.get(0) + "");
                new GetData((ImageView) ivdisplay, MainActivity.this).execute(str.get(0));
            }

        }

    }

    public class getKeys extends AsyncTask<String, Void, ArrayList<String>> {
        BufferedReader br;

        StringBuilder sb=null;
        ArrayList<String> str1 = new ArrayList<String>();


        @Override
        protected ArrayList<String> doInBackground(String... strings) {
                  try {
                URL url = new URL(strings[0]);

                Log.d("url",strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                      /*con.setRequestMethod("GET");
                      con.connect();*/
                      if(con.getResponseCode()== HttpURLConnection.HTTP_OK) {
                          br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                          sb = new StringBuilder();
                          String s = "";
                          end =0;
                          Log.d("st", s);
                          while ((s = br.readLine()) != null) {
                              sb.append(s);
                              Log.d("str", sb + "");
                          }
                          for (int k = 0; k < sb.length(); k++) {
                              if (sb.charAt(k) == ';') {
                                  String s1 = sb.substring(end, k);
                                  end=k+1;
                                  str1.add(s1);
                              }

                          }
                      }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str1;
        }



        @Override
        protected void onPostExecute(final ArrayList<String> strings) {
            //super.onPostExecute(strings);
            keyselect = new AlertDialog.Builder(MainActivity.this);
            btngo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    keyselect.setTitle("Choose a Keyword");
                    keyselect.setCancelable(false);
                    keyselect.setItems(strings.toArray(new CharSequence[strings.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            keyword = strings.get(i);
                            //Log.d("keyword",  keyword+"");
                            tvsearch.setText(keyword + "");
                             new getUrl().execute(" http://dev.theappsdr.com/apis/photos/index.php?keyword=" + keyword + "");
                        }
                    });
                    alertDialog = keyselect.create();
                    keyselect.show();

                    Log.d("keyword", keyword + "");
                    flag = 0;
                }
            });

        }
    }
    private Boolean isConnected() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE) {
            return false;
        }
        return true;
    }
}

