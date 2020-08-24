package com.example.asm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.Dao.CardDao;
import com.example.asm.HTTP;
import com.example.asm.Model.Card;
import com.example.asm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private Context mcontext;
    private List<Card> mData;

    public CardAdapter(Context mcontext, List<Card> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mcontext);
        view = mInflater.inflate(R.layout.item_cardproduct, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.tvCardname.setText(mData.get(position).product_name);
        holder.tvCardprice.setText(mData.get(position).product_price);
        holder.tvCardDes.setText(mData.get(position).product_describer);
        Picasso.get().load("http://" + HTTP.ip + "/APIASM/upload/" + mData.get(position).product_image).into(holder.imvCart);
        holder.btnCardDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mData.get(position).card_id;
//                Toast.makeText(mcontext, id, Toast.LENGTH_SHORT).show();
                CardDao dao = new CardDao(mcontext);
                dao.Delete(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imvCart;
        TextView tvCardname,tvCardprice,tvCardDes;
        ImageButton btnCardDel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.product_card);
            imvCart = itemView.findViewById(R.id.card_imv);
            tvCardname = itemView.findViewById(R.id.card_name);
            tvCardprice = itemView.findViewById(R.id.card_price);
            tvCardDes = itemView.findViewById(R.id.card_desciber);
            btnCardDel = itemView.findViewById(R.id.card_delete);
        }
    }
}
