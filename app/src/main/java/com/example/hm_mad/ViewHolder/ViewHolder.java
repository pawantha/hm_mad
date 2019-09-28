package com.example.hm_mad.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hm_mad.Interface.ItemClicklitner;
import com.example.hm_mad.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textProductname,textProduct_dis,textProduct_price;
    public ImageView imageview;
    public ItemClicklitner Listner;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageview = (ImageView) itemView.findViewById(R.id.product_image);
        textProduct_dis = (TextView) itemView.findViewById(R.id.product_Details);
        textProductname = (TextView) itemView.findViewById(R.id.product_name);
        textProduct_price = (TextView) itemView.findViewById(R.id.product_price);
    }
    public void setItemClickListner(ItemClicklitner listner){
        this.Listner = listner;
    }

    @Override
    public void onClick(View view) {
        Listner.onClick(view,getAdapterPosition() ,false );
    }
}
