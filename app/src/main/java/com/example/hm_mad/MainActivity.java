package com.example.hm_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hm_mad.mod.Users;
import com.example.hm_mad.priv.priv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button singbt,logbt ;
    private ProgressDialog loding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singbt = (Button) findViewById(R.id.wl_si);
        logbt = (Button) findViewById(R.id.wl_lg);



        loding = new ProgressDialog(this);
        Paper.init(this);

        singbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SinginActivity.class);
                startActivity(intent);
            }
        });
        logbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        String usernamekey = Paper.book().read(priv.usernamekey);
        String userpasskey = Paper.book().read(priv.userpasswordkey);
        if (usernamekey != "" && userpasskey != ""){
            if (!TextUtils.isEmpty(usernamekey) && !TextUtils.isEmpty(userpasskey)){
                Allowac(usernamekey,userpasskey);
                loding.setTitle("Alrady in");
                loding.setMessage("Place wait, Checking your Account");
                loding.setCanceledOnTouchOutside(false);
                loding.show();
            }
        }

    }

    private void Allowac(final String name, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(name).exists()){
                    Users Userts = dataSnapshot.child("Users").child(name).getValue(Users.class);

                    if (Userts.getUser().equals(name)){
                        if (Userts.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Welcone  "+name, Toast.LENGTH_SHORT).show();
                            loding.dismiss();
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            priv.onlneuser = Userts;
                            startActivity(intent);
                        }
                        else {
                            loding.dismiss();
                            Toast.makeText(MainActivity.this, "Password incorrect ", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, ""+name+" Account Do not exists", Toast.LENGTH_SHORT).show();
                    loding.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
