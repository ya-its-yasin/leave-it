package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminAddStudent extends AppCompatActivity {

    EditText stname,strollno,stpass;
    Button stadd;
    ListView listView;
    Spinner stmentor,stcp,sthod;
    String sname, srollno,smentor,scp,shod,spass ;
    String ServerURL = "https://leaveitdiya.000webhostapp.com/add_student.php" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);
        stname= (EditText) findViewById(R.id.stname);
        strollno= (EditText) findViewById(R.id.strollno);
        stpass= (EditText) findViewById(R.id.stpass);
        stadd= (Button) findViewById(R.id.stadd);
        stmentor= (Spinner) findViewById(R.id.stmentor);
        stcp= (Spinner) findViewById(R.id.stcp);
        sthod= (Spinner) findViewById(R.id.sthod);




        listView = (ListView) findViewById(R.id.listView);
        getJSON("https://leaveitdiya.000webhostapp.com/faculty_list.php");



        stadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();

                InsertData(sname,srollno,smentor,scp,shod,spass);

                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
            }
        });

    }

    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = obj.getString("name");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
        stmentor.setAdapter(arrayAdapter);
        stcp.setAdapter(arrayAdapter);
        sthod.setAdapter(arrayAdapter);
    }

    public void GetData(){

        sname = stname.getText().toString();
        srollno = strollno.getText().toString();

        smentor = stmentor.getSelectedItem().toString();
        scp = stcp.getSelectedItem().toString();
        shod = sthod.getSelectedItem().toString();


        spass=stpass.getText().toString();

    }




    public void InsertData(final String sname,final String srollno, final String smentor,final String scp,final String shod,final String spass){

        class SendPostReqAsyncTask extends AsyncTask<String,Void, String> {
            @Override
            public String doInBackground(String... params) {

                String sname1 = sname ;
                String srollno1 = srollno ;
                String smentor1 = smentor ;
                String scp1=scp;
                String shod1=shod;
                String spass1=spass;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", sname1));
                nameValuePairs.add(new BasicNameValuePair("rollno", srollno1));
                nameValuePairs.add(new BasicNameValuePair("mentor", smentor1));
                nameValuePairs.add(new BasicNameValuePair("cp", scp1));
                nameValuePairs.add(new BasicNameValuePair("hod", shod1));
                nameValuePairs.add(new BasicNameValuePair("pass", spass1));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            public void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(AdminAddStudent.this, "Student Added Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sname,srollno,smentor,scp,shod,spass);
    }


}

