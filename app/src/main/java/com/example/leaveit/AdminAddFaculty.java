package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class AdminAddFaculty extends AppCompatActivity {

    String ServerURL = "https://leaveitdiya.000webhostapp.com/add_faculty.php" ;

    EditText faname,faid,fapass;
    Button faadd;
    String sfaname, sfaid,sfapass ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_faculty);

        faname= (EditText) findViewById(R.id.faname);
        faid= (EditText) findViewById(R.id.faid);
        fapass= (EditText) findViewById(R.id.fapass);
        faadd= (Button) findViewById(R.id.faadd);

        faadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();

                InsertData(sfaid, sfaname,sfapass);

                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);

            }
        });
    }


    public void GetData(){

        sfaid = faid.getText().toString();
        sfaname = faname.getText().toString();
        sfapass = fapass.getText().toString();


    }

    public void InsertData(final String sfaid,final String sfaname, final String sfapass){

        class SendPostReqAsyncTask extends AsyncTask<String,Void, String> {
            @Override
            protected String doInBackground(String... params) {

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
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(AdminAddFaculty.this, "Faculty Added Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(sfaid,sfaname,sfapass);
    }

}
