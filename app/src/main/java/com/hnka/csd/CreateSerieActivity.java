package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnka.csd.client.ClientFactory;
import com.hnka.csd.client.ClientSerie;

public class CreateSerieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_serie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void CreateSerie(View v) {
        ClientSerie clientSerie = ClientFactory.getClientSerieInstance(getApplicationContext());

        String poster = ((EditText)findViewById(R.id.CreateSeriePoster)).getText().toString();
        String title = ((EditText)findViewById(R.id.CreateSerieTitle)).getText().toString();
        String about = ((EditText)findViewById(R.id.CreateSerieAbout)).getText().toString();
        String launch = ((EditText)findViewById(R.id.CreateSerieLaunchDate)).getText().toString();
        String seasons = ((EditText)findViewById(R.id.createSerieSeasons)).getText().toString();
        String episodes = ((EditText)findViewById(R.id.CreateSerieEpisodes)).getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("csd", Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_pref_key), "");

        clientSerie.createSerie(title, launch, poster, about, seasons, episodes, token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),
                                "The serie has been created.\nThanks for the support!",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "An error has occurred",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void UploadImage(View v) {

    }
}
