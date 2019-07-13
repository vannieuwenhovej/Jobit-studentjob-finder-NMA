package com.example.nma.company;

import com.example.nma.student.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class JobOffer extends BasicJobOffer implements Serializable {
    private int jobOfferID;
    private Date startDate;
    private Date endDate;
    private int studentID;
    private ArrayList<Student> sollicitanten;

    public JobOffer(int jobOfferID, Company company, String function, double hourlyWageGross, String description, String imageFilePath, String wantedProfileDescription, Date startDate, Date endDate) {
        super(company, function, hourlyWageGross, description, imageFilePath, wantedProfileDescription);
        this.jobOfferID = jobOfferID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ArrayList<Student> getSollicitanten() {
        return sollicitanten;
    }

    public void setSollicitanten(ArrayList<Student> sollicitanten) {
        this.sollicitanten = sollicitanten;
    }

    public int getJobOfferID() {
        return jobOfferID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Job (ID: " + jobOfferID +
                ") as " + getFunction() +
                " paid €" + getHourlyWageGross() +
                "/h";
    }
}
