package com.example.hm_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hm_mad.ViewHolder.CartViewHolder;
import com.example.hm_mad.mod.carts;
import com.example.hm_mad.priv.priv;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button Next_cart_btn;
    private TextView txtTotalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.Cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Next_cart_btn = (Button)findViewById(R.id.Cart_next_btn);
        txtTotalprice = (TextView) findViewById((R.id.Cart_total_price));
        Next_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,Main_hiruniActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cardLisrRef = FirebaseDatabase.getInstance().getReference().child("Cart list");
        FirebaseRecyclerOptions<carts> options =
                new FirebaseRecyclerOptions.Builder<carts>()
                        .setQuery(cardLisrRef.child("user view")
                                .child(priv.onlneuser.getUser()).child("Products"),carts.class).build();

        FirebaseRecyclerAdapter<carts, CartViewHolder> adapter = new FirebaseRecyclerAdapter<carts, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull carts carts)
            {
                cartViewHolder.txtProduct_quantity .setText("Quantity = " + carts.getQuantiyt());
                cartViewHolder.txtProduct_name .setText("Product name" + carts.getName());
                cartViewHolder.txtProduct_price .setText("Price = "+carts.getPrice() + " Rs");
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_lot,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
