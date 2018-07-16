package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hnka.csd.client.ClientFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private HomeCustomAdapter adapter;

    public HomeFragment() {};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("csd", Context.MODE_PRIVATE);
        final String token = sharedPref.getString("Token", "");

        adapter = new HomeCustomAdapter(this.getContext());
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = ClientFactory.HOST + "/api/historic";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonParser parser = new JsonParser();
                        JsonObject rootObj = parser.parse(response).getAsJsonObject().getAsJsonObject("historic");

                        JsonArray recent = rootObj.getAsJsonArray("recent");
                        JsonArray watching = rootObj.getAsJsonArray("watching");

                        adapter.addSectionHeaderItem(new HomeObject("Histórico"));

                        for(int i=0; i < recent.size(); i++) {
                            JsonElement viewer = recent.get(i);
                            String title = viewer.getAsJsonObject().getAsJsonObject("serie").get("title").getAsString();
                            String subtitle = viewer.getAsJsonObject().get("status").getAsString();
                            int id = viewer.getAsJsonObject().getAsJsonObject("serie").get("id").getAsInt();
                            String poster = viewer.getAsJsonObject().getAsJsonObject("serie").get("poster_link").getAsString();
                            adapter.addItem(new HomeObject(id, title, subtitle, poster));
                        }

                        adapter.addSectionHeaderItem(new HomeObject("O que você acompanha"));

                        for(int j=0; j < watching.size(); j++) {
                            JsonElement viewer = watching.get(j);
                            String title = viewer.getAsJsonObject().getAsJsonObject("serie").get("title").getAsString();
                            String subtitle = viewer.getAsJsonObject().get("status").getAsString();
                            int id = viewer.getAsJsonObject().getAsJsonObject("serie").get("id").getAsInt();
                            String poster = viewer.getAsJsonObject().getAsJsonObject("serie").get("poster_link").getAsString();
                            adapter.addItem(new HomeObject(id, title, subtitle, poster));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", token);

                return params;
            }
        };

        queue.add(stringRequest);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        int type = adapter.getItemViewType(position);
        if (type == 0) {
            HomeObject object = adapter.getItem(position);

            Intent intent = new Intent(this.getContext(), SerieDetail.class);
            intent.putExtra("id", object.getId());
            startActivity(intent);
        }
    }
}
