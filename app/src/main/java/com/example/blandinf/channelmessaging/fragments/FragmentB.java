package com.example.blandinf.channelmessaging.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blandinf.channelmessaging.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by blandinf on 26/02/2018.
 */
public class FragmentB extends Fragment {
    private ListView messages;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b,container);
        messages = (ListView) v.findViewById(R.id.messages);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void fillListView(String listItem){

    }
}