package com.hnka.csd.client;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ClientHome {

    private RequestQueue queue;

    protected ClientHome(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void getFeed() {
        String url = ClientFactory.HOST + "/api/historic";

    }

}
