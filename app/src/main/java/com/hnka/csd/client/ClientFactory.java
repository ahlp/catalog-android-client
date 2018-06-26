package com.hnka.csd.client;

public class ClientFactory {

    protected static String token;
    public static final String HOST = "localhost:30412";

    private static ClientLogin clientLogin;
    private static ClientProfile clientProfile;

    public static ClientLogin getClientLoginInstance() {
        if (ClientFactory.clientLogin == null){
            ClientFactory.clientLogin = new ClientLogin();
        }

        return ClientFactory.clientLogin;
    }

    public static ClientProfile getClientProfileInstance() {
        if (ClientFactory.clientProfile == null){
            ClientFactory.clientProfile = new ClientProfile();
        }

        return ClientFactory.clientProfile;
    }

}
