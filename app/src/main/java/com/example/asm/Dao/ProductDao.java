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
import com.example.asm.Fragment.Motor_Fragment;
import com.example.asm.HTTP;
import com.example.asm.Model.Product;
import com.example.asm.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao {
    Context context;
    String thanhcong = "";
    String url = HTTP.product;
    String getall = HTTP.getall;

    List<Product> list = new ArrayList<>();

    public ProductDao(Context context) {
        this.context = context;
    }


    public void insert(final Product sp) {

        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String thanhcong = "0";
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    thanhcong = jsonobject.getString("thanhcong");
                    Log.d("test", thanhcong);
                    //doc tat ca du lieu tu json bo vao ArrayList
                    if (Integer.parseInt(thanhcong) == 1)//thanh cong
                    {
                        Toast.makeText(context, "Add Done", Toast.LENGTH_SHORT).show();
                        Motor_Fragment.getInstance().LV();
                    } else //that bai
                    {
                        Toast.makeText(context, "Add Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi", error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("product_name", sp.product_name);
                param.put("product_price", sp.product_price);
                param.put("product_image", sp.product_image);
                param.put("product_describer", sp.product_describer);
                param.put("tag", "addproduct");

                return param;
            }
        };
        Volley.newRequestQueue(context).add(stringrequest);

    }

    public void Delete(final String id) {
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonobject = new JSONObject(response);
                    String thanhcong = jsonobject.getString("thanhcong");

                    //doc tat ca du lieu tu json bo vao ArrayList
                    if (Integer.parseInt(thanhcong) == 1)//thanh cong
                    {
                        Toast.makeText(context, "Delete Done", Toast.LENGTH_SHORT).show();
//                        ((MainActivity)context).capnhatLV();
                        Motor_Fragment.getInstance().LV();

                    } else //that bai
                    {
                        Toast.makeText(context, "Delete Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi", error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("product_id", id);
                param.put("tag", "deleteproduct");


                return param;
            }
        };
        Volley.newRequestQueue(context).add(stringrequest);
    }

    public void Update(final Product sp) {
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonobject = new JSONObject(response);
                    String thanhcong = jsonobject.getString("thanhcong");

                    //doc tat ca du lieu tu json bo vao ArrayList
                    if (Integer.parseInt(thanhcong) == 1)//thanh cong
                    {
                        Toast.makeText(context, "Update Done", Toast.LENGTH_SHORT).show();

                        Motor_Fragment.getInstance().LV();

                    } else //that bai
                    {
                        Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi", error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("product_id", sp.product_id);
                param.put("product_name", sp.product_name);
                param.put("product_price", sp.product_price);
                param.put("product_image", sp.product_image);
                param.put("product_describer", sp.product_describer);
                param.put("tag","updateproduct");
                return param;
            }
        };
        Volley.newRequestQueue(context).add(stringrequest);
    }


    public void GetAll(final VolleyCallback<List<Product>> volleyCallback) {
        list.clear();
        StringRequest stringrequest = new StringRequest(getall
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

                        JSONArray sps = jsonobject.getJSONArray("sanpham");

                        for (int i = 0; i < sps.length(); i++) {
                            JSONObject spsJSONObject = sps.getJSONObject(i);

                            Product sp = new Product();
                            sp.product_id = spsJSONObject.getString("product_id");
                            sp.product_name = spsJSONObject.getString("product_name");
                            sp.product_price = spsJSONObject.getString("product_price");
                            sp.product_image = spsJSONObject.getString("product_image");
                            sp.product_describer = spsJSONObject.getString("product_describer");


                            list.add(sp);


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
