package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asm.Dao.ProductDao;
import com.example.asm.Fragment.Motor_Fragment;
import com.example.asm.Model.Product;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateProduct extends AppCompatActivity {

    ImageView imvUD;
    TextInputEditText tieUDName, tieUDPrice, tieUDDes;
    Button btnUDBack, btnUD;
    ProductDao dao;
    Context context;
    ArrayList<Product> products;
    int RESULT_LOAD_IMAGE = 123;

    Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        Init();
        dao = new ProductDao(this);
        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("product");
        final String id = bundle.getString("id");
        final String name = bundle.getString("name");
        final String price = bundle.getString("price");
        final String img = bundle.getString("img");
        final String des = bundle.getString("des");

        tieUDName.setText(name);
        tieUDPrice.setText(price);
        tieUDDes.setText(des);
        Picasso.get().load("http://" + HTTP.ip + "/APIASM/upload/" + img).into(imvUD);

        imvUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonhinh();
            }
        });

        btnUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tieUDName.getText().length() == 0) {
                    Toast.makeText(UpdateProduct.this, "Please enter Product name", Toast.LENGTH_SHORT).show();
                } else if (tieUDPrice.getText().length() == 0) {
                    Toast.makeText(UpdateProduct.this, "Please enter Product price", Toast.LENGTH_SHORT).show();
                } else {
                    Product sp = new Product();
                    String pdimg = getStringImage(selectedBitmap);
                    sp.product_id=id;
                    sp.product_name = tieUDName.getText().toString();
                    sp.product_price = tieUDPrice.getText().toString();
                    sp.product_image = pdimg;
                    sp.product_describer = tieUDDes.getText().toString();
//                    Motor_Fragment.getInstance().LV();
                    dao.Update(sp);
                    finish();
                }
            }
        });

        btnUDBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void chonhinh() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 200);
    }

    public String getStringImage(Bitmap bm) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);

        return encode;

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //xử lý lấy ảnh trực tiếp lúc chụp hình:
            selectedBitmap = (Bitmap) data.getExtras().get("data");
            imvUD.setImageBitmap(selectedBitmap);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                //xử lý lấy ảnh chọn từ điện thoại:
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imvUD.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Init() {
        imvUD = (ImageView) findViewById(R.id.imvUpdate);
        tieUDName = (TextInputEditText) findViewById(R.id.tieUDProductName);
        tieUDPrice = (TextInputEditText) findViewById(R.id.tieUDProductPrice);
        tieUDDes = (TextInputEditText) findViewById(R.id.tieUDProductDescriber);
        btnUD = (Button) findViewById(R.id.btnUD);
        btnUDBack = (Button) findViewById(R.id.btnUDBack);

    }
}