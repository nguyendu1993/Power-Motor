package com.example.asm.Dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asm.HTTP;
import com.example.asm.Model.Bill;
import com.example.asm.Model.Card;
import com.example.asm.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDao {
    Context context;
    String success = "";
    String url = HTTP.bill;
    String getbill = HTTP.getallbill;


    List<Bill> list = new ArrayList<>();

    public BillDao(Context context) {
        this.context = context;
    }

    public void insert(final String listproduct, final String nameuser,final String totalprice) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getString("thanhcong");

                    if (Integer.parseInt(success) == 1) {
                        Toast.makeText(context, "Add Success", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(context, "Add Fail", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("listproduct", listproduct);
                param.put("nameuser", nameuser);
                param.put("totalprice",totalprice);
                param.put("tag", "addbill");
                return param;
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void GetAll(final VolleyCallback<List<Bill>> volleyCallback) {

        StringRequest stringrequest = new StringRequest(getbill
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonobject = new JSONObject(response);
                    String thanhcong = jsonobject.getString("thanhcong");

                    //doc tat ca du lieu tu json bo vao ArrayList
                    if (Integer.parseInt(thanhcong) == 1)//thanh cong
                    {
//                        Toast.makeText(context, "getAll Done", Toast.LENGTH_SHORT).show();

                        JSONArray sps = jsonobject.getJSONArray("bill");

                        for (int i = 0; i < sps.length(); i++) {
                            JSONObject spsJSONObject = sps.getJSONObject(i);
                            Bill b = new Bill();
                            b.bill_id = spsJSONObject.getInt("bill_id");
                            b.nameuser = spsJSONObject.getString("nameuser");
                            b.createdate = spsJSONObject.getString("createdate");
                            b.totalprice = spsJSONObject.getString("totalprice");


//                            String listproduct = spsJSONObject.getString("listproduct");
//                            String[] list_item = listproduct.split("-");

                            list.add(b);
                        }
                        volleyCallback.onReponse(list);
                    } else //that bai
                    {
//                        Toast.makeText(context, "Khong co product", Toast.LENGTH_SHORT).show();
                        volleyCallback.onError("Fail");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    volleyCallback.onError("Fail");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi", error.toString());
                volleyCallback.onError("Fail");

            }
        }
        );
        Volley.newRequestQueue(context).add(stringrequest);

    }


}
