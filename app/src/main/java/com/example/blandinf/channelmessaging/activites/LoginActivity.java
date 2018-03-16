package com.example.blandinf.channelmessaging.activites;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.blandinf.channelmessaging.fragments.ChannelListFragment;
import com.example.blandinf.channelmessaging.response.AuthenticationResponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.example.blandinf.channelmessaging.R;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText mail;
    private EditText password;
    private TextView _textView;
    private ImageView mIvLogo;
    private Button validateBtn;
    private Gson gson = new Gson();
    public static final String PREFS_NAME = "MyPrefsFile";
    private Handler mHandlerTada;
    private AVLoadingIndicatorView _avi;
    private static final String[] explainStringArray = {
            "Connecte toi pour chatter avec tes amis",
            "rgegergergerge",
            "ergegeverv",
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mail = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.password);
        validateBtn = (Button) findViewById(R.id.validate);
        mIvLogo = (ImageView) findViewById(R.id.mIvLogo);
        _avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        _textView = (TextView) findViewById(R.id.textView);

        AnimationDrawable anim = (AnimationDrawable) findViewById(R.id.myLayout).getBackground();

        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);

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
                            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mIvLogo, "logo").toBundle());
                        } else {
                            Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myLayout), "Error", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                            validateBtn.startAnimation(animFadeOut);
                            _avi.show();
                            _textView.setText(explainStringArray[new Random().nextInt(explainStringArray.length)]);
                            animFadeOut.setAnimationListener(new Animation.AnimationListener(){
                                @Override
                                public void onAnimationStart(Animation arg0) {
                                }
                                @Override
                                public void onAnimationRepeat(Animation arg0) {
                                }
                                @Override
                                public void onAnimationEnd(Animation arg0) {
                                    _avi.hide();
                                }
                            });
                        }

                    }

                    @Override
                    public void onDownloadError(String error) {
                        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
                httpPostHandler.execute(postRequest);
            }
        });


        mHandlerTada = new Handler(); // android.os.handler
        final long mShortDelay = 4000; //milliseconds

        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(1)
                        .playOn(findViewById(R.id.mIvLogo));
                mHandlerTada.postDelayed(this,mShortDelay);
            }
        }, mShortDelay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AnimationDrawable anim = (AnimationDrawable) findViewById(R.id.myLayout).getBackground();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnimationDrawable anim = (AnimationDrawable) findViewById(R.id.myLayout).getBackground();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
}
