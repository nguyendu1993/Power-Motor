package com.example.asm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.Adapter.CardAdapter;
import com.example.asm.Adapter.ProductAdapter;
import com.example.asm.Dao.BillDao;
import com.example.asm.Dao.CardDao;
import com.example.asm.Model.Card;
import com.example.asm.R;
import com.example.asm.VolleyCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;
    RecyclerView recyCard;
    CardDao dao;
    ArrayList<Card> cards;
    CardAdapter adapter;
    TextView tvtotal;
    Button btnBuy;
    CardDao cardDao;
    Card card;
    int total;

    public static Cart_Fragment instance = null;

    public Cart_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cart_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart_Fragment newInstance(String param1, String param2) {
        Cart_Fragment fragment = new Cart_Fragment();
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
        return inflater.inflate(R.layout.fragment_cart_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvtotal = view.findViewById(R.id.tvTotal);
        btnBuy = view.findViewById(R.id.btnBuy);
        recyCard = view.findViewById(R.id.card);
        dao = new CardDao(getContext());
        cardDao = new CardDao(getContext());
        instance = this;
        LVCard();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String listproduct = "";
                for (int i = 0; i < cards.size(); i++) {
                    card = cards.get(i);
                    String id = card.product_id;
                    String name = card.product_name;
                    String price = card.product_price;
                    String image = card.product_image;
                    String des = card.product_describer;
                    listproduct = listproduct + "-" + id + "-" + name + "-" + price + "-" + image + "-" + des;
                }

                Intent intent = getActivity().getIntent();
                String totalprice = tvtotal.getText().toString();
                String usname = intent.getStringExtra("fullname");
//                Toast.makeText(getContext(), usname, Toast.LENGTH_SHORT).show();
                BillDao billDao = new BillDao(getContext());
                billDao.insert(listproduct,usname,totalprice);
                Toast.makeText(getContext(), "DA THANH TOAN", Toast.LENGTH_SHORT).show();
                cardDao.DeleteAll();
                total=0;
                tvtotal.setText(""+total+"USD");

            }
        });


    }

    public static Cart_Fragment getInstance() {
        return instance;
    }


    public void LVCard() {

        dao.GetAll(new VolleyCallback<List<Card>>() {
            @Override
            public void onReponse(List<Card> reponse) {
                cards = (ArrayList<Card>) reponse;
                adapter = new CardAdapter(getContext(), cards);
                recyCard.setLayoutManager(new GridLayoutManager(getContext(), 1));
                recyCard.setAdapter(adapter);

                for (Card c : cards) {
                    total += Integer.parseInt(c.product_price);
                    tvtotal.setText("" + total + " USD");
                }

//                Log.d("gia", String.valueOf(total));


            }

            @Override
            public void onError(String err) {
                cards = new ArrayList<>();
                adapter = new CardAdapter(getContext(), cards);
                recyCard.setLayoutManager(new GridLayoutManager(getContext(), 1));
                recyCard.setAdapter(adapter);
                for (Card c : cards) {
                    total += Integer.parseInt(c.product_price);
                }
                tvtotal.setText("" + total + " USD");
            }
        });

    }
}