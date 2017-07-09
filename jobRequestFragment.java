package com.project.sam.bitservices;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.NotificationManager;

import java.text.SimpleDateFormat;
import java.util.*;
//import java.time.*;

/**
 * Created by Sam on 18/05/2017.
 */
public class jobRequestFragment extends Fragment {

    private static int counter = 0;
    private static int year;
    private static int month;
    private static int day;

    Bundle bundle;
    int id, role, newJobID;
    String dateDue, jobDetails, clientLocation;
    Spinner skillSpinner;
    Spinner contractorSpinner;
    Button btnDate, btnSubmitJobRequest;
    TextView jobDescription;
    static TextView displayDate;

    DatabaseHandler db;
    @Override
    public View onCreateView(LayoutInflater inflate, ViewGroup container, Bundle savedInstanceState) {
        // bundle passed from the clientHome, which was passed from the login so we know who is logged in and can pass the clientID into the jobRequest
        bundle = getArguments();
        id = bundle.getInt("id");
        role = bundle.getInt("role");
        db = DatabaseHandler.getInstance(getContext());
        //Create the view that the fragment will return, but also so that the different views can be id'd and used (Buttons, textviews, etc)
        final View view = inflate.inflate(R.layout.jobrequestlayout, container, false);

        //region Fill the contractor and the skill spinners
        List<String> contractorList = new ArrayList<>();
        for (DatabaseUser user : db.selectContractors()) {
            String contractorName = user.getFirstName() + " " + user.getLastName();
            contractorList.add(contractorName);
        }
        contractorSpinner = (Spinner)view.findViewById(R.id.spnContractor);
        ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, contractorList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contractorSpinner.setAdapter(adp);

        List<String> skillList = new ArrayList<>();
        for (DatabaseSkill skill : db.getSkills()) {
            String skillTitle = skill.getTitle();
            skillList.add(skillTitle);
        }
        skillSpinner = (Spinner) view.findViewById(R.id.spnSkill);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, skillList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillSpinner.setAdapter(adapter);
        //endregion

        jobDescription = (EditText) view.findViewById(R.id.etJobDescription);
        //region DisplayDate stuff
        // This is the textview that will display the date when the client has chosen when the job is due
        displayDate = (TextView) view.findViewById(R.id.tvDisplayDate);
        // This is the button that is pressed by the client in order to choose the date for the job.
        btnDate = (Button) view.findViewById(R.id.btnSelectDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker" );
            }
        });
        //endregion

        btnSubmitJobRequest = (Button)view.findViewById(R.id.btnSubmitJobRequest);
        btnSubmitJobRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DatabaseUser thisUser = db.selectSingleClient(id);
                    try {
                        //region Add The Job
                        DatabaseJob newJob = new DatabaseJob();
                        newJob.setDateCreated(getDateTime());
                        newJob.setDateDue(displayDate.getText().toString());
                        newJob.setDateCompleted(null);
                        newJob.setSkillId(skillSpinner.getSelectedItemPosition() + 1);
                        newJob.setContractorId(contractorSpinner.getSelectedItemPosition() + 1);
                        newJob.setClientId(thisUser.getId());
                        newJob.setJobDescription(jobDescription.getText().toString());
                        newJob.setKms(0);
                        newJob.setStatus("Submitted");
                        db.addJob(newJob);

                        DatabaseJob maxJobId = db.selectMaxJobId();
                        newJobID = maxJobId.getId();
                        dateDue = newJob.getDateDue();
                        jobDetails = newJob.getJobDescription();

                        DatabaseUser clientForNewJob = db.selectClientForJob(newJobID);
                        if (clientForNewJob.getAddressLine2() != null) {
                            clientLocation = clientForNewJob.getAddressLine1() + ", " + clientForNewJob.getAddressLine2() + ", " + clientForNewJob.getSuburb() + ", " + clientForNewJob.getPostcode() + ", " + clientForNewJob.getState();
                        }
                        else clientLocation = clientForNewJob.getAddressLine1() + ", " + clientForNewJob.getSuburb() + ", " + clientForNewJob.getPostcode() + ", " + clientForNewJob.getState();
                        Toast.makeText(getContext(), "Job added successfully.", Toast.LENGTH_LONG).show();
                        //endregion

                        //region Open The Client Home
                            bundle.putInt("id", id);
                            bundle.putInt("role", role);
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            clientHomeFragment clientHomeFragment = new clientHomeFragment();
                            clientHomeFragment.setArguments(bundle);
                            ft.replace(R.id.fragment_placeholder, clientHomeFragment);
                        ft.addToBackStack("clientHome");
                            ft.commit();
                        //endregion

                        SendNotification();

                        MakeCalendarEntry();
                    }
                    catch (Exception ex){
                        Toast.makeText(getContext(),"Something went wrong, " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        });

        // Inflate the layout for this fragment
        return view;
    }

    private void MakeCalendarEntry() {
        Intent calIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        calIntent.putExtra(CalendarContract.Events.TITLE, "Job for " + dateDue);
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, jobDetails);
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, clientLocation);

        String dateArray[] = dateDue.split("-");
        Calendar c = Calendar.getInstance();
        int calendarMonth = Integer.parseInt(dateArray[1]);
        int adjustedMonth = calendarMonth - 1;
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]));
        c.set(Calendar.MONTH, adjustedMonth);
        c.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, c.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        startActivity(calIntent);
    }

    private void SendNotification() {
        //region Send Notification
        // Get the contractor name
        DatabaseUser contractor = db.selectContractorForJob(newJobID);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        mBuilder.setSmallIcon(R.drawable.notificationflat);
        mBuilder.setContentTitle("Job Request For: " + dateDue);
        mBuilder.setContentText("New job request");
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Contractor " + contractor.getFirstName() + " " + contractor.getLastName() + " will be in touch regarding the best time to complete the job for you."));

        NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(101, mBuilder.build());


        //endregion
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            if (counter < 1) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = (c.get(Calendar.MONTH ));
                int day = c.get(Calendar.DAY_OF_MONTH);
                counter++;
                return new DatePickerDialog(getActivity(), this, year, month, day);

            }
            else {
                return new DatePickerDialog(getActivity(), this, year, month, day);

            }
            // Create a new instance of DatePickerDialog and return it

        }

        public void onDateSet(DatePicker view, int syear, int smonth, int sday) {
            // Do something with the date chosen by the user
            year = syear;
            month = smonth + 1;
            day = sday;
            String completeDate = (year + "-" + month + "-" + day);

            displayDate.setText(completeDate);
        }
    }
}
