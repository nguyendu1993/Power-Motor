package com.example.asm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.Dao.CardDao;
import com.example.asm.Model.Card;
import com.example.asm.Model.Product;
import com.squareup.picasso.Picasso;

public class ShowProduct extends AppCompatActivity {

    TextView tvName, tvPrice, tvDes;
    ImageView imageView;
    Button btnAddtoCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("DETAILS");
        Init();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("product");
        final String id = bundle.getString("id");
        final String name = bundle.getString("name");
        final String price = bundle.getString("price");
        final String image = bundle.getString("image");
        final String des = bundle.getString("des");

        tvName.setText(name);
        tvPrice.setText(price + " USD");
        Picasso.get().load("http://" + HTTP.ip + "/APIASM/upload/" + image).into(imageView);
        tvDes.setText(des);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardDao dao = new CardDao(ShowProduct.this);
                Card p = new Card();
                p.product_id = id;
                p.product_name = name;
                p.product_price = price;
                p.product_image = image;
                p.product_describer = des;
                dao.insert(p);
                Toast.makeText(ShowProduct.this, "Them thanh cong vao gio hang", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public void Init() {
        tvName = findViewById(R.id.tvShowName);
        tvDes = findViewById(R.id.tvShowDes);
        tvPrice = findViewById(R.id.tvShowPrice);
        imageView = findViewById(R.id.imvShow);
        btnAddtoCart = findViewById(R.id.btnAddCart);
        btnBack = findViewById(R.id.btnBackEnd);
    }
}