package com.example.hm_mad.firebaseDatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hm_mad.Main2Activity;
import com.example.hm_mad.R;
import com.example.hm_mad.adminViewActivity;
import com.example.hm_mad.admin_product_deleteActivity;
import com.example.hm_mad.mod.products;

import java.util.List;

public class Recovary_view {
    private Context mcontext;
    private productAdepter productAd;
    private String po;
    public void setConfig(RecyclerView recyclerView,Context context,List<products> products,List<String> keys){
        mcontext = context;
        productAd = new productAdepter(products,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(productAd);
    }

    class productItems extends RecyclerView.ViewHolder{
        private TextView pnaem,proPrice,pdetails;
        private String keye;

        public productItems(ViewGroup parent){
            super(LayoutInflater.from(mcontext).inflate(R.layout.admin_view_products,parent,false));
            pnaem =(TextView) itemView.findViewById(R.id.admin_name_view);
            proPrice =(TextView) itemView.findViewById(R.id.admin_price_view);
            pdetails =(TextView) itemView.findViewById(R.id.admin_detail_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, admin_product_deleteActivity.class);
                    intent.putExtra(keye,keye);
                    intent.putExtra("name",pnaem.getText().toString());
                    intent.putExtra("dic",pdetails.getText().toString());
                    intent.putExtra("price",proPrice.getText().toString());

                    mcontext.startActivity(intent);
                }
            });
        }
        public void bind(products product,String keye){
            pnaem.setText(product.getName());
            pdetails.setText(product.getDic());
            proPrice.setText(product.getPrice());
            this.keye = keye;
        }

    }

    class productAdepter extends RecyclerView.Adapter<productItems>{
        private List<products> products_a_list;
        private List<String> keyes;

        public productAdepter(List<products> products_a_list, List<String> keyes) {
            this.products_a_list = products_a_list;
            this.keyes = keyes;
        }

        @NonNull
        @Override
        public productItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new  productItems(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull productItems holder, int position) {
            holder.bind(products_a_list.get(position),keyes.get(position));
        }

        @Override
        public int getItemCount() {
            return products_a_list.size();
        }
    }

}
