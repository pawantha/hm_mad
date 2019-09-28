package com.example.hm_mad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hm_mad.firebaseDatabase.Recovary_view;
import com.example.hm_mad.firebaseDatabase.firebase;
import com.example.hm_mad.mod.products;

import java.util.List;

public class adminViewActivity extends AppCompatActivity {
    private RecyclerView mresicalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        mresicalView = (RecyclerView)findViewById(R.id.re_view_ac);
        new firebase().readproduct(new firebase.DataStates() {
            @Override
            public void dataIsStoring(List<products> products, List<String> keye) {
                new Recovary_view().setConfig(mresicalView,adminViewActivity.this,products,keye);
            }

            @Override
            public void dataUpdated() {

            }

            @Override
            public void dataDeleted() {

            }
        });
    }
}
