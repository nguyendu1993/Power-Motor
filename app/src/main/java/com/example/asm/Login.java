package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
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

public class Login extends AppCompatActivity {
    TextInputEditText edtusname, edtpass;
    Button btnLogin, btnRes, btnForget;
    ImageView imv;
    TextView tv, tv2;
    TextInputLayout ly1, ly2;
    CheckBox cbRemem;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String url = HTTP.user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        Init();

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        String usname = sharedPreferences.getString("usname", "");
        String pass = sharedPreferences.getString("pass", "");
        edtusname.setText(usname);
        edtpass.setText(pass);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("tk");
        if (bundle != null) {
            String us = bundle.getString("us");
            String pw = bundle.getString("pw");
            edtusname.setText(us);
            edtpass.setText(pw);
        }

        //kiem tra remember checked
        if (edtusname.getText().length() > 0) {
            cbRemem.setChecked(true);
        } else {
            cbRemem.setChecked(false);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtusname.getText().length() == 0) {
                    Toast.makeText(Login.this, "Vui lòng nhập Tài Khoản", Toast.LENGTH_SHORT).show();
                } else if (edtpass.getText().length() == 0) {
                    Toast.makeText(Login.this, "Vui lòng nhập Mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    if (cbRemem.isChecked()) {
                        editor.putString("usname", edtusname.getText().toString());
                        editor.putString("pass", edtpass.getText().toString());
                        editor.commit();
                    } else {
                        editor.putString("usname", "");
                        editor.putString("pass", "");
                        editor.commit();
                    }

                    StringRequest stringrequest = new StringRequest(
                            Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            xulyLogin(response);


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Login.this, "Volley error", Toast.LENGTH_SHORT).show();
                            Log.d("loi",error.toString());

                        }
                    }
                    ) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<String,String>();


                            param.put("username",edtusname.getText().toString());
                            param.put("password",edtpass.getText().toString());
                            param.put("tag","login");

                            return param;
                        }
                    };
                    Volley.newRequestQueue(Login.this).add(stringrequest);



                }
            }
        });

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Pair[] pairs = new Pair[9];
                pairs[0] = new Pair<View, String>(imv, "logo_image");
                pairs[1] = new Pair<View, String>(tv, "logo_text");
                pairs[2] = new Pair<View, String>(tv2, "logo_desc");
                pairs[3] = new Pair<View, String>(ly1, "username_tran");
                pairs[4] = new Pair<View, String>(ly2, "password_tran");
                pairs[5] = new Pair<View, String>(btnForget, "foget_tran");
                pairs[6] = new Pair<View, String>(btnLogin, "button_tran");
                pairs[7] = new Pair<View, String>(btnRes, "login_signup_tran");
                pairs[8] = new Pair<View, String>(cbRemem, "remem_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());

            }
        });


    }


    public void Init() {
        edtusname = (TextInputEditText) findViewById(R.id.edtusname);
        edtpass = (TextInputEditText) findViewById(R.id.edtpass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRes = (Button) findViewById(R.id.btnRes);
        btnForget = (Button) findViewById(R.id.btnForgot);
        imv = (ImageView) findViewById(R.id.logo_image);
        tv = (TextView) findViewById(R.id.logo_name);
        tv2 = (TextView) findViewById(R.id.slogan);
        ly1 = (TextInputLayout) findViewById(R.id.username);
        ly2 = (TextInputLayout) findViewById(R.id.password);
        cbRemem = (CheckBox) findViewById(R.id.cbRemem);
    }


    private void xulyLogin(String response) {
        String thanhcong = "";
        String name = "";
        try {
            JSONObject jsonobject = new JSONObject(response);
            thanhcong = jsonobject.getString("thanhcong");
            //doc tat ca du lieu tu json bo vao ArrayList
            if (Integer.parseInt(thanhcong) == 1)//thanh cong
            {
                JSONObject user = jsonobject.getJSONObject("user");
                name = user.getString("fullname");
                Toast.makeText(this, "Login Done", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("fullname", name);
                Log.d("fullname", name);
                startActivity(intent);
            } else //that bai
            {
                Log.d("login", "LoginFail");
                Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}