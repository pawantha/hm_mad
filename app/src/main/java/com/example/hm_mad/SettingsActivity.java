package com.example.hm_mad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hm_mad.priv.priv;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.time.Instant;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView profileinmage_view;
    private TextView profileChange_text, cancel_text, update_text;
    private EditText setting_user_name,setting_full_name,setting_address;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask upload_tack;
    private StorageReference storageProfileimgRef;
    private String checkr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfileimgRef = FirebaseStorage.getInstance().getReference().child("image");

        profileinmage_view = (CircleImageView)findViewById(R.id.Setting_profile_img);
        profileChange_text = (TextView) findViewById(R.id.Update_User_name);
        cancel_text = (TextView) findViewById(R.id.Close_settings);
        update_text = (TextView)findViewById(R.id.Update_settings);
        setting_address = (EditText)findViewById(R.id.Setting_Address);
        setting_full_name = (EditText)findViewById(R.id.Setting_Full_name);
        setting_user_name = (EditText) findViewById(R.id.Setting_user_name);

        userInfoDisplay(profileinmage_view,setting_user_name,setting_full_name,setting_address);
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkr.equals("Clicked")){
                    userInfoSaved();
                }
                else {
                    updateOnlyuserInfo();
                }
            }
        });
        profileChange_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkr = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });

    }

    private void updateOnlyuserInfo() {
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("name",setting_user_name.getText().toString());
        userMap.put("Address",setting_address.getText().toString());
        userMap.put("fullname",setting_full_name.getText().toString());
        Ref.child(priv.onlneuser.getUser()).updateChildren(userMap);

        startActivity(new Intent(SettingsActivity.this,Home.class));
        Toast.makeText(SettingsActivity.this, "Profile info Updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileinmage_view.setImageURI(imageUri);
        }
        else {
            Toast.makeText(this, "Error : Try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(setting_full_name.getText().toString())){
            Toast.makeText(this, "Your name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(setting_user_name.getText().toString())){
            Toast.makeText(this, "Your name is user.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(setting_address.getText().toString())){
            Toast.makeText(this, "Your name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (checkr.equals("Clicked")){
            upload_Image();
        }
    }

    private void upload_Image() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Place wait.. We are updating your account...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null){
            final StorageReference fileRef = storageProfileimgRef.child(priv.onlneuser.getUser() + ".jpg");
            upload_tack = fileRef.putFile(imageUri);
            upload_tack.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();

                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String,Object> userMap = new HashMap<>();
                        userMap.put("name",setting_user_name.getText().toString());
                        userMap.put("Address",setting_address.getText().toString());
                        userMap.put("fullname",setting_full_name.getText().toString());
                        userMap.put("image",myUri);
                        Ref.child(priv.onlneuser.getUser()).updateChildren(userMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(SettingsActivity.this,Home.class));
                        Toast.makeText(SettingsActivity.this, "Profile info Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SettingsActivity.this, "Error .", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(this, "Image is not selected..", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(final CircleImageView profileinmage_view, final EditText setting_user_name, final EditText setting_full_name, final EditText setting_address) {
        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("Users").child(priv.onlneuser.getUser());

        Userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if (dataSnapshot.child("image").exists())
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("user").getValue().toString();
                        String address = dataSnapshot.child("Address").getValue().toString();
                        String fullName = dataSnapshot.child("fullName").getValue().toString();
                        Picasso.get().load(image).into(profileinmage_view);
                        setting_user_name.setText(name);
                        setting_address.setText(address);
                        setting_full_name.setText(fullName);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
