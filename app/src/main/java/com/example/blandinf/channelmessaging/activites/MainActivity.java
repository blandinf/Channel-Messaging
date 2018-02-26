package com.example.blandinf.channelmessaging.activites;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.fragments.FragmentA;
import com.example.blandinf.channelmessaging.fragments.FragmentB;

/**
 * Created by blandinf on 26/02/2018.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_fragments);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentA fragA = (FragmentA)getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        FragmentB fragB = (FragmentB)getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        if(fragB == null|| !fragB.isInLayout()){
            Intent i = new Intent(getApplicationContext(),DetailActivity.class);
            startActivity(i);
        }
    }
}
