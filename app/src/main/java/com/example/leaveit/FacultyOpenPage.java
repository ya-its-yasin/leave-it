package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FacultyOpenPage extends AppCompatActivity {

    TextView sname,srollno,from,to,fromtime,totime,type,reason;
    Button ap,rj,mm;
    String ssname,ssfrom,tr,faid;
    String myurl="https://leaveitdiya.000webhostapp.com/req_det.php?name=";
    String ServerURL = "https://leaveitdiya.000webhostapp.com/req_res.php?stname=" ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_open_page);
        sname=(TextView)findViewById(R.id.sname);
        srollno=(TextView)findViewById(R.id.srollno);
        from=(TextView)findViewById(R.id.from);
        to=(TextView)findViewById(R.id.to);
        fromtime=(TextView)findViewById(R.id.fromtime);
        totime=(TextView)findViewById(R.id.totime);
        type=(TextView)findViewById(R.id.type);
        reason=(TextView)findViewById(R.id.reason);
        ap=(Button)findViewById(R.id.approve);
        rj=(Button)findViewById(R.id.disapprove);
        mm=(Button)findViewById(R.id.meetme);


        Intent intent1 = getIntent();

        tr = intent1.getStringExtra("tr_key");
        faid=intent1.getStringExtra("tr1_key");
        //sname.setText(tr);
        myurl=myurl.concat(tr);
        getJSON(myurl);

       ServerURL=ServerURL.concat(tr);
        ServerURL=ServerURL.concat("&resp=");


        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ServerURL=ServerURL.concat("ap&faid=");
              ServerURL=ServerURL.concat(faid);
                Toast.makeText(getApplicationContext(), "Approved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FacultyApprovalPage.class);

                intent.putExtra("url_key",ServerURL);

                startActivity(intent);




            }
        });

        rj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerURL=ServerURL.concat("rj&faid=");



            }
        });

        mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerURL=ServerURL.concat("mm&faid=");


            }
        });


    }

    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            public void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    public void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);


        String temp;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            sname.setText(obj.getString("name"));
            srollno.setText(obj.getString("rollno"));
            from.setText( obj.getString("fromdate"));
            to.setText( obj.getString("todate"));
            fromtime.setText( obj.getString("fromtime"));
            totime.setText( obj.getString("totime"));
            type.setText( obj.getString("type"));
            reason.setText( obj.getString("reason"));

        }


    }




}
