package com.hnka.csd.client;

import android.content.Context;

public class ClientFactory {

    protected static String token;
    public static final String HOST = "https://csd-dev.herokuapp.com";

    private static ClientLogin clientLogin;
    private static ClientProfile clientProfile;
    private static ClientHome clientHome;
    private static ClientSerie clientSerie;

    public static ClientLogin getClientLoginInstance(Context context) {
        if (ClientFactory.clientLogin == null){
            ClientFactory.clientLogin = new ClientLogin(context);
        }

        return ClientFactory.clientLogin;
    }

    public static ClientProfile getClientProfileInstance(Context context) {
        if (ClientFactory.clientProfile == null){
            ClientFactory.clientProfile = new ClientProfile(context);
        }

        return ClientFactory.clientProfile;
    }

    public static ClientHome getClientHomeInstance(Context context) {
        if (ClientFactory.clientHome == null){
            ClientFactory.clientHome = new ClientHome(context);
        }

        return ClientFactory.clientHome;
    }

    public static ClientSerie getClientSerieInstance(Context context) {
        if (ClientFactory.clientSerie == null){
            ClientFactory.clientSerie = new ClientSerie(context);
        }

        return ClientFactory.clientSerie;
    }
}
