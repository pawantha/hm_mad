package com.example.hm_mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class cash extends AppCompatActivity {

    Button con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        con = (Button) findViewById(R.id.con);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openRetdata();
            }
        });
    }

    private void openRetdata() {
        Intent intent4 = new Intent(this, Retdata.class);
        startActivity(intent4);
    }


}
