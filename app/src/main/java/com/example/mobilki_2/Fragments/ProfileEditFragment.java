package com.example.mobilki_2.Fragments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilki_2.Profiles;
import com.example.mobilki_2.ProxyBitmap;
import com.example.mobilki_2.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static int RESULT_LOAD_IMAGE = 1;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileEditFragment newInstance(String param1, String param2) {
        ProfileEditFragment fragment = new ProfileEditFragment();
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
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText editText = view.findViewById(R.id.editTextProfileName);
        editText.setText(Profiles.getInstance().getCurrentProfile().getName());
        editText = view.findViewById(R.id.editTextProfileEmail);
        editText.setText(Profiles.getInstance().getCurrentProfile().getEmail());

        view.findViewById(R.id.btnCancelProfileEdit).setOnClickListener(v -> {
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageViewPhoto);
            imageView.setImageBitmap(Profiles.getInstance().getCurrentProfile().getPhoto().getBitmap());
            Navigation.findNavController(view).navigate(R.id.action_profileEditFragment_to_profileMenuFragment);
        });

        view.findViewById(R.id.btnEditProfileEdit).setOnClickListener(v -> {
            EditText editText1 = view.findViewById(R.id.editTextProfileName);
            String profileName = editText1.getText().toString();
            editText1 = view.findViewById(R.id.editTextProfileEmail);
            String profileEmail = editText1.getText().toString();
            TextView textViewMessage = view.findViewById(R.id.textViewMessageEditProfile);
            if (!Profiles.getInstance().isProfileNamePresentAndNotCurrent(profileName)){
                Profiles.getInstance().getCurrentProfile().setName(profileName);
                Profiles.getInstance().getCurrentProfile().setEmail(profileEmail);
                textViewMessage.setText("");
                TextView tv = getActivity().findViewById(R.id.textProfileName);
                tv.setText(Profiles.getInstance().getCurrentProfile().getName());
                tv = getActivity().findViewById(R.id.textViewProfileName);
                tv.setText(Profiles.getInstance().getCurrentProfile().getName());
                ImageView imv = getActivity().findViewById(R.id.imageViewPhoto);
                BitmapDrawable drawable = (BitmapDrawable) imv.getDrawable();
                Profiles.getInstance().getCurrentProfile().setPhoto(new ProxyBitmap(drawable.getBitmap()));
                Navigation.findNavController(view).navigate(R.id.action_profileEditFragment_to_profileMenuFragment);
            } else {
                textViewMessage.setText("this profile name already exists");
            }
        });

        view.findViewById(R.id.btnChangePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageViewPhoto);
            imageView.setImageURI(selectedImage);
        }
    }
}

