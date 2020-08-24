package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    ImageView imv;
    TextView tv1, tv2;
    TextInputLayout ly1, ly2, ly3, ly4;
    Button btnSignUp, btnAlready;
    TextInputEditText tieFullname, tieUsername, tiePass, tieConfirm;

    String url = HTTP.user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
        btnAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                Pair[] pairs = new Pair[9];
                pairs[0] = new Pair<View, String>(imv, "logo_image");
                pairs[1] = new Pair<View, String>(tv1, "logo_text");
                pairs[2] = new Pair<View, String>(tv2, "logo_desc");
                pairs[3] = new Pair<View, String>(ly1, "fullname_tran");
                pairs[4] = new Pair<View, String>(ly2, "username_tran");
                pairs[5] = new Pair<View, String>(ly3, "password_tran");
                pairs[6] = new Pair<View, String>(ly4, "confirm_tran");
                pairs[7] = new Pair<View, String>(btnSignUp, "button_tran");
                pairs[8] = new Pair<View, String>(btnAlready, "login_signup_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tieFullname.getText().length() == 0) {
                    Toast.makeText(Register.this, "Vui lòng điền Tên của bạn", Toast.LENGTH_SHORT).show();
                } else if (tieUsername.getText().length() == 0) {
                    Toast.makeText(Register.this, "Vui lòng điền Password của bạn", Toast.LENGTH_SHORT).show();
                } else if (tieConfirm.getText().length() == 0) {
                    Toast.makeText(Register.this, "Confirm phải trùng với Password của bạn", Toast.LENGTH_SHORT).show();
                } else if (tieConfirm.getText().toString().equals(tiePass.getText().toString())) {

                    StringRequest stringrequest = new StringRequest(
                            Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            xulyRegister(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Register.this, "Volley error", Toast.LENGTH_SHORT).show();
                            Log.d("loi", error.toString());

                        }
                    }
                    ) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<String, String>();
                            param.put("username", tieUsername.getText().toString());
                            param.put("password", tiePass.getText().toString());
                            param.put("fullname", tieFullname.getText().toString());
                            param.put("tag", "register");

                            return param;
                        }
                    };
                    Volley.newRequestQueue(Register.this).add(stringrequest);
                } else {
                    Toast.makeText(Register.this, "Vui lòng Confirm đúng Password của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void Init() {
        imv = (ImageView) findViewById(R.id.logo_image);
        tv1 = (TextView) findViewById(R.id.logo_name);
        tv2 = (TextView) findViewById(R.id.slogan);
        ly1 = (TextInputLayout) findViewById(R.id.us1);
        ly2 = (TextInputLayout) findViewById(R.id.us2);
        ly3 = (TextInputLayout) findViewById(R.id.pw1);
        ly4 = (TextInputLayout) findViewById(R.id.pw2);
        tieFullname = (TextInputEditText) findViewById(R.id.tieFullname);
        tieUsername = (TextInputEditText) findViewById(R.id.tieUsername);
        tiePass = (TextInputEditText) findViewById(R.id.tiePass);
        tieConfirm = (TextInputEditText) findViewById(R.id.tieConfirm);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnAlready = (Button) findViewById(R.id.btnAlready);
    }


    private void xulyRegister(String response) {

        String thanhcong = "";
        try {
            JSONObject jsonobject = new JSONObject(response);
            thanhcong = jsonobject.getString("thanhcong");

            //doc tat ca du lieu tu json bo vao ArrayList
            if (Integer.parseInt(thanhcong) == 1)//thanh cong
            {
                Toast.makeText(this, "Register Done", Toast.LENGTH_SHORT).show();
                String us = tieUsername.getText().toString();
                String pw = tiePass.getText().toString();
                Intent intent = new Intent(Register.this,Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("us",us);
                bundle.putString("pw",pw);
                intent.putExtra("tk",bundle);
                startActivity(intent);
            } else //that bai
            {
                Toast.makeText(this, "Register Fail", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}