package com.example.allem.revised_capstone.User;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.R;


public class FragmentProfileUserEdit extends Fragment {
    View view;
    EditText etContact, etEmail, etAddress;
    TextView fullName;
    ImageView iv_prof, iv_clickCheck;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_user_edit, container, false);
        etContact = view.findViewById(R.id.profile_user_edit_contact);
        etEmail = view.findViewById(R.id.profile_user_edit_email);
        etAddress = view.findViewById(R.id.profile_user_edit_address);
        fullName = view.findViewById(R.id.profile_user_edit_fullName);

        iv_prof = view.findViewById(R.id.profile_user_edit_iv);
        iv_clickCheck = view.findViewById(R.id.profile_user_edit_check);

        fullName.setText(Login.fName + " " + Login.lName);
        etContact.setText(Login.contact);
        etAddress.setText(Login.address);
        etEmail.setText(Login.email);

        iv_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

}
