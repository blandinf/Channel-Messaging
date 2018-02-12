package com.example.blandinf.channelmessaging.activites;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.adapter.MyArrayAdapter;
import com.example.blandinf.channelmessaging.adapter.MyFriendsListAdapter;
import com.example.blandinf.channelmessaging.databases.FriendsDB;
import com.example.blandinf.channelmessaging.databases.UserDataSource;
import com.example.blandinf.channelmessaging.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blandinf on 05/02/2018.
 */
public class FriendsListActivity extends Activity{

    private SQLiteOpenHelper onHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private GridView _gridView;
    private List<User> users;
    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list_activity);

        _gridView = (GridView) findViewById(R.id.gridView);


        userDataSource = new UserDataSource(getApplicationContext());
        userDataSource.open();
        users = userDataSource.getAllUsers();

        _gridView.setAdapter(new MyFriendsListAdapter(getApplicationContext(),(ArrayList) users));

            }
}
