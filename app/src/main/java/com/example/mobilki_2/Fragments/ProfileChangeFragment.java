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
 * Use the {@link ProfileChangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileChangeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileChangeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileChangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileChangeFragment newInstance(String param1, String param2) {
        ProfileChangeFragment fragment = new ProfileChangeFragment();
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
        return inflater.inflate(R.layout.fragment_profile_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btnCancelProfileChange).setOnClickListener(v -> {
            TextView textView = view.findViewById(R.id.textViewMessageChangeProfile);
            textView.setText("");
            Navigation.findNavController(view).navigate(R.id.action_profileChangeFragment_to_profileMenuFragment);
        });

        view.findViewById(R.id.btnChangeProfileChange).setOnClickListener(v -> {
            EditText editText = view.findViewById(R.id.editTextProfileChange);
            String profileName = editText.getText().toString();
            TextView textView = view.findViewById(R.id.textViewMessageChangeProfile);
            if (Profiles.getInstance().isProfileNamePresent(profileName)){
                Profiles.getInstance().changeProfile(profileName);
                textView.setText("");
                TextView tv = getActivity().findViewById(R.id.textProfileName);
                tv.setText(Profiles.getInstance().getCurrentProfile().getName());
                tv = getActivity().findViewById(R.id.textViewProfileName);
                tv.setText(Profiles.getInstance().getCurrentProfile().getName());
                //tv = getActivity().findViewById(R.id.textViewProfileScore);
                //tv.setText(String.valueOf(Profiles.getInstance().getCurrentProfile().score));
                Navigation.findNavController(view).navigate(R.id.action_profileChangeFragment_to_profileMenuFragment);
            } else {
                textView.setText("this profile name not exist");
            }
        });
    }
}