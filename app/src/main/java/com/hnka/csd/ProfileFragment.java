package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private HomeCustomAdapter adapter;
    private ViewGroup header;

    public ProfileFragment() {};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new HomeCustomAdapter(this.getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        header = (ViewGroup)inflater.inflate(R.layout.profile_header, getListView(),false);
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = ClientFactory.HOST + "/api/profile";

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("csd", Context.MODE_PRIVATE);
        final String token = sharedPref.getString(getString(R.string.token_pref_key), "");

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
                            int id = viewer.getAsJsonObject().getAsJsonObject("serie").get("id").getAsInt();
                            String poster = viewer.getAsJsonObject().getAsJsonObject("serie").get("poster_link").getAsString();
                            adapter.addItem(new HomeObject(id, title, subtitle, poster));
                        }

                        adapter.addSectionHeaderItem(new HomeObject("Finalizadas"));

                        for(int j=0; j < finished.size(); j++) {
                            JsonElement viewer = finished.get(j);
                            String title = viewer.getAsJsonObject().getAsJsonObject("serie").get("title").getAsString();
                            String subtitle = viewer.getAsJsonObject().get("status").getAsString();
                            int id = viewer.getAsJsonObject().getAsJsonObject("serie").get("id").getAsInt();
                            String poster = viewer.getAsJsonObject().getAsJsonObject("serie").get("poster_link").getAsString();
                            adapter.addItem(new HomeObject(id, title, subtitle, poster));
                        }

                        TextView name = (TextView) header.findViewById(R.id.user_name);
                        String objectName = rootObj.get("name").getAsString();
                        name.setText(objectName);

                        TextView mail = (TextView) header.findViewById(R.id.user_mail);
                        String about = rootObj.get("about").getAsString();
                        mail.setText(about);

                        TextView summary = (TextView) header.findViewById(R.id.user_summary);
                        String birthday = rootObj.get("birthday").getAsString();
                        summary.setText(birthday);

                        ImageView avatar = (ImageView) header.findViewById(R.id.user_avatar);
                        String link = rootObj.get("avatar_link").getAsString();
                        Picasso.get().load(link).resize(120,120).into(avatar);

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

        getListView().addHeaderView(header);
        getListView().setOnItemClickListener(this);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        int correctPosition = position - 1;

        if (correctPosition >= 0) {
            int type = adapter.getItemViewType(correctPosition);

            if (type == 0) {
                HomeObject object = adapter.getItem(correctPosition);
                Intent intent = new Intent(this.getContext(), SerieDetail.class);
                intent.putExtra("id",String.valueOf(object.getId()));
                startActivity(intent);
            }
        }

    }
}
