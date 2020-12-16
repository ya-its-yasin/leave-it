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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button bt;
    Spinner s;

    //Data for populating in Spinner
    String [] lg_array={"admin","faculty","student"};

    String num,pass,who,ss="";
    String myurl="https://leaveitdiya.000webhostapp.com/log_chk.php?num=";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1= (EditText) findViewById(R.id.lgname);
        e2= (EditText) findViewById(R.id.lgpass);

        bt= (Button) findViewById(R.id.lg);

        s= (Spinner) findViewById(R.id.lgspiner);


        //Creating Adapter for Spinner for adapting the data from array to Spinner
        ArrayAdapter adapter= new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,lg_array);
        s.setAdapter(adapter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=e1.getText().toString();
                pass=e2.getText().toString();
                who=s.getSelectedItem().toString();

                if(who.equals("admin") /*&& name.equals("ya") && pass.equals("sin")*/)
                {
                    Intent i = new Intent(MainActivity.this,AdminHomePage.class);
                    startActivity(i);
                }

                else
                {
                    myurl=myurl.concat(num);
                    myurl=myurl.concat("&pass=");
                    myurl=myurl.concat(pass);
                    myurl=myurl.concat("&who=");
                    myurl=myurl.concat(who);

                    getJSON(myurl);
                }


            }
        });

    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
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
            protected String doInBackground(Void... voids) {
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
        String temp;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ss= obj.getString("status");
            //temp=temp.concat("   ");
            //heroes[i] =temp.concat(obj.getString("from"));
        }
        if(ss.equals("1"))
        {
            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), StudentHomePage.class);

            intent.putExtra("num_key", num);

            startActivity(intent);
        }
        else if (ss.equals("2"))
        {
            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), FacultyHomePage.class);

            intent.putExtra("num_key", num);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);
        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
    }


}
