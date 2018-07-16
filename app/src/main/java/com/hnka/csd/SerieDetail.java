package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hnka.csd.client.ClientFactory;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SerieDetail extends AppCompatActivity {

    Button watchLaterButton;
    Button watchedButton;
    Button watchingButton;

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

        Intent myIntent = getIntent();
        final String idValue = myIntent.getStringExtra("id");

        SharedPreferences sharedPref = this.getApplicationContext().getSharedPreferences("csd", Context.MODE_PRIVATE);
        final String token = sharedPref.getString("Token", "");

        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        String url = ClientFactory.HOST + "/api/series/" + idValue;
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
                        String viewerStatus = "";

                        if (rootObj.has("viewer_status")) {
                            viewerStatus = rootObj.get("viewer_status").getAsString();
                        }

                        String serieDetail = seasonsCount + " temporada(s), " + episodesCount + " episódios";

                        ImageView poster = (ImageView) findViewById(R.id.posterView);
                        TextView titleView = (TextView) findViewById(R.id.serieTitle);
                        TextView launchView = (TextView) findViewById(R.id.serieLaunchDate);
                        TextView detailView = (TextView) findViewById(R.id.serieSeasons);
                        TextView aboutView = (TextView) findViewById(R.id.serieAbout);

                        watchLaterButton = (Button) findViewById(R.id.watchLaterButton);
                        watchLaterButton.setBackgroundResource(R.drawable.button_not_selected);
                        watchLaterButton.setOnClickListener(new Button.OnClickListener() {
                            public void onClick(View v) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                String url = ClientFactory.HOST + "/api/viewers";

                                JSONObject mainJson  = new JSONObject();
                                JSONObject viewerJson  = new JSONObject();

                                try {
                                    viewerJson.put("serie_id", idValue);
                                    viewerJson.put("status", "watch_later");
                                    mainJson.put("viewer", viewerJson);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, mainJson, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        watchLaterButton.setBackgroundResource(R.drawable.button_selected);
                                        watchLaterButton.setTextColor(Color.WHITE);
                                        watchLaterButton.setEnabled(false);

                                        watchingButton.setEnabled(true);
                                        watchingButton.setTextColor(Color.BLACK);
                                        watchingButton.setBackgroundResource(R.drawable.button_not_selected);

                                        watchedButton.setEnabled(true);
                                        watchedButton.setTextColor(Color.BLACK);
                                        watchedButton.setBackgroundResource(R.drawable.button_not_selected);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
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
                        });

                        watchedButton = (Button) findViewById(R.id.watchedButton);
                        watchedButton.setBackgroundResource(R.drawable.button_not_selected);
                        watchedButton.setOnClickListener(new Button.OnClickListener() {
                            public void onClick(View v) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                String url = ClientFactory.HOST + "/api/viewers";

                                JSONObject mainJson  = new JSONObject();
                                JSONObject viewerJson  = new JSONObject();

                                try {
                                    viewerJson.put("serie_id", idValue);
                                    viewerJson.put("status", "watched");
                                    mainJson.put("viewer", viewerJson);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, mainJson, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        watchedButton.setBackgroundResource(R.drawable.button_selected);
                                        watchedButton.setTextColor(Color.WHITE);
                                        watchedButton.setEnabled(false);

                                        watchingButton.setEnabled(true);
                                        watchingButton.setTextColor(Color.BLACK);
                                        watchingButton.setBackgroundResource(R.drawable.button_not_selected);

                                        watchLaterButton.setEnabled(true);
                                        watchLaterButton.setTextColor(Color.BLACK);
                                        watchLaterButton.setBackgroundResource(R.drawable.button_not_selected);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
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
                        });

                        watchingButton = (Button) findViewById(R.id.watchingButton);
                        watchingButton.setBackgroundResource(R.drawable.button_not_selected);
                        watchingButton.setOnClickListener(new Button.OnClickListener() {
                            public void onClick(View v) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                String url = ClientFactory.HOST + "/api/viewers";

                                JSONObject mainJson  = new JSONObject();
                                JSONObject viewerJson  = new JSONObject();

                                try {
                                    viewerJson.put("serie_id", idValue);
                                    viewerJson.put("status", "watching");
                                    mainJson.put("viewer", viewerJson);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, mainJson, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        watchingButton.setBackgroundResource(R.drawable.button_selected);
                                        watchingButton.setTextColor(Color.WHITE);
                                        watchingButton.setEnabled(false);

                                        watchedButton.setEnabled(true);
                                        watchedButton.setTextColor(Color.BLACK);
                                        watchedButton.setBackgroundResource(R.drawable.button_not_selected);

                                        watchLaterButton.setEnabled(true);
                                        watchLaterButton.setTextColor(Color.BLACK);
                                        watchLaterButton.setBackgroundResource(R.drawable.button_not_selected);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
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
                        });

                        if (viewerStatus.equals("watch_later")) {
                            watchLaterButton.setBackgroundResource(R.drawable.button_selected);
                            watchLaterButton.setTextColor(Color.WHITE);
                            watchLaterButton.setEnabled(false);
                        }

                        if (viewerStatus.equals("watched")) {
                            watchedButton.setBackgroundResource(R.drawable.button_selected);
                            watchedButton.setTextColor(Color.WHITE);
                            watchedButton.setEnabled(false);
                        }

                        if (viewerStatus.equals("watching")) {
                            watchingButton.setBackgroundResource(R.drawable.button_selected);
                            watchingButton.setTextColor(Color.WHITE);
                            watchingButton.setEnabled(false);
                        }

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

    public void onShareAction(View v) {

        String title = ((TextView) findViewById(R.id.serieTitle)).getText().toString();
        String about = ((TextView) findViewById(R.id.serieAbout)).getText().toString();

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");

        String shareBody = "Olá, vi esta Serie / Drama e lembrei de você.\nO nome dela é " + title;
        shareBody = shareBody + "\n e ela é sobre: " + about;

        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "CSD infos"));
    }
}
