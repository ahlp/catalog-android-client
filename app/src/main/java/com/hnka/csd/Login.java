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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void onLogin(View v) {
        String userName = ((EditText)findViewById(R.id.Username)).getText().toString();
        String password = ((EditText)findViewById(R.id.Password)).getText().toString();

        // TODO: pass callback in parameters
        ClientLogin client = ClientFactory.getClientLoginInstance(getApplicationContext());
        client.login(userName, password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
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

    public void onRegister(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
