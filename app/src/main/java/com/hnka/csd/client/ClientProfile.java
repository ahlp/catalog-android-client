package com.hnka.csd.client;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ClientProfile {

    private RequestQueue queue;

    protected ClientProfile(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void getProfile(String id, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/api/profiles/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        this.queue.add(stringRequest);
    }

    public void createProfile(String userName, String birthday, String avatarLink, String bios,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/api/profiles/";

        final String contextUsername = userName;
        final String contextBirthday = birthday;
        final String contextAvatar = avatarLink;
        final String contextBios = bios;

        // POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("userName", contextUsername);
                params.put("birthday", contextBirthday);
                params.put("avatarLink", contextAvatar);
                params.put("bios", contextBios);

                return params;
            }
        };

        this.queue.add(stringRequest);
    }

    public void updateProfile(String userName, String birthday, String avatarLink, String bios,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/api/profiles/";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, listener, errorListener);
        this.queue.add(stringRequest);
    }
}
