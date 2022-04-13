package com.example.mobilki_2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilki_2.Catalog;
import com.example.mobilki_2.Filter;
import com.example.mobilki_2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.editTextSearchManufacturer);
        tv.setText(Catalog.getInstance().getFilter().getManufacturer());
        tv = view.findViewById(R.id.editTextSearchDescription);
        tv.setText(Catalog.getInstance().getFilter().getDescription());
        tv = view.findViewById(R.id.editTextNumberPriceLess);
        if (Catalog.getInstance().getFilter().getPriceLess() != Integer.MAX_VALUE){
            String numberStr = Catalog.getInstance().getFilter().getPriceLess() / 100 + "." + Catalog.getInstance().getFilter().getPriceLess() % 100;
            tv.setText(numberStr);
        } else {
            tv.setText("");
        }
        tv = view.findViewById(R.id.editTextNumberPriceMore);
        if (Catalog.getInstance().getFilter().getPriceMore() != 0) {
            String numberStr = Catalog.getInstance().getFilter().getPriceMore() / 100 + "." + Catalog.getInstance().getFilter().getPriceMore() % 100;
            tv.setText(numberStr);
        } else {
            tv.setText("");
        }
        Catalog.getInstance().setFilter(new Filter());

        view.findViewById(R.id.btnSearchFilterClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewBtn) {
                TextView tv = view.findViewById(R.id.editTextSearchManufacturer);
                tv.setText("");
                tv = view.findViewById(R.id.editTextSearchDescription);
                tv.setText("");
                tv = view.findViewById(R.id.editTextNumberPriceLess);
                tv.setText("");
                tv = view.findViewById(R.id.editTextNumberPriceMore);
                tv.setText("");
                Catalog.getInstance().setFilter(new Filter());
            }
        });

        view.findViewById(R.id.btnSearchFilterSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewBtn) {
                Filter filter = new Filter();
                try {
                    TextView tv = view.findViewById(R.id.editTextSearchManufacturer);
                    filter.setManufacturer(tv.getText().toString());
                    tv = view.findViewById(R.id.editTextSearchDescription);
                    filter.setDescription(tv.getText().toString());
                    tv = view.findViewById(R.id.editTextNumberPriceMore);
                    int priceMore = 0;
                    if (!tv.getText().toString().equals("")) {
                        priceMore = (int) (Double.parseDouble(tv.getText().toString()) * 100);
                    }
                    tv = view.findViewById(R.id.editTextNumberPriceLess);
                    int priceLess = Integer.MAX_VALUE;
                    if (!tv.getText().toString().equals("")) {
                        priceLess = (int) (Double.parseDouble(tv.getText().toString()) * 100);
                    }
                    if (priceMore > priceLess) {
                        tv = view.findViewById(R.id.editTextNumberPriceLess);
                        tv.setText("");
                        tv = view.findViewById(R.id.editTextNumberPriceMore);
                        tv.setText("");
                        priceMore = 0;
                        priceLess = Integer.MAX_VALUE;
                    }
                    filter.setPriceLess(priceLess);
                    filter.setPriceMore(priceMore);
                } catch (Exception ignore){

                }
                Catalog.getInstance().setFilter(filter);
            }
        });

    }
}