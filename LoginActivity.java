package com.project.sam.bitservices;


import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;

public class LoginActivity extends Activity  {

    Bundle bundle;
    DatabaseHandler db;
    String jobAddress, jobSuburb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = DatabaseHandler.getInstance(this);
        // Set the framelayout to the loginchooserfragment
        // Then I need to set an onclick listener to the choosefragment and ft.replace instead of add.
        FragmentManager fr = getFragmentManager();
        FragmentTransaction ft = fr.beginTransaction();
        ft.replace(R.id.fragment_placeholder, new LoginChooserFragment());
        ft.addToBackStack("first");
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {

            getFragmentManager().popBackStackImmediate();
            return;
        }
        super.onBackPressed();
    }

}
