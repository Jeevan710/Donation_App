package com.example.donationapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DonateCategoryActivity extends AppCompatActivity {
    private ImageView Shirts_Tshirts,FemaleDresses,Male_pants;
    private ImageView Shoes,Notes,Others;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_category);

        Shirts_Tshirts = (ImageView) findViewById(R.id.male_shirt_tshirt);
        FemaleDresses = (ImageView) findViewById(R.id.female_dreseses);
        Male_pants = (ImageView) findViewById(R.id.male_pants);
        Shoes = (ImageView) findViewById(R.id.shoes);
        Notes = (ImageView) findViewById(R.id.notes_books);
        Others = (ImageView) findViewById(R.id.others);

        Shirts_Tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DonateCategoryActivity.this, DonateAddNewProductsActivity.class);
                intent.putExtra("category", "Shirt or T-Shirts");
                startActivity(intent);
            }
        });

        FemaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DonateCategoryActivity.this, DonateAddNewProductsActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });

        Male_pants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DonateCategoryActivity.this, DonateAddNewProductsActivity.class);
                intent.putExtra("category", "Male Pants");
                startActivity(intent);
            }
        });

        Shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DonateCategoryActivity.this, DonateAddNewProductsActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });

        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DonateCategoryActivity.this, DonateAddNewProductsActivity.class);
                intent.putExtra("category", "Notes or Books");
                startActivity(intent);
            }
        });

        Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DonateCategoryActivity.this, DonateAddNewProductsActivity.class);
                intent.putExtra("category", "Others");
                startActivity(intent);
            }
        });



    }
}