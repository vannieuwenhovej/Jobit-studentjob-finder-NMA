package com.example.nma.config;

public class ConnectionConfig {

    private static String IPAddress = "172.16.3.22"; //TODO: VERANDEREN BIJ SERVER IMPLEMENTATIE
    private static String Port = "8080";

    public static String getServerAddress() {
        return "http://" + IPAddress + ":" + Port; //no SSL
    }
}
