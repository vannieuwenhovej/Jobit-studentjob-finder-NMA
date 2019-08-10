package com.example.nma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nma.api.APIDeleteJobByID;
import com.example.nma.api.APIRetrieveJobByID;
import com.example.nma.api.APIUpdateJobByID;
import com.example.nma.api.interfaces.AsyncResponseJobOfferByID;
import com.example.nma.company.JobOffer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DisplayJobActivity extends AppCompatActivity implements AsyncResponseJobOfferByID {
    private int jobID;

    APIRetrieveJobByID apiGetJob;
    APIDeleteJobByID apiDelJob;
    JobOffer jobAfterAPICall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Your Open Jobs");
        jobID = getIntent().getExtras().getInt("jobID");
        apiGetJob = new APIRetrieveJobByID(jobID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);
        //to get jobs, see processFinish();
        apiGetJob.delegate = this;
        apiGetJob.execute();
    }

    public DisplayJobActivity(int jobID) {
        this.jobID = jobID;
    }

    public DisplayJobActivity() {
    }

    public void btnViewSollicitants(View vw){

        Intent intent = new Intent(DisplayJobActivity.this, DisplaySollicitants.class);
        intent.putExtra("job", jobAfterAPICall); //pass the ID
        startActivity(intent);
    }

    public void btnDeleteJob(View vw){
        apiDelJob = new APIDeleteJobByID(jobID);
        apiDelJob.execute();
        Toast.makeText(getBaseContext(), jobAfterAPICall.getFunction() + " with ID " + jobAfterAPICall.getJobOfferID() + " succesfully deleted.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void btnUpdateJob(View vw){
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);

        EditText inputFunctie = findViewById(R.id.inputFunctie);
        EditText inputWage = findViewById(R.id.inputWage);
        EditText inputDescription = findViewById(R.id.inputDescription);
        EditText inputWanted = findViewById(R.id.inputWanted);
        EditText inputDateBegin = findViewById(R.id.dateBegin);
        EditText inputDateEnd = findViewById(R.id.dateEnd);
        //Conversion to string
        String strFunctie = inputFunctie.getText().toString();
        String strWage = inputWage.getText().toString();
        String strDescription = inputDescription.getText().toString();
        String strWanted = inputWanted.getText().toString();
        String strStartDate = inputDateBegin.getText().toString();
        String strEndDate = inputDateEnd.getText().toString();
        if (jobAfterAPICall != null){
            try {
                //true if there are changes made
                if (!(strFunctie.equals(jobAfterAPICall.getFunction()) && strWage.equals(Double.toString(jobAfterAPICall.getHourlyWageGross())) &&
                strDescription.equals(jobAfterAPICall.getDescription()) && strWanted.equals(jobAfterAPICall.getWantedProfileDescription()) &&
                formatter.parse(strStartDate).equals(jobAfterAPICall.getStartDate()) && formatter.parse(strEndDate).equals(jobAfterAPICall.getEndDate()))){

                    JobOffer jobWithUpdatedDetails = new JobOffer(jobID, null, strFunctie, Double.parseDouble(strWage), strDescription,
                            null,strWanted, formatter.parse(strStartDate), formatter.parse(strEndDate));
                    updateJobToServer(jobWithUpdatedDetails);
                }
                //else{} there is nothing to do except to go back to previous activity which is MainActivity
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(getBaseContext(), strFunctie + " (" + jobID  + ") has been updated", Toast.LENGTH_LONG).show();
        finish(); /*this makes the activity close and return previous activity which is MainActivity.java */

    }

    private void updateJobToServer(JobOffer jobWithUpdatedDetails) {
        new APIUpdateJobByID(jobWithUpdatedDetails).execute();
    }

    @Override
    public void processFinish(final JobOffer job) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                setTextInInputs(job);
            }
        });
    }
    private void setTextInInputs(JobOffer job) {
        jobAfterAPICall = job;
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH);
        TextView inputFunctie = findViewById(R.id.inputFunctie);
        inputFunctie.setText(job.getFunction());

        EditText inputWage = findViewById(R.id.inputWage);
        String wageAsStr = Double.toString(job.getHourlyWageGross());
        inputWage.setText(wageAsStr);

        TextView inputDesc = findViewById(R.id.inputDescription);
        inputDesc.setText(job.getDescription());

        TextView inputWanted = findViewById(R.id.inputWanted);
        inputWanted.setText(job.getWantedProfileDescription());

        TextView dateBegin = findViewById(R.id.dateBegin);
        dateBegin.setText(formatter.format(job.getStartDate()));

        TextView dateEnd = findViewById(R.id.dateEnd);
        dateEnd.setText(formatter.format(job.getEndDate()));

        Button btn = findViewById(R.id.btnViewSollicitants);
        if (job.getSollicitanten().size() == 0){
            btn.setText(getString(R.string.noSollicitantsCaps));
        } else if(job.getSollicitanten().size() == 1){
            btn.setText(job.getSollicitanten().size() + " " + getString(R.string.sollicitant));
        } else{
            btn.setText(job.getSollicitanten().size() + " " + getString(R.string.sollicitantsCaps));
        }

    }
}
