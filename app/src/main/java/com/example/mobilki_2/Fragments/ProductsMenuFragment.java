package com.example.mobilki_2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilki_2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsMenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View screenView;

    public ProductsMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsMenuFragment newInstance(String param1, String param2) {
        ProductsMenuFragment fragment = new ProductsMenuFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        screenView = inflater.inflate(R.layout.fragment_products_menu, container, false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ProductsFragment()).commit();

        BottomNavigationView bottomNav = screenView.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Inflate the layout for this fragment
        return screenView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.item_home:
                    selectedFragment = new ProductsFragment();
                    break;
                case R.id.item_favorites:
                    selectedFragment = new FavoriteFragment();
                    break;
                case R.id.item_search:
                    selectedFragment = new SearchFragment();
                    break;
            }
            if (selectedFragment != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_content, selectedFragment)
                        .commit();
            }
            return true;
        }
    };

}

// https://javadevblog.com/primer-raboty-s-bottomnavigationview-nizhnee-menyu-v-android.html