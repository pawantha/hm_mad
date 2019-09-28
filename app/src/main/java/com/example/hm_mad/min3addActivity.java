package com.example.hm_mad;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class min3addActivity extends AppCompatActivity {
    private String categoryNm,discription,price,pname,saveCurrentdate,savecurrentTime;
    private Button addpro;
    private EditText proname,prodis,proprice;
    private ImageView addimg;
    private static final int galarypic = 1;
    private Uri inamgeuri;
    private String productrmkey,downloadimgUrl;
    private StorageReference  proimageRef;
    private DatabaseReference productref;
    private ProgressDialog loding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min3add);
        loding = new ProgressDialog(this);
        categoryNm = getIntent().getExtras().get("category").toString();
        proimageRef = FirebaseStorage.getInstance().getReference().child("Product img");
        productref = FirebaseDatabase.getInstance().getReference().child("Products");

        addpro = (Button)findViewById(R.id.Admin_submit_new_pro);
        addimg = (ImageView)findViewById(R.id.select_produt);
        proname = (EditText) findViewById(R.id.pro_name);
        prodis = (EditText)findViewById(R.id.pro_dis);
        proprice = (EditText)findViewById(R.id.pro_price);

        Toast.makeText(this, categoryNm, Toast.LENGTH_SHORT).show();
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary();
            }
        });
        addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateprodata();
            }
        });
    }

    private void validateprodata() {
        discription = prodis.getText().toString();
        price = proprice.getText().toString();
        pname = proname.getText().toString();

        if(inamgeuri== null){
            Toast.makeText(this, "Image is needed", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(discription)){
            Toast.makeText(this, "description is needed", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pname)){
            Toast.makeText(this, "product name is needed", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "price is needed", Toast.LENGTH_SHORT).show();
        }
        else {
            storeproductdata();
        }

    }

    private void storeproductdata() {
        loding.setTitle("Admin product");
        loding.setMessage("Place wait, product is adding");
        loding.setCanceledOnTouchOutside(false);
        loding.show();
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM,DD,YYYY");
        saveCurrentdate = currentdate.format(calander.getTime());
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:MM:SS a");
        savecurrentTime = currenttime.format(calander.getTime());
        productrmkey = saveCurrentdate + savecurrentTime;

        final StorageReference fillparth = proimageRef.child(inamgeuri.getLastPathSegment()+productrmkey + ".jpg");
        final UploadTask uploadTask = fillparth.putFile(inamgeuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String massage = e.toString();
                Toast.makeText(min3addActivity.this, "Error: "+massage, Toast.LENGTH_SHORT).show();
                loding.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(min3addActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }
                        downloadimgUrl = fillparth.getDownloadUrl().toString();
                        return fillparth.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadimgUrl = task.getResult().toString();
                            Toast.makeText(min3addActivity.this, "Got product url", Toast.LENGTH_SHORT).show();
                            saveProdutInfotoDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveProdutInfotoDatabase() {
        HashMap<String, Object> productmap = new HashMap<>();
        productmap.put("pid",productrmkey);
        productmap.put("date",saveCurrentdate);
        productmap.put("time",savecurrentTime);
        productmap.put("dic",discription);
        productmap.put("image",downloadimgUrl);
        productmap.put("catagoyr",categoryNm);
        productmap.put("price",price);
        productmap.put("name",pname);
        productref.child(productrmkey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(min3addActivity.this,Main2Activity.class);
                    startActivity(intent);
                    loding.dismiss();
                    Toast.makeText(min3addActivity.this, "Product is added", Toast.LENGTH_SHORT).show();
                } else {
                    loding.dismiss();
                    String massage = task.getException().toString();
                    Toast.makeText(min3addActivity.this, "Erroe: "+massage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallary() {
        Intent galaryIntent = new Intent();
        galaryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galaryIntent.setType("image/*");
        startActivityForResult(galaryIntent,galarypic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galarypic && resultCode == RESULT_OK && data!= null){
            inamgeuri = data.getData();
            addimg.setImageURI(inamgeuri);

        }
    }
}
