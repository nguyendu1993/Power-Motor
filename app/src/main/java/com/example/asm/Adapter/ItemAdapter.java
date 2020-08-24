package com.example.asm.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.Dao.ProductDao;
import com.example.asm.Fragment.Motor_Fragment;
import com.example.asm.HTTP;
import com.example.asm.Model.Product;
import com.example.asm.R;
import com.example.asm.UpdateProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private Context mcontext;
    private List<Product> mdata;


    public ItemAdapter(Context mcontext, List<Product> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mcontext);
        view = mInflater.inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.txtNameItem.setText(mdata.get(position).product_name);
        holder.txtPriceItem.setText(mdata.get(position).product_price +" USD");
        holder.txtDesItem.setText(mdata.get(position).product_describer);
        Picasso.get().load("http://" + HTTP.ip + "/APIASM/upload/" + mdata.get(position).product_image).into(holder.imvItem);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence[] item = {"NO", "YES"};
                final AlertDialog.Builder dialog3 = new AlertDialog.Builder(mcontext);
                dialog3.setTitle("BẠN CÓ CHẮC MUỐN XÓA LOẠI SÁCH NÀY KHÔNG ?");
                dialog3.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Toast.makeText(mcontext, "ĐÃ HỦY", Toast.LENGTH_LONG).show();
                        }
                        if (which == 1) {
                            String id = mdata.get(position).product_id;
                            String name = mdata.get(position).product_name;
                            ProductDao dao = new ProductDao(mcontext);
                            dao.Delete(id);
                            Motor_Fragment.getInstance().LV();
                        }

                    }
                });
                dialog3.show();


            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, UpdateProduct.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",mdata.get(position).product_id);
                bundle.putString("name",mdata.get(position).product_name);
                bundle.putString("price",mdata.get(position).product_price);
                bundle.putString("img",mdata.get(position).product_image);
                bundle.putString("des",mdata.get(position).product_describer);

                intent.putExtra("product",bundle);
                mcontext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageButton btnEdit, btnDelete;
        ImageView imvItem;
        TextView txtNameItem, txtPriceItem, txtDesItem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imvItem = itemView.findViewById(R.id.imvMotor);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtNameItem = itemView.findViewById(R.id.tvNameItem);
            txtPriceItem = itemView.findViewById(R.id.tvPriceItem);
            txtDesItem = itemView.findViewById(R.id.tvDesItem);
            cardView = itemView.findViewById(R.id.item_card);
        }
    }
}
