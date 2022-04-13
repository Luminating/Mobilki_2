package com.example.mobilki_2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilki_2.Profiles;
import com.example.mobilki_2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileAddFragment newInstance(String param1, String param2) {
        ProfileAddFragment fragment = new ProfileAddFragment();
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
        return inflater.inflate(R.layout.fragment_profile_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btnCancelProfileAdd).setOnClickListener(v -> {
            TextView textView = view.findViewById(R.id.textViewMessageAddProfile);
            textView.setText("");
            Navigation.findNavController(view).navigate(R.id.action_profileAddFragment_to_profileMenuFragment);
        });

        view.findViewById(R.id.btnAddProfileAdd).setOnClickListener(v -> {
            EditText editText = view.findViewById(R.id.editTextProfileAdd);
            String profileName = editText.getText().toString();
            TextView textView = view.findViewById(R.id.textViewMessageAddProfile);
            if (!Profiles.getInstance().isProfileNamePresent(profileName)){
                Profiles.getInstance().addProfile(profileName);
                textView.setText("");
                TextView tv = getActivity().findViewById(R.id.textProfileName);
                tv.setText(Profiles.getInstance().getCurrentProfile().getName());
                tv = getActivity().findViewById(R.id.textViewProfileName);
                tv.setText(Profiles.getInstance().getCurrentProfile().getName());
                //tv = getActivity().findViewById(R.id.textViewProfileScore);
                //tv.setText(String.valueOf(Profiles.getInstance().getCurrentProfile().score));
                Navigation.findNavController(view).navigate(R.id.action_profileAddFragment_to_profileMenuFragment);
            } else {
                textView.setText("this profile name already exists");
            }
        });
    }
}