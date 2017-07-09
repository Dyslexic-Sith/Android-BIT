package com.project.sam.bitservices;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.maps.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 18/05/2017.
 */
public class contractorHomeFragment extends Fragment {
    int id;
    int role;
    int jobID;
    int lvSelectedID;
    String clientPhone;
    String txtContent;
    String jobAddress,jobSuburb;
    Bundle bundle;

    DatabaseUser jobLocationUser;
    DatabaseHandler db;
    ListView lvJobs;
    Button sortJobs,btnSMSJobs,btnMarkJobs;
    Spinner spnMarkJobs,spnSMSJobs;
    ArrayList<String> jobList = new ArrayList<>();
    List<String> truncatedJobs = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adp;

    GoogleMap mMap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contractorhomelayout, container, false);

        //region Get some values and get some views for the fragment
        bundle = getArguments();
        id = bundle.getInt("id");
        role = bundle.getInt("role");
        db = DatabaseHandler.getInstance(getContext());
        lvJobs = (ListView) view.findViewById(R.id.lvJobs);
        spnMarkJobs = (Spinner) view.findViewById(R.id.spnMarkJobCompleted);
        spnSMSJobs = (Spinner) view.findViewById(R.id.spnSMSJob);
        sortJobs = (Button) view.findViewById(R.id.btnSortJobs);
        btnMarkJobs = (Button) view.findViewById(R.id.btnMarkJobCompleted);
        btnSMSJobs = (Button) view.findViewById(R.id.btnSendSMSForJob);
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, truncatedJobs);
        adp = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, jobList);
        //endregion

        fillAllThings();


        lvJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                // The next few lines are the get the number from the listview display then use that number for the job id.
                String selectedFromList = (String) (lvJobs.getItemAtPosition(position));
                String[] split = selectedFromList.split("\\s+");
                // lvSelectedID is the ID for the job, we need that to do all the other selects and get the information about the job
                lvSelectedID = Integer.parseInt(split[0]);

                jobLocationUser = db.selectClientForJob(lvSelectedID);
                if (jobLocationUser.getAddressLine2() == null || jobLocationUser.getAddressLine2().isEmpty()) {
                    jobAddress = jobLocationUser.getAddressLine1() + ", " + jobLocationUser.getSuburb() + ", " + jobLocationUser.getState();
                }
                else if (jobLocationUser.getAddressLine2() != null || !jobLocationUser.getAddressLine2().isEmpty()) {
                    jobAddress = jobLocationUser.getAddressLine1() + ", " + jobLocationUser.getAddressLine2() + ", " + jobLocationUser.getSuburb() + ", " + jobLocationUser.getState();
                }

                Intent mapIntent = new Intent(getContext(), mapActivity.class);

                mapIntent.putExtra("address", jobAddress);
                mapIntent.putExtra("suburb", jobLocationUser.getSuburb());
                startActivity(mapIntent);
//                FragmentManager fr = getFragmentManager();
//                FragmentTransaction ft = fr.beginTransaction();
//                ft.replace(R.id.fragment_placeholder, map);
//                ft.addToBackStack(null);
//                ft.commit();

            }
        });

        //region SortJobs
        sortJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;
                for (int i = 0; i < jobList.size(); i++) {
                            for (int j = i; j < jobList.size(); j++) {
                                if (jobList.get(i).compareTo(jobList.get(j)) > 0) {
                                    temp = jobList.get(i);
                                    jobList.set(i, jobList.get(j));
                                    jobList.set(j, temp);
                                }
                    }
                }
                ArrayAdapter<String> sortedAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, jobList);
                lvJobs.setAdapter(sortedAdapter);
            }
        });
//endregion

        //region MarkJobs

        btnMarkJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String updateJob = spnMarkJobs.getSelectedItem().toString();
                    String[] split = updateJob.split("\\s+");
                    int updateJobID = Integer.parseInt(split[1]);
                    db.updateJobs(updateJobID);
                    DatabaseJob completedJob = db.getJobSingle(updateJobID);
                    DatabaseUser completedClient = db.selectClientForJob(updateJobID);
                    clientPhone = completedClient.getPhone();
                    txtContent = "Dear " + completedClient.getFirstName() + " " + completedClient.getLastName() + ", your job request now has the status of " + completedJob.getStatus() + ". We hope the job has been completed to your satisfaction. If you have any comments please call the office on...";
                    sendSMSForCompletion();
                    adapter.clear();
                    adp.clear();
                    fillAllThings();
                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //endregion

        //region Send SMS
        btnSMSJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String updateJob = spnSMSJobs.getSelectedItem().toString();
                    String[] split = updateJob.split("\\s+");
                    int smsJobID = Integer.parseInt(split[1]);

                    DatabaseJob smsJob = db.getJobSingle(smsJobID);
                    DatabaseUser smsClient = db.selectClientForJob(smsJobID);
                    clientPhone = smsClient.getPhone();
                    txtContent = "Dear " + smsClient.getFirstName() + " " + smsClient.getLastName() + ", this is a confirmation sms regarding a job you have requested through the BIT Services app. The job description is listed as : " + smsJob.getJobDescription() + " One of our contractors will be there to complete the job on " + smsJob.getDateDue() + " at ";
                    sendSMSForTime();
                    adapter.clear();
                    adp.clear();
                    fillAllThings();
                } catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });

        //endregion


        return view;
    }

    private void sendSMSForTime() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto: " + clientPhone));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", clientPhone);
        smsIntent.putExtra("sms_body", txtContent);
        try {
            startActivity(smsIntent);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(),
                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMSForCompletion() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto: " + clientPhone));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", clientPhone);
        smsIntent.putExtra("sms_body", txtContent);
        try {
            startActivity(smsIntent);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(),
                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillAllThings() {

        for (DatabaseJob job : db.getSubmittedJobs(id)) {
            String contractorName;
            DatabaseUser client = db.selectClientForJob(job.getId());
            if ( !client.getAddressLine2().equals("") )
            {
            contractorName = String.valueOf(job.getId()) + " " + client.getFirstName() + " " + client.getLastName() + " " + job.getJobDescription() + " at " + client.getAddressLine1() + " " + client.getAddressLine2() + " " + client.getSuburb();
            }
            else contractorName = String.valueOf(job.getId()) + " " + client.getFirstName() + " " + client.getLastName() + " " + job.getJobDescription() + " at " + client.getAddressLine1() + " " + client.getSuburb();
            jobList.add(contractorName);
            truncatedJobs.add("Job " + String.valueOf(job.getId()));
        }

        lvJobs.setAdapter(adp);

        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSMSJobs.setAdapter(adapter);
        spnMarkJobs.setAdapter(adapter);
    }

}



