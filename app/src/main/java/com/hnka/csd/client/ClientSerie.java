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

    private void requestSeries(Map<String, String> params, int method, String id,
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
        };
        this.queue.add(stringRequest);
    }

    public void createSerie(Map<String, String> params, Response.Listener<String> listener,
                            Response.ErrorListener errorListener)
    {
        this.requestSeries(params, Request.Method.POST, "", listener, errorListener);
    }

    public void updateSerie(Map<String, String> params, String id,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener)
    {
        this.requestSeries(params, Request.Method.PUT, id, listener, errorListener);
    }

    public void getSeries(Response.Listener<String> listener,
                            Response.ErrorListener errorListener)
    {
        this.requestSeries(new HashMap<String, String>(), Request.Method.GET, "",
                listener, errorListener);
    }

    public void getSerie(String id, Response.Listener<String> listener,
                          Response.ErrorListener errorListener)
    {
        this.requestSeries(new HashMap<String, String>(), Request.Method.GET, id,
                listener, errorListener);
    }
}
