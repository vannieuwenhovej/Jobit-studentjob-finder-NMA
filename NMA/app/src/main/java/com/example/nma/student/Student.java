package com.example.nma.student;

import com.example.nma.Address;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private int ID;
    private String firstname;
    private String lastname;
    private Address address;
    private Date birthdate;
    private char gender;
    private String filePathCV; //file path on the server
    private String bankname;
    private String billnr;
    private String billname;
    private String emailLogin;
    private String passwordLogin;

    public Student(int ID, String firstname, String lastname, Address address, Date birthdate,
                   char gender, String bankname, String billnr, String billname, String emailLogin) {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.birthdate = birthdate;
        this.gender = gender;
        this.bankname = bankname;
        this.billnr = billnr;
        this.billname = billname;
        this.emailLogin = emailLogin;
    }


    public int getID() {
        return ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Address getAddress() {
        return address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public char getGender() {
        return gender;
    }

    public String getBankname() {
        return bankname;
    }

    public String getBillnr() {
        return billnr;
    }

    public String getBillname() {
        return billname;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address=" + address +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", filePathCV='" + filePathCV + '\'' +
                ", bankname='" + bankname + '\'' +
                ", billnr='" + billnr + '\'' +
                ", billname='" + billname + '\'' +
                ", emailLogin='" + emailLogin + '\'' +
                '}';
    }
}
