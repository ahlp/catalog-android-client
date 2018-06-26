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

    public void CreateUpdateProfile(View v) {
        ClientProfile clientProfile = ClientFactory.getClientProfileInstance();
        ClientLogin clientLogin = ClientFactory.getClientLoginInstance();

        boolean isCreatingProfile = true;

        String userName = ((EditText)findViewById(R.id.ProfileUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.ProfilePassword)).getText().toString();

        String birthday = ((EditText)findViewById(R.id.ProfileBirthday)).getText().toString();
        String bios = ((EditText)findViewById(R.id.ProfileBios)).getText().toString();

        if (isCreatingProfile){
            // TODO: adjust requests
            clientLogin.register(userName, password);
            clientLogin.login(userName, password);
            clientProfile.createProfile(userName, birthday, "",bios);
        } else {
            clientProfile.updateProfile(userName, birthday, "",bios);
        }
    }

    private void ProfileCreateWithSuccess() {
        Toast.makeText(getApplicationContext(), "Welcome to CSD!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void ProfileUpdatedWithSuccess() {
        Toast.makeText(getApplicationContext(), "Profile Update with success", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
