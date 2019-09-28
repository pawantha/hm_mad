package com.example.hm_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SinginActivity extends AppCompatActivity {
    private Button creat_ac;
    private EditText uname, address,phone,pass;
    private ProgressDialog loding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);

        creat_ac = (Button) findViewById(R.id.SingIn);
        uname = (EditText) findViewById(R.id.usernm);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phonenum);
        pass = (EditText) findViewById(R.id.password);
        loding = new ProgressDialog(this);

        creat_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAcc();
            }
        });

    }
    private void createAcc() {
        String name = uname.getText().toString();
        String addres = address.getText().toString();
        String phone_num = phone.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addres)){
            Toast.makeText(this,"Enter your address",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone_num)){
            Toast.makeText(this,"Enter phone number",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
        }
        else {
            loding.setTitle("Create Account");
            loding.setMessage("Place wait, We are checking the credentials");
            loding.setCanceledOnTouchOutside(false);
            loding.show();

            validatedata(name,phone_num,addres,password);
        }
    }

    private void validatedata(final String name, final String phone_num, final String addres, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(name).exists())){
                    HashMap<String, Object> userdatamap = new HashMap<>();
                    userdatamap.put("user",name);
                    userdatamap.put("Address",addres);
                    userdatamap.put("phone",phone_num);
                    userdatamap.put("password",password);

                    RootRef.child("Users").child(name).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SinginActivity.this,"Your account has been created..",Toast.LENGTH_SHORT).show();
                                loding.dismiss();
                                Intent intent = new Intent(SinginActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                loding.dismiss();
                                Toast.makeText(SinginActivity.this,"Network Error: place check your connection",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(SinginActivity.this,"This"+ name + "already exists.",Toast.LENGTH_SHORT).show();
                    loding.dismiss();
                    Toast.makeText(SinginActivity.this,"place try another name",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SinginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
