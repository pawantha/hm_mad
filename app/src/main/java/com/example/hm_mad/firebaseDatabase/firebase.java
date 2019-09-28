package com.example.hm_mad.firebaseDatabase;

import com.example.hm_mad.mod.products;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class firebase {
    private FirebaseDatabase database;
    private DatabaseReference newReference;
    private List<products> Products = new ArrayList<>();
    public interface DataStates{
        void dataIsStoring(List<products> products,List<String> keye);
        void dataUpdated();
        void dataDeleted();
    }

    public firebase() {
        database = FirebaseDatabase.getInstance();
        newReference = database.getReference("Products");
    }
    public void readproduct(final DataStates dataStates){
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Products.clear();
                List<String> keye = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                   keye.add(keyNode.getKey());
                   products product = keyNode.getValue(products.class);
                   Products.add(product);
                }
                dataStates.dataIsStoring(Products,keye );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void updateProduct(String keye ,products product, final DataStates dataStates){
        newReference.child(keye).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStates.dataUpdated();
            }
        });

    }
    public void deleteProduct(String keye, final DataStates dataStates){
        newReference.child(keye).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStates.dataDeleted();
            }
        });
    }
}
