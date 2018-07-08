package com.hnka.csd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_pref_key), "");
        if(token != "") {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onLogin(View v) {
        String userName = ((EditText)findViewById(R.id.Username)).getText().toString();
        String password = ((EditText)findViewById(R.id.Password)).getText().toString();

        ClientLogin client = ClientFactory.getClientLoginInstance(getApplicationContext());
        client.login(userName, password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPref.edit();
                        Log.d("desespero", "The token is: " + response);
                        String token = response;
                        edit.putString(getString(R.string.token_pref_key), token);
                        edit.commit();


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("desespero", "o erro foi: "+ error.getCause());
                        Toast.makeText(getApplicationContext(), "Invalid Login or Password",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onRegister(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
