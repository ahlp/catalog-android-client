package com.hnka.csd.client;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ClientProfile {

    private RequestQueue queue;

    protected ClientProfile(Context context) { this.queue = Volley.newRequestQueue(context);  }

    public void getProfile(String id, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/csd/api/profiles/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        this.queue.add(stringRequest);
    }

    public void createProfile(final String name, final String birthday,
                              final String avatarLink, final String bios,
                              final String token,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/csd/api/profiles/";

        // POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("token", token);
                return params;
            }
            @Override
            public byte[] getBody()
            {

                String body = "{\"profile\":{";

                body = body + "\"name\":\""+ name + "\",";
                body = body + "\"birthday\":\""+ birthday + "\",";
                body = body + "\"avatarLink\":\""+ avatarLink + "\",";
                body = body + "\"about\":\""+ bios + "\"";

                body = body + "}}";
                return body.getBytes();
            }
        };

        this.queue.add(stringRequest);
    }

    public void updateProfile(String id, final String name, final String birthday, final String avatarLink,
                              final String bios, final String token,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/csd/api/profiles/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, listener,
                errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("token", token);
                return params;
            }
            @Override
            public byte[] getBody()
            {
                String body = "{\"profile\":{";

                if (!name.equals(""))
                    body.concat("\"name\":\""+ name + "\",");
                if (!birthday.equals(""))
                    body.concat("\"birthday\":\""+ birthday + "\",");
                if (!avatarLink.equals(""))
                    body.concat("\"avatarLink\":\""+ avatarLink + "\",");
                if (!bios.equals(""))
                    body.concat("\"about\":\""+ bios + "\"");

                if (body.substring(body.length() - 1).equals(","))
                    body = body.substring(0, body.length() - 2);

                body.concat("}}");
                return body.getBytes();
            }
        };
        this.queue.add(stringRequest);
    }
}
