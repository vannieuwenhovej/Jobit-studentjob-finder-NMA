package com.example.nma.api;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nma.Address;
import com.example.nma.api.interfaces.AsyncResponseSollicitants;
import com.example.nma.config.ConnectionConfig;
import com.example.nma.student.Student;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class APIRetrieveSollicitantsForJob extends AsyncTask<Void, String, String> {

    private int jobID;
    private APIRetrieveJobByID caller;
    public AsyncResponseSollicitants delegate = null;

    public APIRetrieveSollicitantsForJob(APIRetrieveJobByID original, int jobID) {
        caller = original;
        this.jobID = jobID;
    }

    @Override
    protected String doInBackground(Void... voids) {
// Create URL
        URL url = null;
        try {
            url = new URL(ConnectionConfig.getServerAddress() + "/nmaapi/job/" + jobID + "/sollicitants");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

// Create connection
        HttpURLConnection myConnection = null;
        try {
            myConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myConnection.setRequestProperty("User-Agent", "NMA/Android-JobApplication");
        myConnection.setRequestProperty("Accept",
                "application/json");

        try {
            if (myConnection.getResponseCode() == 200) {
            } else {
                Log.d("API", "Connection to job/sollicitants API failed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream responseBody = null;
        try {
            responseBody = myConnection.getInputStream(); //inputstream
            String s = "";
            String line = "";

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(responseBody));

            // Read response until the end
            while ((line = rd.readLine()) != null) {
                s += line;
//                JSONObject json = new JSONObject(s);
//                System.out.println(json.get("title"));
            }
            System.out.println(s);
            parseDataToJSON(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
//        caller.setSollicitants();
        return null;
    }

    private void parseDataToJSON(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        //Firstly get array of jobs as Json.
        JsonArray jsonObjects = new JsonParser()
                .parse(s)
                .getAsJsonArray();
        ArrayList<Student> sollicitants = new ArrayList<>();

        for (int i=0; i<jsonObjects.size(); i++){
            JsonObject obj = jsonObjects.get(i).getAsJsonObject();
            Address address = new Address(obj.get("fldStudentGemeente").getAsString(), obj.get("fldStudentPostcode").getAsString(), obj.get("fldStudentStraat").getAsString(),
                    obj.get("fldStudentHuisnr").getAsString());
            Student student = new Student(obj.get("fldStudentID").getAsInt(), obj.get("fldStudentVoornaam").getAsString(), obj.get("fldStudentAchternaam").getAsString(),
                    address, format.parse(obj.get("fldStudentGeboortedatum").getAsString()), obj.get("fldStudentGeslacht").getAsCharacter(),
                    obj.get("fldBanknaam").getAsString(), obj.get("fldRekeningnr").getAsString(), obj.get("fldRekeningnr").getAsString(),
                    obj.get("fldStudentEmailLogin").getAsString());
            sollicitants.add(student);
        }
        onPostExecute(sollicitants);
    }
    private void onPostExecute(ArrayList<Student> result) {
        delegate.processFinish(result);
    }
}
