package com.example.hm_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hm_mad.mod.Users;
import com.example.hm_mad.priv.priv;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText inputname,pass;
    private Button login;
    private ProgressDialog loding;
    private String padbName = "Users";
    private CheckBox checkbx;
    private TextView admin,notAdin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.loginbt);
        inputname = (EditText) findViewById(R.id.usernm);
        pass = (EditText) findViewById(R.id.password);
        admin = (TextView) findViewById(R.id.admin_im);
        notAdin = (TextView) findViewById(R.id.admin_not);
        loding = new ProgressDialog(this);

        checkbx =(CheckBox)findViewById(R.id.check_box);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Admin Login");
                admin.setVisibility(View.INVISIBLE);
                notAdin.setVisibility(View.VISIBLE);
                padbName = "Admins" ;


            }
        });
        notAdin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Log In");
                admin.setVisibility(View.VISIBLE);
                notAdin.setVisibility(View.INVISIBLE);
                padbName = "Users";

            }
        });
    }

    private void loginuser() {
        String name = inputname.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter your password",Toast.LENGTH_SHORT).show();
        }
        else {
            loding.setTitle("Lording Account");
            loding.setMessage("Place wait, Checking your Account");
            loding.setCanceledOnTouchOutside(false);
            loding.show();

            LogintoAc(name,password);
        }
    }

    private void LogintoAc(final String name, final String password) {
        if (checkbx.isChecked()){
            Paper.book().write(priv.usernamekey,name);
            Paper.book().write(priv.userpasswordkey,password);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(padbName).child(name).exists()){
                    Users userdata = dataSnapshot.child(padbName).child(name).getValue(Users.class);


                    if (userdata.getUser().equals(name)){

                        if (userdata.getPassword().equals(password)){
                            if(padbName.equals("Admins")){

                                Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT). show();
                                loding.dismiss();
                                try { Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                    startActivity(intent); } catch(Exception ex) { ex.printStackTrace(); } finally {
                                    finish(); }
                            }
                            else if(padbName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Welcome  "+name, Toast.LENGTH_SHORT).show();
                                loding.dismiss();

                                Intent intent = new Intent(LoginActivity.this,Home.class);
                                priv.onlneuser = userdata;
                                startActivity(intent);
                            }
                        }
                        else {
                            loding.dismiss();
                            Toast.makeText(LoginActivity.this, "Password incorrect ", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, ""+name+" Account Do not exists", Toast.LENGTH_SHORT).show();
                    loding.dismiss();
                    Toast.makeText(LoginActivity.this, "Place Register before Log in", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
