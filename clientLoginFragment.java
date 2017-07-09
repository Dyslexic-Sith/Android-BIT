package com.project.sam.bitservices;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sam on 18/05/2017.
 */
public class clientLoginFragment extends Fragment {
DatabaseHandler db;
    @Override
    public View onCreateView(LayoutInflater inflate, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = DatabaseHandler.getInstance(getContext());

        View view = inflate.inflate(R.layout.clientloginlayout, container, false);
        Button btnSubmit = (Button)view.findViewById(R.id.btnClientSignIn);
        final EditText clientUsername = (EditText) view.findViewById(R.id.etClientUsername);
        final EditText clientPassword = (EditText) view.findViewById(R.id.etClientPassword);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = clientUsername.getText().toString();
                String pass = clientPassword.getText().toString();
                DatabaseUser login = db.selectLogin(user, pass);
                if (login.getRoleId() == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", login.getId());
                    bundle.putInt("role", login.getRoleId());
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    clientHomeFragment clientHomeFragment = new clientHomeFragment();
                    clientHomeFragment.setArguments(bundle);
                    ft.replace(R.id.fragment_placeholder, clientHomeFragment);
                    ft.addToBackStack("clientHome");
                    ft.commit();
                }
                else if (login.getRoleId() == 1){
                    Toast.makeText(getContext(), "Our records state you are an employee of BIT, not a client. Please go to the contractor login.",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getContext(), "There was no record of the username and password. Please try again.",Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }
}
