package com.hnka.csd;

import android.content.Intent;
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
import com.hnka.csd.client.ClientLogin;
import com.hnka.csd.client.ClientProfile;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void CreateProfile() {
        ClientProfile clientProfile = ClientFactory.getClientProfileInstance(getApplicationContext());

        boolean isCreatingProfile = true;

        String userName = ((EditText)findViewById(R.id.ProfileUsername)).getText().toString();
        String birthday = ((EditText)findViewById(R.id.ProfileBirthday)).getText().toString();
        String bios = ((EditText)findViewById(R.id.ProfileBios)).getText().toString();

        clientProfile.createProfile(userName, birthday, "", bios,
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

    private void SendRegister(View v) {
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
                                CreateProfile();
                            }
                        },
                        errorLoginCallback);
            }
        }, errorLoginCallback);
    }

    private void ProfileCreateWithSuccess() {
        Toast.makeText(getApplicationContext(), "Welcome to CSD!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
