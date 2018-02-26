package com.example.blandinf.channelmessaging.activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.fragments.FragmentB;

/**
 * Created by blandinf on 26/02/2018.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_b);
        FragmentB fragB = (FragmentB)getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
    }
}
