package com.hnka.csd.client;

public class ClientProfile {

    protected ClientProfile() {
    }

    // TODO: Adjust request to get in parameter the callback

    public void getProfile(String id) {
        String url = ClientFactory.HOST + "/api/profile/" + id;
    }

    public void createProfile(String userName, String birthday, String avatarLink, String bios) {
        String url = ClientFactory.HOST + "/api/profile";
        // POST
    }

    public void updateProfile(String userName, String birthday, String avatarLink, String bios) {
        // TODO: need send /profile/:id ?
        String url = ClientFactory.HOST + "/api/profile";
        // PUT
    }
}
