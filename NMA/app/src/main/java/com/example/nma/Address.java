package com.example.nma;

import java.io.Serializable;

public class Address implements Serializable {
    private String gemeente;
    private String postcode;
    private String straatnaam;
    private String huisnr;

    public Address(String gemeente, String postcode, String straatnaam, String huisnr) {
        this.gemeente = gemeente;
        this.postcode = postcode;
        this.straatnaam = straatnaam;
        this.huisnr = huisnr;
    }

    @Override
    public String toString() {
        return straatnaam + " " + huisnr + ", " + postcode + " " + gemeente;
    }
}
