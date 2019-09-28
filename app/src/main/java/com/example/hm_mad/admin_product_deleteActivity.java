package com.example.hm_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hm_mad.firebaseDatabase.firebase;
import com.example.hm_mad.mod.products;

import java.util.List;

public class admin_product_deleteActivity extends AppCompatActivity {
    private EditText pname,pprice,pdetail;
    private Button pupdate,pdelet,pback;

    private String name,price,dic;
    private String keye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_delete);
        keye = getIntent().getStringExtra("keye");
        name = getIntent().getStringExtra("name");
        dic = getIntent().getStringExtra("dic");
        price = getIntent().getStringExtra("price");


        setContentView(R.layout.activity_admin_product_delete);
        pname = (EditText)findViewById(R.id.Update_pro_name);
        pname.setText(name);
        pprice = (EditText)findViewById(R.id.Update_pro_price);
        pprice.setText(price);
        pdetail = (EditText)findViewById(R.id.Update_pro_details);
        pdetail.setText(dic);

        pupdate = (Button)findViewById(R.id.Update_pro_update);
        pdelet = (Button)findViewById(R.id.Update_pro_delete);
        pback = (Button)findViewById(R.id.Update_pro_back);
        pupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products Products = new products();
                Products.setName(pname.getText().toString());
                Products.setPrice(pprice.getText().toString());
                Products.setDic(pdetail.getText().toString());

                new firebase().updateProduct(keye, Products, new firebase.DataStates() {
                    @Override
                    public void dataIsStoring(List<products> products, List<String> keye) {


                    }

                    @Override
                    public void dataUpdated() {
                        Toast.makeText(admin_product_deleteActivity.this, "Product data Updated", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void dataDeleted() {

                    }
                });
            }
        });
        pdelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new firebase().deleteProduct(keye, new firebase.DataStates() {
                    @Override
                    public void dataIsStoring(List<products> products, List<String> keye) {

                    }

                    @Override
                    public void dataUpdated() {

                    }

                    @Override
                    public void dataDeleted() {
                        Toast.makeText(admin_product_deleteActivity.this, "Product data has been Deleted", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                });
            }
        });
        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); return;
            }
        });


    }

}
