package com.hnka.csd.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientLogin {

    private RequestQueue queue;

    protected ClientLogin(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.queue.start();
    }

    public void login(final String userName, final String password, Response.Listener<String> listener,
                      Response.ErrorListener errorListener) {

        String url = ClientFactory.HOST + "/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                listener,
                errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
            @Override
            public byte[] getBody(){
                String body = "{\"login\":\"" + userName + "\", \"password\": \""+ password +"\"}";

                return body.getBytes();
            }
        };
        this.queue.add(stringRequest);
    }

    public void register(final String userName, final String password, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {

        String url = ClientFactory.HOST + "/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener,
                errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
            @Override
            public byte[] getBody(){
                String body = "{\"login\":\"" + userName + "\", \"password\": \""+ password +"\"}";

                return body.getBytes();
            }
        };
        this.queue.add(stringRequest);
    }

    public void logout() {
        ClientFactory.token = "";
    }
}
