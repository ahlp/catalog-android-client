package com.hnka.csd.client;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ClientLogin {

    private RequestQueue queue;

    protected ClientLogin(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.queue.start();
    }

    public void login(String userName, String password, Response.Listener<String> listener,
                      Response.ErrorListener errorListener) {

        String url = ClientFactory.HOST + "/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener);
        this.queue.add(stringRequest);
    }

    public void register(String userName, String password, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {

        String url = ClientFactory.HOST + "/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener);
        this.queue.add(stringRequest);
    }

    public void logout() {
        ClientFactory.token = "";
    }
}
