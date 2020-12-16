package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FacultyHomePage extends AppCompatActivity {

    Button openreq;
    ListView listView;
    Spinner selreq;
    TextView tv;
    String myurl="https://leaveitdiya.000webhostapp.com/fac_leave_list.php?fac=";
    String fnam,trname,trfrom,tr;
    EditText stname,stfrom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home_page);
        openreq=(Button)findViewById(R.id.openreq);
        tv=(TextView)findViewById(R.id.fnamet);
        stname=(EditText)findViewById(R.id.stname);
        stfrom=(EditText) findViewById(R.id.from);
        selreq=(Spinner)findViewById(R.id.selreq);




        Intent intent1 = getIntent();

        fnam = intent1.getStringExtra("num_key");
        tv.setText(fnam);

        myurl=myurl.concat(fnam);

        listView = (ListView) findViewById(R.id.listView);
        getJSON(myurl);

        openreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tr=selreq.getSelectedItem().toString();

                Intent intent = new Intent(getApplicationContext(), FacultyOpenPage.class);

                intent.putExtra("tr_key",tr);
                intent.putExtra("tr1_key",fnam);

                startActivity(intent);

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
            public void onPostExecute(String s) {
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
        String[] heroes = new String[jsonArray.length()];
        String[] heroes1 = new String[jsonArray.length()];

        String temp;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            temp= obj.getString("name");
            heroes1[i]=temp;
            temp=temp.concat("   ");
            heroes[i] =temp.concat(obj.getString("from"));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes1);

        listView.setAdapter(arrayAdapter);
        selreq.setAdapter(arrayAdapter1);

    }

}
