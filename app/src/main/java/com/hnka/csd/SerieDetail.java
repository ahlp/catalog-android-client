package com.hnka.csd;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SerieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getApplicationContext().getSharedPreferences("csd", Context.MODE_PRIVATE);
        final String token = sharedPref.getString("Token", "");

        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url = ClientFactory.HOST + "/api/series/" + "1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonParser parser = new JsonParser();
                        JsonObject rootObj = parser.parse(response).getAsJsonObject().getAsJsonObject("serie");

                        String posterUrl = rootObj.get("poster_link").getAsString();
                        String title = rootObj.get("title").getAsString();
                        String launch = rootObj.get("launch_date").getAsString();
                        String about = rootObj.get("about").getAsString();
                        String seasonsCount  = rootObj.get("seasons_count").getAsString();
                        String episodesCount  = rootObj.get("episodes_count").getAsString();

                        String serieDetail = seasonsCount + " temporada(s), " + episodesCount + " episódios";

                        ImageView poster = (ImageView) findViewById(R.id.posterView);
                        TextView titleView = (TextView) findViewById(R.id.serieTitle);
                        TextView launchView = (TextView) findViewById(R.id.serieLaunchDate);
                        TextView detailView = (TextView) findViewById(R.id.serieSeasons);
                        TextView aboutView = (TextView) findViewById(R.id.serieAbout);

                        titleView.setText(title);
                        launchView.setText("Data de Lançamento: " + launch);
                        aboutView.setText(about);
                        detailView.setText(serieDetail);

                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;

                        float density = getApplicationContext().getResources().getDisplayMetrics().density;
                        float pxValue = 180 * density;

                        Picasso.get().load(posterUrl).resize(width,Math.round(pxValue)).centerCrop().into(poster);

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
    }
}
