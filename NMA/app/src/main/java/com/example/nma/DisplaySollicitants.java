package com.example.nma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nma.company.JobOffer;
import com.example.nma.student.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DisplaySollicitants extends AppCompatActivity {
    private JobOffer currentJob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sollicitants);
        currentJob = (JobOffer) getIntent().getSerializableExtra("job");
        this.setTitle("Sollicitants for " + currentJob.getFunction());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        final Context ctx = this;
        LinearLayout linearLayout = findViewById(R.id.sollicitantsLayout);
        makeTitle(linearLayout, params);
        setSollicitants(linearLayout, params, ctx);
    }


    @SuppressLint("SetTextI18n")
    private void makeTitle(LinearLayout layout, LinearLayout.LayoutParams params) {
        TextView title = new TextView(this);
        title.setLayoutParams(params);
        title.setText("Sollicitants for " + currentJob.getFunction());
        title.setBackgroundColor(Color.LTGRAY);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24);
        title.setPadding(0, 5, 0, 5);
        layout.addView(title);
    }


    @SuppressLint("SetTextI18n")
    private void setSollicitants(LinearLayout linearLayout, LinearLayout.LayoutParams params, Context ctx) {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        for (int i=0; i<currentJob.getSollicitanten().size(); i++) {
            //Creating Title and wage Text
            Student sollicitant = currentJob.getSollicitanten().get(i);
            TextView name = new TextView(ctx);
            name.setText(sollicitant.getLastname() + " " + sollicitant.getFirstname() + " (" + sollicitant.getID() + ")");

            name.setTextSize(22);
            name.setTypeface(null, Typeface.BOLD);

            //Creating date period Text
            TextView birthDate = new TextView(ctx);
            birthDate.setText("Birthdate: " + formatter.format(sollicitant.getBirthdate()));
            birthDate.setTextSize(18);
            birthDate.setTypeface(null, Typeface.ITALIC);

            //Creating the description Text
            TextView email = new TextView(ctx);
            email.setText(sollicitant.getEmailLogin());
            email.setTextSize(18);

            //Creating the wanted profile Text
            TextView address = new TextView(ctx);
            address.setText(sollicitant.getAddress().toString());
            address.setTextSize(16);

            TextView billnr = new TextView(ctx);
            billnr.setText("Billnr: " + sollicitant.getBillnr());
            billnr.setTextSize(16);

            TextView nameOnBill = new TextView(ctx);
            nameOnBill.setText("Name on bill: " + sollicitant.getBillname());
            nameOnBill.setTextSize(16);

            TextView bankDetails = new TextView(ctx);
            bankDetails.setText("Bank: " + sollicitant.getBankname());
            bankDetails.setTextSize(16);

            //Margins & Padding for the Textviews
            ArrayList<TextView> txtViews = new ArrayList<>();
            txtViews.add(name);txtViews.add(birthDate);txtViews.add(email);txtViews.add(billnr); txtViews.add(nameOnBill);txtViews.add(bankDetails);
            for (TextView tv : txtViews){
                tv.setPadding(0, 5, 0, 0);
                tv.setLayoutParams(params);
                tv.setBackgroundColor(Color.LTGRAY);
                linearLayout.addView(tv);

            }
            params.setMargins(25, 0, 100, 0);
            bankDetails.setPadding(0,0,0, 40);
            LinearLayout.LayoutParams profMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            profMargin.setMargins(25,0,100,20);
            bankDetails.setLayoutParams(profMargin);
        }

    }
}
