package com.project.sam.bitservices;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sam on 23/05/2017.
 */
public class clientViewJobsFragment extends Fragment {

    Button btnSortClientJobs, btnClientJobsGoBack;
    DatabaseHandler db;
    int id;
    Bundle bundle;
    ListView lvClientJobs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        View view = inflater.inflate(R.layout.clientviewjobslayout, container, false);
        db = DatabaseHandler.getInstance(getContext());
        bundle = getArguments();
        id = bundle.getInt("id");

        ArrayList<String> jobs = new ArrayList<>();
        for (DatabaseJob job : db.getJobsForClient(id)) {
            String jobID = String.valueOf(job.getId());
            String createdDate = job.getDateCreated();
            String dueDate = job.getDateDue();
            String completedDate = job.getDateCompleted();
            String status = job.getStatus();
            String jobDetails = "Job ID: " + jobID + ", Date Created: " + createdDate + ", Date Due: " + dueDate + ", Date Completed: " + completedDate + ", Status: " + status;
            jobs.add(jobDetails);
        }

        lvClientJobs = (ListView) view.findViewById(R.id.lvClientJobs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, jobs);
        lvClientJobs.setAdapter(adapter);

        btnSortClientJobs = (Button) view.findViewById(R.id.btnSortClientJobsDate);
        btnSortClientJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> jobsSorted = new ArrayList<>();
                for (DatabaseJob job1 : db.getJobsForClientSorted(id)) {
                    String jobID1 = String.valueOf(job1.getId());
                    String createdDate1 = job1.getDateCreated();
                    String dueDate1 = job1.getDateDue();
                    String completedDate1 = job1.getDateCompleted();
                    String status1 = job1.getStatus();
                    String jobDetails1 = "Job ID: " + jobID1 + ", Date Created: " + createdDate1 + ", Date Due: " + dueDate1 + ", Date Completed: " + completedDate1 + ", Status: " + status1;
                    jobsSorted.add(jobDetails1);
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, jobsSorted);
                lvClientJobs.setAdapter(adapter1);
            }
        });
        return view;
    }
}
