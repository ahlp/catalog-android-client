package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hnka.csd.client.ClientFactory;
import com.hnka.csd.client.ClientSerie;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends ListFragment implements AdapterView.OnItemClickListener {
    public ExploreFragment() {};

    private HomeCustomAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new HomeCustomAdapter(this.getContext());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        new RecuperarSeries().execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HomeObject object = adapter.getItem(position);

        Intent intent = new Intent(this.getContext(), SerieDetail.class);
        intent.putExtra("id", String.valueOf(object.getId()));
        startActivity(intent);
    }

    private class RecuperarSeries extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            getSeries();
            return null;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity().getApplicationContext(), "recuperando series...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity().getApplicationContext(), "terminando...", Toast.LENGTH_SHORT).show();
        }

        public void getSeries() {
            ClientSerie clientSerie = ClientFactory.getClientSerieInstance(getActivity().getApplicationContext());

            SharedPreferences sharedPref = getActivity().getSharedPreferences("csd", Context.MODE_PRIVATE);
            String token = sharedPref.getString(getString(R.string.token_pref_key), "");

            clientSerie.getSeries(token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    JsonObject responseJson = new JsonParser().parse(response).getAsJsonObject();
                    JsonArray seriesElement = responseJson.getAsJsonArray("series");
                    for(int i = 0; i < seriesElement.size(); ++i){
                        JsonObject serieObject = seriesElement.get(i).getAsJsonObject();
                        Serie newSerie = new Serie(serieObject);
                        adapter.addItem(new HomeObject(Integer.parseInt(newSerie.getId()), newSerie.getTitle(), "", newSerie.getPoster_link()));
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot get series",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
