package com.example.hm_mad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Retdata extends AppCompatActivity {
    EditText type, crdno, Date, Cvv, Total;
    Button btn;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retdata);

        type = (EditText) findViewById(R.id.visa);
        crdno = (EditText) findViewById(R.id.xx);
        Date = (EditText) findViewById(R.id.date);
        Cvv = (EditText) findViewById(R.id.cvv);
        Total = (EditText) findViewById(R.id.total);
        btn =(Button) findViewById(R.id.View);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("1");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String nameonCard = dataSnapshot.child("nameonCard").getValue().toString();
                        int cardNo = Integer.parseInt(dataSnapshot.child("cardNo").getValue().toString());
                        String expDate = dataSnapshot.child("expDate").getValue().toString();
                        String secCode = dataSnapshot.child("secCode").getValue().toString();
                        double price = Double.parseDouble(dataSnapshot.child("price").getValue().toString());

                        type.setText(nameonCard);
                        crdno.setText(cardNo);
                        Date.setText(expDate);
                        Cvv.setText(secCode);
                        Total.setText((int) price);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });





    }
}
