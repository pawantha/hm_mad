package com.example.hm_mad.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hm_mad.Interface.ItemClicklitner;
import com.example.hm_mad.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProduct_name,txtProduct_price,txtProduct_quantity;
    private ItemClicklitner itemClicklitner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProduct_name = itemView.findViewById(R.id.Cart_product_name);
        txtProduct_price = itemView.findViewById(R.id.Card_product_price);
        txtProduct_quantity = itemView.findViewById(R.id.Card_product_quantity);
    }

    @Override
    public void onClick(View view) {
        itemClicklitner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClicklitner(ItemClicklitner itemClicklitner) {
        this.itemClicklitner = itemClicklitner;
    }
}
