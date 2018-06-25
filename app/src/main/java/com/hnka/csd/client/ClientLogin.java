package com.hnka.csd.client;

public class ClientLogin {

    protected ClientLogin() {
    }

    // TODO: Adjust request to get in parameter the callback

    public void login(String userName, String password) {
        String url = ClientFactory.HOST + "/login";
        // TODO: set token on return success
    }

    public void register(String userName, String password) {
        String url = ClientFactory.HOST + "/register";
    }

    public void logout() {
        ClientFactory.token = "";
    }
}
