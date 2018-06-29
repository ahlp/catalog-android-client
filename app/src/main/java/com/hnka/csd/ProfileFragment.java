package com.hnka.csd;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hnka.csd.client.ClientFactory;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private HomeCustomAdapter adapter;

    public ProfileFragment() {};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new HomeCustomAdapter(this.getContext());
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = ClientFactory.HOST + "/api/profiles";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonParser parser = new JsonParser();
                        JsonObject rootObj = parser.parse(response).getAsJsonObject().getAsJsonObject("profile");

                        JsonArray later = rootObj.getAsJsonArray("later");
                        JsonArray finished = rootObj.getAsJsonArray("finished");

                        adapter.addSectionHeaderItem(new HomeObject("Ver Depois"));

                        for(int i=0; i < later.size(); i++) {
                            JsonElement viewer = later.get(i);
                            String title = viewer.getAsJsonObject().getAsJsonObject("serie").get("title").getAsString();
                            String subtitle = viewer.getAsJsonObject().get("status").getAsString();
                            adapter.addItem(new HomeObject(title, subtitle, "https://source.unsplash.com/300x300/?movies"));
                        }

                        adapter.addSectionHeaderItem(new HomeObject("Finalizadas"));

                        for(int j=0; j < finished.size(); j++) {
                            JsonElement viewer = finished.get(j);
                            String title = viewer.getAsJsonObject().getAsJsonObject("serie").get("title").getAsString();
                            String subtitle = viewer.getAsJsonObject().get("status").getAsString();
                            adapter.addItem(new HomeObject(title, subtitle, "https://source.unsplash.com/300x300/?movies"));
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
                params.put("ID", "1");

                return params;
            }
        };

        queue.add(stringRequest);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.profile_header, getListView(),false);
        getListView().addHeaderView(header);
        getListView().setOnItemClickListener(this);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
