package com.example.blandinf.channelmessaging.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.blandinf.channelmessaging.model.User;

import java.util.UUID;

/**
 * Created by blandinf on 05/02/2018.
 */
public class UserDataSource {
    private SQLiteDatabase database;
    private FriendsDB dbHelper;

    public UserDataSource(Context context){
        dbHelper = new FriendsDB(context);
    }

    public void open() throws SQLiteException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertUser(String username, String imageURL) {
        UUID newID = UUID.randomUUID();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendsDB.KEY_USERNAME,username);
        contentValues.put(FriendsDB.KEY_IMAGE_URL,imageURL);
        contentValues.put(FriendsDB.KEY_USER_ID, newID.toString());
        database.insert(FriendsDB.FRIENDS_TABLE_NAME,null,contentValues);
    }
}
