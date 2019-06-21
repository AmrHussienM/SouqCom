package com.example.souqcom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class adminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts,sportsTshirts,dresses,sweather;
    private ImageView glasses,hatcaps,walletBagsPurses,shoes;
    private ImageView headPhones,laptobs,watches,mobilePhones;
    private Button adminLogout,checkOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        identifications();

        prodectClickListener();

        checkOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminNewOrderActivity.class);
                startActivity(intent);
            }
        });

        logout();
    }




    private void logout()
    {
        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void prodectClickListener()
    {
        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });

        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sportsTshirts");
                startActivity(intent);
            }
        });


        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","femaleDresses");
                startActivity(intent);
            }
        });


        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sweather");
                startActivity(intent);
            }
        });


        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });


        hatcaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","hatCaps");
                startActivity(intent);
            }
        });


        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","femaleDresses");
                startActivity(intent);
            }
        });


        walletBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","walletBagsPurses");
                startActivity(intent);
            }
        });


        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });


        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","headPhones");
                startActivity(intent);
            }
        });


        laptobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","laptobs");
                startActivity(intent);
            }
        });


        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });


        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","mobilePhones");
                startActivity(intent);
            }
        });

    }

    private void identifications()
    {
        tshirts=findViewById(R.id.tshirts);
        sportsTshirts=findViewById(R.id.sports_tshirts);
        dresses=findViewById(R.id.femaleDresses);
        sweather=findViewById(R.id.sweather);
        glasses=findViewById(R.id.glasses);
        hatcaps=findViewById(R.id.hats);
        walletBagsPurses=findViewById(R.id.bags);
        shoes=findViewById(R.id.shoes);
        headPhones=findViewById(R.id.headphones);
        laptobs=findViewById(R.id.laptob);
        watches=findViewById(R.id.watches);
        mobilePhones=findViewById(R.id.mobiles);
        adminLogout=findViewById(R.id.admin_logout_btn);
        checkOrdersBtn=findViewById(R.id.check_order_btn);
    }
}
