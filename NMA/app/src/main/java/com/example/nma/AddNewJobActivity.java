package com.example.nma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nma.api.APISendNewJobTask;
import com.example.nma.company.JobOffer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewJobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Creating new Job");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job);
    }

    public void btnSaveJob(View vw) {
        EditText inputFunctie = (EditText) findViewById(R.id.inputFunctie);
        EditText inputWage = (EditText) findViewById(R.id.inputWage);
        EditText inputDescription = (EditText) findViewById(R.id.inputDescription);
        EditText inputWanted = (EditText) findViewById(R.id.inputWanted);
        EditText inputDateBegin = (EditText) findViewById(R.id.dateBegin);
        EditText inputDateEnd = (EditText) findViewById(R.id.dateEnd);
//        TextView tview = (TextView)findViewById(R.id.textview1); /*For getting a TextView, handy for later */
        String strFunctie = inputFunctie.getText().toString();
        String strWage = inputWage.getText().toString();
        String strDescription = inputDescription.getText().toString();
        String strWanted = inputWanted.getText().toString();
        String strStartDate = inputDateBegin.getText().toString();
        String strEndDate = inputDateEnd.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        Date endDate = new Date();

        if (!(strFunctie.equals("") || strWage.equals("") || strDescription.equals("") || strWanted.equals("") || strStartDate.equals("") || strEndDate.equals(""))) {
            double dblWage = Double.parseDouble(strWage);
            try {
                startDate = format.parse(strStartDate);
                endDate = format.parse(strEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            JobOffer newJobOffer = new JobOffer(-1, null, strFunctie, dblWage, strDescription, null, strWanted,
                    startDate, endDate);
            Toast.makeText(getBaseContext(), "Job created: " + strFunctie + " (€" + strWage + "/h) " + format.format(startDate) +
                    " till " + format.format(endDate), Toast.LENGTH_SHORT).show();

            try {
                Log.d("DATE", "date before func startdate: " + newJobOffer.getStartDate());
                makeJSONAndSendToServer(newJobOffer); // Sends the Job as JSON to the node.js server
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish(); /*this makes the activity close and return previous activity which is MainActivity.java */
        } else {
            Toast.makeText(getBaseContext(), "Please make sure everything is filled out.", Toast.LENGTH_LONG).show();
        }
    }

    private void makeJSONAndSendToServer(JobOffer newJobOffer) throws IOException {
        Gson g = new GsonBuilder().setDateFormat("YYYY-MM-dd").create();
        String json =  g.toJson(newJobOffer);
        new APISendNewJobTask(json).execute();
    }
}
