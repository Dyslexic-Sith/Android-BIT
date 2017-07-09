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
public class contractorLoginFragment extends Fragment {
    DatabaseHandler db;
    @Override
    public View onCreateView(LayoutInflater inflate, ViewGroup container, Bundle savedInstanceState) {
        db = DatabaseHandler.getInstance(getContext());
        View view = inflate.inflate(R.layout.contractorloginlayout, container, false);
        final EditText contractorUser = (EditText) view.findViewById(R.id.etContractorUsername);
        final EditText contractorPass = (EditText) view.findViewById(R.id.etContractorPassword);

        Button btnLogin = (Button)view.findViewById(R.id.btnContractorLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = contractorUser.getText().toString();
                String pass = contractorPass.getText().toString();
                DatabaseUser login = db.selectLogin(user, pass);
                if (login.getRoleId() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", login.getId());
                    bundle.putInt("role", login.getRoleId());
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    contractorHomeFragment contractorHomeFragment = new contractorHomeFragment();
                    contractorHomeFragment.setArguments(bundle);
                    ft.replace(R.id.fragment_placeholder, contractorHomeFragment);
                    ft.addToBackStack("contractorHome");
                    ft.commit();
                }
                else if (login.getRoleId() == 2){
                    Toast.makeText(getContext(), "Our records state you are a client of BIT, not an employee. Please go to the client login.",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getContext(), "There was no record of the username and password. Please try again.",Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
