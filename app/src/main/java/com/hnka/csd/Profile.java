package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnka.csd.client.ClientFactory;
import com.hnka.csd.client.ClientLogin;
import com.hnka.csd.client.ClientProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void SendRegister(View v) {

        final String email = ((EditText)findViewById(R.id.ProfileUsername)).getText().toString();
        final String password = ((EditText)findViewById(R.id.ProfilePassword)).getText().toString();
        final ClientLogin clientLogin = ClientFactory.getClientLoginInstance(getApplicationContext());
        final String name = ((EditText)findViewById(R.id.ProfileName)).getText().toString();
        final String birthday = ((EditText)findViewById(R.id.ProfileBirthday)).getText().toString();
        final String about = ((EditText)findViewById(R.id.ProfileBios)).getText().toString();
        final String avatarLink = ((EditText)findViewById(R.id.profileAvatarLink)).getText().toString();

        clientLogin.register(email, password, name, birthday, avatarLink, about,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                clientLogin.login(email, password,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                try {
                                    JSONObject responseJson = new JSONObject(response);

                                    String token = responseJson.getString("token");

                                    editor.putString(getString(R.string.token_pref_key), token);
                                    editor.commit();
                                } catch (JSONException e) {
                                    Log.e("Error", "Cannot save token");
                                    Log.e("Error", e.getMessage());
                                    Toast.makeText(getApplicationContext(),
                                            "Cannot login, wrong version", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                ProfileCreateWithSuccess();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),
                                        "Register created but cannot login",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Cannot create register",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void ProfileCreateWithSuccess() {
        Toast.makeText(getApplicationContext(), "Welcome to CSD!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
