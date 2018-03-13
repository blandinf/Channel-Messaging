package com.example.blandinf.channelmessaging.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blandinf.channelmessaging.response.AuthenticationResponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.example.blandinf.channelmessaging.R;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText mail;
    private EditText password;
    private Button validateBtn;
    private Gson gson = new Gson();
    public static final String PREFS_NAME = "MyPrefsFile";

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mail = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.password);
        validateBtn = (Button) findViewById(R.id.validate);

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("username", mail.getText().toString());
                hm.put("password", password.getText().toString());
                PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=connect", hm);
                HttpPostHandler httpPostHandler = new HttpPostHandler();
                httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete(String downloadedContent) {
                        Toast.makeText(getBaseContext(), downloadedContent, Toast.LENGTH_SHORT).show();
                        AuthenticationResponse login = gson.fromJson(downloadedContent, AuthenticationResponse.class);
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("access", login.getAccesstoken());
                        editor.commit();
                        if (login.getResponse().equals("Ok")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadError(String error) {
                        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
                httpPostHandler.execute(postRequest);
            }
        });
    }
}
