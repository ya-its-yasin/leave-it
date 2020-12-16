package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacultyApprovalPage extends AppCompatActivity {

    TextView n1,n2,n3,n4,n5,n6,n7,n8;
    Button app,rej,mm;
    String snam,from,ServerURL,s1,s2,s3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_approval_page);
        n1=(TextView)findViewById(R.id.urlll);
        app=(Button)findViewById(R.id.button);

        Intent intent = getIntent();
        ServerURL= intent.getStringExtra("url_key");
        n1.setText(ServerURL);

        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
                InsertData(s1,s2,s3);
            }
        });




    }
    public void GetData(){

        s1="s1";
        s2="s2";
        s3="s3";


    }

    public void InsertData(final String sfaid,final String sfaname, final String sfapass){

        class SendPostReqAsyncTask extends AsyncTask<String,Void, String> {
            @Override
            public String doInBackground(String... params) {

                String IdHolder = sfaid ;
                String NameHolder = sfaname ;
                String PassHolder = sfapass ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("id", IdHolder));
                nameValuePairs.add(new BasicNameValuePair("pass", PassHolder));

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

                Toast.makeText(FacultyApprovalPage.this, "Faculty Added Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sfaid,sfaname,sfapass);
    }

}
