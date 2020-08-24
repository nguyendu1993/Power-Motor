package com.example.asm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.HTTP;
import com.example.asm.Model.Product;
import com.example.asm.R;
import com.example.asm.ShowProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mcontext;
    private List<Product> mData;

    public ProductAdapter(Context mcontext, List<Product> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mcontext);
        view = mInflater.inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtName.setText(mData.get(position).product_name);
        holder.txtPrice.setText(mData.get(position).product_price + " USD");
        Picasso.get().load("http://" + HTTP.ip + "/APIASM/upload/" + mData.get(position).product_image).into(holder.img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ShowProduct.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",mData.get(position).product_id);
                bundle.putString("name",mData.get(position).product_name);
                bundle.putString("price",mData.get(position).product_price);
                bundle.putString("image",mData.get(position).product_image);
                bundle.putString("des",mData.get(position).product_describer);
                intent.putExtra("product",bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView img;
        TextView txtName, txtPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_product);
            img = itemView.findViewById(R.id.imvProduct);
            txtName = itemView.findViewById(R.id.tvPDNAme);
            txtPrice = itemView.findViewById(R.id.tvPDPrice);
        }
    }
}


