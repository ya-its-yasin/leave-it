package com.example.leaveit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentTrackRequest extends AppCompatActivity {

    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_track_request);
        ok=(Button)findViewById(R.id.okay);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ok=new Intent(StudentTrackRequest.this,StudentHomePage.class);
                startActivity(ok);
            }
        });
    }
}
