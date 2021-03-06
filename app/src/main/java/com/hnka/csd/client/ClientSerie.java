package com.hnka.csd.client;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ClientSerie {
    private RequestQueue queue;

    protected ClientSerie(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.queue.start();
    }

    private void requestSeries(Map<String, String> params, int method, String id, final String token,
                             Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        String url = ClientFactory.HOST + "/api/series/" + id;

        final Map<String, String> requestParams = params;
        StringRequest stringRequest = new StringRequest(method, url, listener, errorListener){
            @Override
            protected Map<String, String> getParams()
            {
                return requestParams;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        this.queue.add(stringRequest);
    }

    public void createSerie(final String title, final String launch_date,
                            final String posterLink, final String about,
                            final String number_of_seasons, final String episodes_per_season,
                            final String token,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener)
    {
//        {
//            "serie": {
//            "title": "Hello My Twenties",
//                    "launch_date": "22/08/2016",
//                    "about": "A slice-of-life story about five college students who connect over the growing pains in their youth.",
//                    "number_of_seasons": 2,
//                    "episodes_per_season": 12
//             }
//        }
        String url = ClientFactory.HOST + "/api/series/";

        // POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("token", token);
                return params;
            }
            @Override
            public byte[] getBody()
            {

                String body = "{\"serie\":{";

                body = body + "\"title\":\""+ title + "\",";
                body = body + "\"launch_date\":\""+ launch_date + "\",";
                body = body + "\"poster_link\":\""+ posterLink + "\",";
                body = body + "\"about\":\""+ about + "\",";
                body = body + "\"number_of_seasons\":" + number_of_seasons + ",";
                body = body + "\"episodes_per_season\":" + episodes_per_season + "";

                body = body + "}}";
                return body.getBytes();
            }
        };

        this.queue.add(stringRequest);
    }

    public void updateSerie(Map<String, String> params, String id,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener)
    {
//        this.requestSeries(params, Request.Method.PUT, id, listener, errorListener);
    }

    public void getSeries(String token,
                          Response.Listener<String> listener,
                          Response.ErrorListener errorListener)
    {
        this.requestSeries(new HashMap<String, String>(), Request.Method.GET, "", token,
                listener, errorListener);
    }

    public void getSerie(String id, String token, Response.Listener<String> listener,
                          Response.ErrorListener errorListener)
    {
        this.requestSeries(new HashMap<String, String>(), Request.Method.GET, id, token,
                listener, errorListener);
    }
}
