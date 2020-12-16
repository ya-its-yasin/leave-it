package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentHomePage extends AppCompatActivity {

    String ServerURL = "https://leaveitdiya.000webhostapp.com/req_leave.php" ;

    TextView snamet;
    EditText from,to,fromtime,totime,reason,trackfrom;
    Button req,trackreq;
    Spinner type;
    String snam,sname,sfrom,sto,sfromtime,stotime,stype,sreason,strackfrom;
    String [] lve_arr={"Leave","On Duty"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        from= (EditText) findViewById(R.id.from);
        to= (EditText) findViewById(R.id.to);
        fromtime= (EditText) findViewById(R.id.fromtime);
        totime= (EditText) findViewById(R.id.totime);
        reason= (EditText) findViewById(R.id.reason);
        req= (Button) findViewById(R.id.req);
        trackfrom= (EditText) findViewById(R.id.trackfrom);
        trackreq= (Button) findViewById(R.id.trackreq);
        type=(Spinner) findViewById(R.id.lve);
        snamet=(TextView)findViewById(R.id.snamet);

        Intent intent = getIntent();

        snam = intent.getStringExtra("num_key");
        snamet.setText(snam);

        //Creating Adapter for Spinner for adapting the data from array to Spinner
        ArrayAdapter adapter= new ArrayAdapter(StudentHomePage.this,android.R.layout.simple_spinner_item,lve_arr);
        type.setAdapter(adapter);

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
                InsertData(sname,sfrom,sto,sfromtime,stotime,stype,sreason);

                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
            }
        });

        trackreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tr=new Intent(StudentHomePage.this,StudentTrackRequest.class);
                startActivity(tr);
            }
        });

    }


    public void GetData(){

        sfrom = from.getText().toString();
        sto = to.getText().toString();
        sfromtime = fromtime.getText().toString();
        stotime = totime.getText().toString();
        stype=type.getSelectedItem().toString();
        sreason = reason.getText().toString();
        //sname=getIntent().getStringExtra("name_key");
        //Bundle bundle=getIntent().getExtras();
        //sname=bundle.getString("name_key");
        sname=snamet.getText().toString();


    }

    public void InsertData(final String sname,final String sfrom,final String sto, final String sfromtime,final String stotime,final String stype, final String sreason){

        class SendPostReqAsyncTask extends AsyncTask<String,Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("rollno", sname));
                nameValuePairs.add(new BasicNameValuePair("from", sfrom));
                nameValuePairs.add(new BasicNameValuePair("to", sto));
                nameValuePairs.add(new BasicNameValuePair("fromtime", sfromtime));
                nameValuePairs.add(new BasicNameValuePair("totime", stotime));
                nameValuePairs.add(new BasicNameValuePair("type", stype));
                nameValuePairs.add(new BasicNameValuePair("reason", sreason));

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
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(StudentHomePage.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sname,sfrom,sto,sfromtime,stotime,stype,sreason);
    }

}
