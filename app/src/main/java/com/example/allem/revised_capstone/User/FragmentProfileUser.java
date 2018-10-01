package com.example.allem.revised_capstone.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allem.revised_capstone.Login;
import com.example.allem.revised_capstone.R;

import static android.app.Activity.RESULT_OK;


public class FragmentProfileUser extends Fragment {
    View view;
    TextView tvFullName, tvEmail, tvContact, tvAddress;
    ImageView imgEdit;
    private final int REQUEST_CODE = 1;
    private static final String TAG = "FragmentProfileUser";

    @SuppressLint("SetTextI18n")
    private void MyViews(View view) {
        tvFullName = view.findViewById(R.id.profile_user_fullName);
        tvEmail = view.findViewById(R.id.profile_user_email);
        tvContact = view.findViewById(R.id.profile_user_contactNo);
        tvAddress = view.findViewById(R.id.profile_user_address);

        tvFullName.setText(Login.fName + " " + Login.mInitial + " " + Login.lName);
        tvEmail.setText(Login.email);
        tvContact.setText(Login.contact);
        tvAddress.setText(Login.address);

        imgEdit = view.findViewById(R.id.profile_user_image);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);

                imgEdit.setImageBitmap(bitmap);
            }
            catch(Exception e){
                Log.d(TAG, "onActivityResult: "+e.getMessage());
            }
        }
        else{

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        MyViews(view);
        return view;
    }


}
