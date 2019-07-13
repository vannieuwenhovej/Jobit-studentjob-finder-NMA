package com.example.nma.company;

import java.io.Serializable;

public abstract class BasicJobOffer implements Serializable {
    private Company company;
    private String function;
    private double hourlyWageGross;
    private String description;
    private String imageFilePath;
    private String wantedProfileDescription;

    public BasicJobOffer(Company company, String function, double hourlyWageGross, String description, String imageFilePath, String wantedProfileDescription) {
        this.company = company;
        this.function = function;
        this.hourlyWageGross = hourlyWageGross;
        this.description = description;
        this.imageFilePath = imageFilePath;
        this.wantedProfileDescription = wantedProfileDescription;
    }

    @Override
    public String toString() {
        return "BasicJobOffer{" +
                "company=" + company +
                ", function='" + function + '\'' +
                ", hourlyWageGross=" + hourlyWageGross +
                ", description='" + description + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", wantedProfileDescription='" + wantedProfileDescription + '\'' +
                '}';
    }

    public Company getCompany() {
        return company;
    }

    public String getFunction() {
        return function;
    }

    public double getHourlyWageGross() {
        return hourlyWageGross;
    }

    public String getDescription() {
        return description;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public String getWantedProfileDescription() {
        return wantedProfileDescription;
    }
}
