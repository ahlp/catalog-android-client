package com.hnka.csd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnka.csd.client.ClientFactory;
import com.hnka.csd.client.ClientSerie;

import java.util.List;

public class ExploreFragment extends Fragment {
    public ExploreFragment() {};

    public EditText inputText;
    public List<Serie> series;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getSeries();
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    public void getSeries() {
        ClientSerie clientSerie = ClientFactory.getClientSerieInstance(this.getActivity().getApplicationContext());

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("csd", Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_pref_key), "");

        clientSerie.getSeries(token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO - salvar séries
                String verResultado = response;
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
