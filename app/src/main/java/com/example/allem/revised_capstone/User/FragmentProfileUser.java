package com.example.allem.revised_capstone.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.R;


public class FragmentProfileUser extends Fragment {
    View view;
    TextView tvFullName, tvEmail, tvContact, tvAddress;
    ImageView imgEdit;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        tvFullName = view.findViewById(R.id.profile_user_fullName);
        tvEmail = view.findViewById(R.id.profile_user_email);
        tvContact = view.findViewById(R.id.profile_user_contactNo);
        tvAddress = view.findViewById(R.id.profile_user_address);

        tvFullName.setText(Login.fName +  " " + Login.mInitial + " " + Login.lName);
        tvEmail.setText(Login.email);
        tvContact.setText(Login.contact);
        tvAddress.setText(Login.address);

        imgEdit = view.findViewById(R.id.profile_user_edit);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit Pop-up", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }


}
