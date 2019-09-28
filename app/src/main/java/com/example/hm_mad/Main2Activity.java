package com.example.hm_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    private ImageView pizza,normal_food,burger;
    private ImageView drinks,ice,cake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pizza = (ImageView)findViewById(R.id.pizza);
        normal_food = (ImageView)findViewById(R.id.normal_food);
        burger = (ImageView)findViewById(R.id.burger);
        drinks = (ImageView)findViewById(R.id.drinks);
        cake = (ImageView)findViewById(R.id.cake);
        ice = (ImageView)findViewById(R.id.ice);


        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,min3addActivity.class);
                intent.putExtra("category","pizza");
                startActivity(intent);
            }
        });
        normal_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,min3addActivity.class);
                intent.putExtra("category","normal_food");
                startActivity(intent);
            }
        });
        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,min3addActivity.class);
                intent.putExtra("category","burgers");
                startActivity(intent);
            }
        });
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,min3addActivity.class);
                intent.putExtra("category","drinks");
                startActivity(intent);
            }
        });
        ice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,min3addActivity.class);
                intent.putExtra("category","ice");
                startActivity(intent);
            }
        });
        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,min3addActivity.class);
                intent.putExtra("category","cake");
                startActivity(intent);
            }
        });
    }
}
