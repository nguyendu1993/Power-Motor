package com.example.asm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
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
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Add_Product extends AppCompatActivity {

    ImageView imv;
    TextInputEditText tieName, tiePrice, tieDes;
    Button btnBack, btnSave;
    ProductDao dao;
    Context context;
    ArrayList<Product> products;
    int RESULT_LOAD_IMAGE = 123;

    Bitmap selectedBitmap;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ADD PRODUCT");
        dao = new ProductDao(this);
        Init();
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonhinh();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_Product.this, "Canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tieName.getText().length() == 0) {
                    Toast.makeText(Add_Product.this, "Please enter product name", Toast.LENGTH_SHORT).show();
                } else if (tiePrice.getText().length() == 0) {
                    Toast.makeText(Add_Product.this, "Please enter product price", Toast.LENGTH_SHORT).show();
                } else {
                    Product sp = new Product();
                    String pdimg = getStringImage(selectedBitmap);
                    sp.product_name = tieName.getText().toString();
                    sp.product_price = tiePrice.getText().toString();
                    sp.product_image = pdimg;
                    sp.product_describer = tieDes.getText().toString();
                    dao.insert(sp);
//                    Motor_Fragment.getInstance().LV();
                    finish();
                }


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
            imv.setImageBitmap(selectedBitmap);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                //xử lý lấy ảnh chọn từ điện thoại:
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imv.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Init() {
        imv = (ImageView) findViewById(R.id.imvAdd);
        tieName = (TextInputEditText) findViewById(R.id.tieProductName);
        tiePrice = (TextInputEditText) findViewById(R.id.tieProductPrice);
        tieDes = (TextInputEditText) findViewById(R.id.tieProductDescriber);
        btnSave = (Button) findViewById(R.id.btnADD);
        btnBack = (Button) findViewById(R.id.btnBack);

    }
}