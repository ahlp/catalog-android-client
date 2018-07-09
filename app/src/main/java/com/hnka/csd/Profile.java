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

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void CreateProfile() {
        ClientProfile clientProfile = ClientFactory.getClientProfileInstance(getApplicationContext());

        boolean isCreatingProfile = true;

        String name = ((EditText)findViewById(R.id.ProfileName)).getText().toString();
        String birthday = ((EditText)findViewById(R.id.ProfileBirthday)).getText().toString();
        String bios = ((EditText)findViewById(R.id.ProfileBios)).getText().toString();
        String avatarLink = ((EditText)findViewById(R.id.profileAvatarLink)).getText().toString();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_pref_key), "");

        clientProfile.createProfile(name, birthday, avatarLink, bios, token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProfileCreateWithSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Invalid Login or Password",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void SendRegister(View v) {

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_pref_key), "");
        if (!token.equals("")) {
            CreateProfile();
            return;
        }

        final String userName = ((EditText)findViewById(R.id.ProfileUsername)).getText().toString();
        final String password = ((EditText)findViewById(R.id.ProfilePassword)).getText().toString();
        final ClientLogin clientLogin = ClientFactory.getClientLoginInstance(getApplicationContext());

        final Response.ErrorListener errorLoginCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Invalid Login or Password",
                        Toast.LENGTH_LONG).show();
            }
        };

        clientLogin.register(userName, password,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                clientLogin.login(userName, password,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.token_pref_key), response);
                                editor.commit();
                                CreateProfile();
                            }
                        },
                        errorLoginCallback);
            }
        }, errorLoginCallback);
    }

    private void ProfileCreateWithSuccess() {
        Toast.makeText(getApplicationContext(), "Welcome to CSD!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
