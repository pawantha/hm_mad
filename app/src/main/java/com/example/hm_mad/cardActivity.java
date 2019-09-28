package com.example.hm_mad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hm_mad.payment.produc;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cardActivity extends AppCompatActivity {
    EditText type, crdno, Date, Cvv, Total;
    Button pay;

    DatabaseReference dberf;
    produc Produc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card2);
        type = (EditText) findViewById(R.id.visa);
        crdno = (EditText) findViewById(R.id.xx);
        Date = (EditText) findViewById(R.id.date);
        Cvv = (EditText) findViewById(R.id.cvv);
        Total = (EditText) findViewById(R.id.total);
        pay = (Button) findViewById(R.id.Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cardActivity.this,Retdata.class);
                startActivity(intent);
            }
        });
        Produc = new produc();
        dberf = FirebaseDatabase.getInstance().getReference().child("Product ");
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Produc.setNameonCard(type.getText().toString().trim());
                Produc.setExpDate(Date.getText().toString().trim());
                Produc.setSecCode(Cvv.getText().toString().trim());
                Produc.setPrice(Total.getText().toString().trim());
                Produc.setCardNo(crdno.getText().toString().trim());
                dberf.push().setValue(Produc);
                Toast.makeText(cardActivity.this, "Data added", Toast.LENGTH_SHORT).show();

                opendataretrive();
            }
        });


    }

    private void opendataretrive() {

    }

}
