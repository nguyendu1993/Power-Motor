package com.example.asm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.Model.Bill;
import com.example.asm.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {

    private Context mContext;
    private List<Bill> bills;

    public BillAdapter(Context mContext, List<Bill> bills) {
        this.mContext = mContext;
        this.bills = bills;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_bill, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvName.setText(bills.get(position).nameuser);
        holder.tvDate.setText(bills.get(position).createdate);
        holder.tvTotal.setText(bills.get(position).totalprice);
//        holder.tvName.setText(bills.get(position).nameuser);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.bill_nameser);
            tvDate = itemView.findViewById(R.id.bill_date);
            tvTotal = itemView.findViewById(R.id.bill_total);
        }
    }
}
