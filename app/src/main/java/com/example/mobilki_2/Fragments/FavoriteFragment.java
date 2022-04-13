package com.example.mobilki_2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilki_2.Catalog;
import com.example.mobilki_2.ProductFavoriteAdapter;
import com.example.mobilki_2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View screenView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        screenView = inflater.inflate(R.layout.fragment_favorite, container, false);
        showCatalog();
        // Inflate the layout for this fragment
        screenView.findViewById(R.id.btnShowWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewBtn) {
                Fragment selectedFragment = new ExploreFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_content, selectedFragment)
                            .commit();
            }
        });


        return screenView;
    }

    private void showCatalog(){
        RecyclerView recyclerView = screenView.findViewById(R.id.listFavorites);
        recyclerView.addItemDecoration(new DividerItemDecoration(screenView.getContext(), LinearLayoutManager.VERTICAL));
        ProductFavoriteAdapter adapter = new ProductFavoriteAdapter(screenView.getContext(), Catalog.getInstance().getCatalogByFavorites());
        recyclerView.setAdapter(adapter);
    }
}