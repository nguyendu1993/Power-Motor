package com.example.asm.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm.Adapter.BillAdapter;
import com.example.asm.Dao.BillDao;
import com.example.asm.Model.Bill;
import com.example.asm.Model.Product;
import com.example.asm.R;
import com.example.asm.VolleyCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bill_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bill_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView listBill;
    BillDao dao;
    ArrayList<Bill> bills;
    BillAdapter adapter;
    VolleyCallback<List<Bill>> volleyCallback;

    public Bill_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bill_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Bill_Fragment newInstance(String param1, String param2) {
        Bill_Fragment fragment = new Bill_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new BillDao(getContext());
        listBill = view.findViewById(R.id.listBill);
        LB();
    }

    public void LB() {
        dao.GetAll(new VolleyCallback<List<Bill>>() {
            @Override
            public void onReponse(List<Bill> reponse) {
                bills = (ArrayList<Bill>) reponse;
                adapter = new BillAdapter(getContext(), bills);
                listBill.setLayoutManager(new GridLayoutManager(getContext(), 1));
                listBill.setAdapter(adapter);
            }

            @Override
            public void onError(String err) {
                bills = new ArrayList<>();
                adapter = new BillAdapter(getContext(), bills);
                listBill.setLayoutManager(new GridLayoutManager(getContext(), 1));
                listBill.setAdapter(adapter);
            }
        });
    }
}