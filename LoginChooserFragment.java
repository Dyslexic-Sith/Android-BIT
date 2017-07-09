package com.project.sam.bitservices;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Sam on 18/05/2017.
 */
public class LoginChooserFragment extends Fragment {

    Button btnClient;
    Button btnContractor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loginchooserlayout, container, false);
        btnClient = (Button)view.findViewById(R.id.btnClient);
        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_placeholder, new clientLoginFragment());
                ft.addToBackStack("clientHome");
                ft.commit();
            }
        });
        btnContractor = (Button) view.findViewById(R.id.btnContractor);
        btnContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_placeholder, new contractorLoginFragment());
                ft.addToBackStack("contractorHome");
                ft.commit();
            }
        });
        return view;


    }

}
