package com.example.hm_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hm_mad.mod.products;
import com.example.hm_mad.priv.priv;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class produtDetailsActivity extends AppCompatActivity {
    private Button addtoCart;
    private ImageView prodct_image;
    private ElegantNumberButton numberButton;
    private TextView produt_price,product_discription,product_name;
    private String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produt_details);

        productId = getIntent().getStringExtra("pid");
        addtoCart = (Button)findViewById(R.id.product_addTo_cart);
        numberButton =(ElegantNumberButton) findViewById(R.id.btn_detail_count);
        prodct_image =(ImageView)findViewById(R.id.Produt_imag_dtais);

        produt_price =(TextView)findViewById(R.id.product_detail_price);
        product_discription =(TextView)findViewById(R.id.produt_detais_discrpction);
        product_name =(TextView)findViewById(R.id.produt_detais_name);

        getProductDetails(productId);
        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTocatList();
            }
        });
    }

    private void addTocatList()
    {
        String saveCurrentTime,saveCurrentDate;
        Calendar calFromdate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM/DD/YYYY");
        saveCurrentDate = currentDate.format(calFromdate.getTime());
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:MM:SS a");
        saveCurrentTime = currenttime.format(calFromdate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("name",product_name.getText().toString());
        cartMap.put("price",produt_price.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("Quantiyt",numberButton.getNumber());

        cartListRef.child("user view").child(priv.onlneuser.getUser()).child("Products")
                .child(productId).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin view").child(priv.onlneuser.getUser()).child("Products")
                                    .child(productId).updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(produtDetailsActivity.this, "Added to cart list..", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(produtDetailsActivity.this,Home.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });

                        }
                    }
                });

    }

    private void getProductDetails(String productId) {
        DatabaseReference prouctRef = FirebaseDatabase.getInstance().getReference().child("Products");
        prouctRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    products products = dataSnapshot.getValue(products.class);
                    product_name.setText(products.getName());
                    product_discription.setText(products.getDic());
                    produt_price.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(prodct_image);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
