package com.example.nma.api.interfaces;

import com.example.nma.company.JobOffer;

import java.util.ArrayList;

public interface AsyncResponseJobOffers {

    void processFinish(ArrayList<JobOffer> output);
}
