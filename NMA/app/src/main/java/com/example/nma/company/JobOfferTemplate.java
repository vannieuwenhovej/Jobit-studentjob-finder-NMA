package com.example.nma.company;

public class JobOfferTemplate extends BasicJobOffer{
    private int jobofferTemplateID;

    public JobOfferTemplate(int ID, Company company, String function, double hourlyWageGross, String description, String imageFilePath, String wantedProfileDescription) {
        super(company, function, hourlyWageGross, description, imageFilePath, wantedProfileDescription);
        this.jobofferTemplateID = ID;
    }

}
