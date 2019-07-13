package com.example.nma;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nma.api.APIRetrieveAllOpenJobs;
import com.example.nma.api.interfaces.AsyncResponseJobOffers;
import com.example.nma.company.JobOffer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AsyncResponseJobOffers {
    ConstraintLayout constraintLayout;
//    LinearLayout linearLayout;

    APIRetrieveAllOpenJobs apiGetJobs = new APIRetrieveAllOpenJobs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("All Your Jobs");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to get jobs, see processFinish();
        apiGetJobs.delegate = this;
        apiGetJobs.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Okay this is a bit tricky:
        //Checks if the the task on launch is still running if not:
        //The activity is resumed after deleting/editing/adding a job
        //if so I refresh the UI and retrieve the jobs.
        if (!(apiGetJobs.getStatus() == AsyncTask.Status.RUNNING)){
            LinearLayout linearLayout = findViewById(R.id.idLayout);
            linearLayout.removeAllViews();
            APIRetrieveAllOpenJobs newOne = new APIRetrieveAllOpenJobs();
            newOne.delegate = this;
            newOne.execute();
        }
    }
    protected void btnAddJob() {
        startActivity(new Intent(MainActivity.this, AddNewJobActivity.class));
    }

    protected void btnSeeJob(JobOffer job) {
        Intent intent = new Intent(MainActivity.this, DisplayJobActivity.class);
        intent.putExtra("jobID", job.getJobOfferID()); //pass the ID
        startActivity(intent);
    }


    @Override
    public void processFinish(ArrayList<JobOffer> output) {
        addJobsToHomeScreen(output);
    }

    private void addJobsToHomeScreen(final ArrayList<JobOffer> jobs) {
//        setContentView(R.layout.activity_main);-
        final Context ctx = this;
        runOnUiThread(new Runnable() { //this is needed to update stuff on the main thread, otherwise exception
            @Override
            public void run() {
                setJobs(jobs, ctx);
            }
        });

    }
    public void setJobs(ArrayList<JobOffer> jobs, Context ctx){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d'th' YYYY", Locale.ENGLISH);
        LinearLayout linearLayout = findViewById(R.id.idLayout);


        Button myButton = new Button(this);
        myButton.setId(R.id.btnAddJob); //I have defined this in res/values/strings.xml
        myButton.setText(getString(R.string.createAJob));

        //Calls the method btnAddJob() when clicked on the button
        myButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                btnAddJob();
            }
        });
        linearLayout.addView(myButton);

        for (int i=0; i<jobs.size(); i++) {
            //Creating Title and wage Text
            final JobOffer job = jobs.get(i);
            TextView jobTitle = new TextView(ctx);
            jobTitle.setText(String.format("%s %s (€%s/h)", getString(R.string.txtJobAs), job.getFunction() + " (" + job.getJobOfferID() + ")", job.getHourlyWageGross()));

            jobTitle.setTextSize(22);
            jobTitle.setTypeface(null, Typeface.BOLD);

            //Creating date period Text
            TextView period = new TextView(ctx);
            period.setText(String.format("%s - %s", formatter.format(job.getStartDate()), formatter.format(job.getEndDate())));
            period.setTextSize(18);
            period.setTypeface(null, Typeface.ITALIC);

            //Creating the description Text
            TextView desc = new TextView(ctx);
            desc.setText(String.format("Description:\n%s", formatTextToUnder100Chars(job.getDescription())));
            desc.setTextSize(16);

            //Creating the wanted profile Text
            TextView profile = new TextView(ctx);
            profile.setText(String.format("Wanted profile:\n%s", formatTextToUnder100Chars(job.getWantedProfileDescription())));
            profile.setTextSize(16);


            //Creating parameters for the textviews
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //Setting the above params to our TextView

            //Margins & Padding for the Textviews
            ArrayList<TextView> txtViews = new ArrayList<>();
            txtViews.add(jobTitle);txtViews.add(period);txtViews.add(desc);txtViews.add(profile);
            for (TextView tv : txtViews){
                tv.setPadding(0, 5, 0, 0);
                tv.setLayoutParams(params);
                tv.setBackgroundColor(Color.LTGRAY);
                tv.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        btnSeeJob(job);
                    }
                });
                linearLayout.addView(tv);

            }
            params.setMargins(25, 0, 100, 0);
            profile.setPadding(0,0,0, 40);
            LinearLayout.LayoutParams profMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            profMargin.setMargins(25,0,100,20);
            profile.setLayoutParams(profMargin);
        }
    }

    private String formatTextToUnder100Chars(String text){
        if (text.length() > 100){
            return text.substring(0,97) + "...";
        }
        return text;
    }
}
