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
import com.example.asm.Fragment.Cart_Fragment;
import com.example.asm.Fragment.Motor_Fragment;
import com.example.asm.HTTP;
import com.example.asm.Model.Card;
import com.example.asm.Model.Product;
import com.example.asm.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardDao {
    Context context;
    String url = HTTP.card;
    String getcard = HTTP.getcard;
    List<Card> list = new ArrayList<>();
    String getprice = HTTP.gettotalprice;
    String total;
    List<Card> price = new ArrayList<>();

    public CardDao(Context context) {
        this.context = context;
    }

    public void insert(final Card sp) {

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
                param.put("product_id", sp.product_id);
                param.put("product_name", sp.product_name);
                param.put("product_price", sp.product_price);
                param.put("product_image", sp.product_image);
                param.put("product_describer", sp.product_describer);
                param.put("tag", "additem");

                return param;
            }
        };
        Volley.newRequestQueue(context).add(stringrequest);

    }


    public void GetAll(final VolleyCallback<List<Card>> volleyCallback) {
        list.clear();
        StringRequest stringrequest = new StringRequest(getcard
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

                        JSONArray sps = jsonobject.getJSONArray("card");

                        for (int i = 0; i < sps.length(); i++) {
                            JSONObject spsJSONObject = sps.getJSONObject(i);

                            Card sp = new Card();
                            sp.card_id = spsJSONObject.getString("card_id");
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
//                        Motor_Fragment.getInstance().LV();
                        Cart_Fragment.getInstance().LVCard();
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

                param.put("card_id", id);
                param.put("tag", "deleteitem");


                return param;
            }
        };
        Volley.newRequestQueue(context).add(stringrequest);
    }

    public void DeleteAll() {
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
//                        Motor_Fragment.getInstance().LV();
                        Cart_Fragment.getInstance().LVCard();
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

                param.put("tag", "deleteall");
                return param;
            }
        };
        Volley.newRequestQueue(context).add(stringrequest);
    }

}
