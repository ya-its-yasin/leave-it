package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomePage extends AppCompatActivity {

    Button faad,favw,stad,stvw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        faad=(Button)findViewById(R.id.faad);
        favw=(Button)findViewById(R.id.favw);
        stad=(Button)findViewById(R.id.stad);
        stvw=(Button)findViewById(R.id.stvw);

        faad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faad1 = new Intent(AdminHomePage.this,AdminAddFaculty.class);
                startActivity(faad1);
            }
        });

        favw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favw1 = new Intent(AdminHomePage.this,AdminViewFaculty.class);
                startActivity(favw1);
            }
        });

        stad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stad1 = new Intent(AdminHomePage.this,AdminAddStudent.class);
                startActivity(stad1);
            }
        });

        stvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stvw1 = new Intent(AdminHomePage.this,AdminViewStudent.class);
                startActivity(stvw1);
            }
        });
    }
}
