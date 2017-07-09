package com.project.sam.bitservices;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sam on 18/05/2017.
 */
public class clientHomeFragment extends Fragment {
    int id;
    int role;
    Bundle bundle;
    TextView tv;
    static final int MY_REQUEST_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_REQUEST_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(takePictureIntent);
                }
                else {
                    // Your app will not have this permission. Turn off all functions
                    // that require this permission or it will force close like your
                    // original question
                    Toast.makeText(getContext(), "You cannot use the camera. Taking you back", Toast.LENGTH_LONG).show();
                    bundle.putInt("id", id);
                    bundle.putInt("role", role);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    clientHomeFragment clientHomeFragment = new clientHomeFragment();
                    clientHomeFragment.setArguments(bundle);
                    ft.replace(R.id.fragment_placeholder, clientHomeFragment);
                    ft.addToBackStack("clientHome");
                    ft.commit();
                }
            }
        }
        }

    public View onCreateView(LayoutInflater inflate, ViewGroup container, Bundle savedInstanceState) {

        // Get the information from the login bundle.
        bundle = getArguments();
        id = bundle.getInt("id");
        role = bundle.getInt("role");

        // Inflate the layout for this fragment
        View view = inflate.inflate(R.layout.clienthomelayout, container, false);

//          This was done to test the database, make sure it was entering and retrieving data as it should
//        tv = (TextView) view.findViewById(R.id.textView5);
//        tv.setText(Integer.toString(id));

        //region Image Clicking
        ImageView callImg = (ImageView)view.findViewById(R.id.imgCall);
        callImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Set the string to be the phone number for BIT Services
                    String uri = "tel:0422632769";
                    // Start the phone call intent.
                // I tried here to do an ACTION_DIAL but I couldn't work out the permissions. There are runtime permissions so I tried checking for it but couldn't get it working.
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);

            }
        });

        ImageView emailImg = (ImageView) view.findViewById(R.id.imgEmail);
        emailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get today's date for the email subject line
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String Date = df.format(c.getTime());
                // Start the email intent, set the data to be whatever
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"coianizs@outlook.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email Inquiry" +  " " + Date);

                try {
                    startActivity(intent);

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_LONG).show();
                }
            }
        });


        ImageView webImg = (ImageView) view.findViewById(R.id.imgWebsite);
        webImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Super easy, just open a website. This is currently set to google, but it can be whatever. If I had a URL for BIT then it would go here obviously.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com.au"));
                startActivity(intent);
            }
        });

        ImageView camImg = (ImageView)view.findViewById(R.id.imgCam1);
        camImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getContext().checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_REQUEST_CODE);
               }
                else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(takePictureIntent);}
            }
        });
        //endregion


        Button btnJobRequest = (Button) view.findViewById(R.id.btnClientJobRequest);
        btnJobRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                jobRequestFragment newJob = new jobRequestFragment();
                bundle.putInt("id", id);
                bundle.putInt("role", role);
                newJob.setArguments(bundle);
                ft.replace(R.id.fragment_placeholder, newJob);
                ft.addToBackStack("newJob");
                ft.commit();
            }
        });

        Button btnClientViewJobs = (Button)view.findViewById(R.id.btnClientViewJobs);
        btnClientViewJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                clientViewJobsFragment viewJob = new clientViewJobsFragment();
                bundle.putInt("id", id);
                bundle.putInt("role", role);
                viewJob.setArguments(bundle);
                ft.replace(R.id.fragment_placeholder, viewJob);
                ft.addToBackStack("viewJob");
                ft.commit();
            }
        });


        return view;
    }
}
