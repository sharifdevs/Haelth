package com.example.haelth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


/**
 * Add a button in future to Sync any Data from Online JSON to Local JSON (to update local json file)
 */
public class ProfileFragment extends Fragment {


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putString();
        super.onSaveInstanceState(outState);
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {

        // Set Data to ProfileFragment
        ImageView imageViewProfile = (ImageView) getView().findViewById(R.id.imageViewProfile);
        TextView name = (TextView) getView().findViewById(R.id.textViewName);
        TextView natID = (TextView) getView().findViewById(R.id.textViewID);
        TextView region = (TextView) getView().findViewById(R.id.textViewRegion);
        TextView birthDate = (TextView) getView().findViewById(R.id.textViewBirthDate);
        TextView cellPhone = (TextView) getView().findViewById(R.id.textViewCellPhone);
        TextView gender = (TextView) getView().findViewById(R.id.textViewGender);
        TextView address = (TextView) getView().findViewById(R.id.textViewAddress);
        TextView homePhone = (TextView) getView().findViewById(R.id.textViewHomePhone);
        TextView email = (TextView) getView().findViewById(R.id.textViewEmail);

        // Set Texts
        if(App.patient.getProfileImageUrl() != null)
            Picasso.get().load(App.googleSignInAccount.getPhotoUrl()).into(imageViewProfile);
        name.setText(App.patient.getfName() + " " + App.patient.getlName());
        natID.setText("National ID: " + App.patient.getNatID());
        region.setText(App.patient.getCountry() + "/" + App.patient.getCity());
        birthDate.setText(App.patient.getBirthDate());
        cellPhone.setText("Mobile: " + App.patient.getCellPhone());
        gender.setText("Gender: " + ((App.patient.getGender() == App.Gender.MALE)?"male":"female"));
        address.setText("Home Address: " + App.patient.getAddress());
        homePhone.setText("Home Phone: " + App.patient.getHomePhone());
        email.setText("Email: " + App.patient.getEmail());
    }
}
