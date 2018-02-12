package com.example.blandinf.channelmessaging.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.blandinf.channelmessaging.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by blandinf on 05/02/2018.
 */
public class UserDataSource {
    private SQLiteDatabase database;
    private FriendsDB dbHelper;
    private String[] allColumns = { FriendsDB.KEY_USER_ID,FriendsDB.KEY_USERNAME,
            FriendsDB.KEY_IMAGE_URL };

    public UserDataSource(Context context){
        dbHelper = new FriendsDB(context);
    }

    public void open() throws SQLiteException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public User insertUser(String username, String imageURL) {
        UUID newID = UUID.randomUUID();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FriendsDB.KEY_USER_ID, newID.toString());
        contentValues.put(FriendsDB.KEY_USERNAME,username);
        contentValues.put(FriendsDB.KEY_IMAGE_URL,imageURL);
        database.insert(FriendsDB.FRIENDS_TABLE_NAME,"",contentValues);
        Cursor cursor = database.query(FriendsDB.FRIENDS_TABLE_NAME, allColumns, FriendsDB.KEY_USER_ID + " = \"" + newID.toString()+"\"", null, null, null, null);
        cursor.moveToFirst();
        User user = cursorToUser(cursor);
        cursor.close();
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        Cursor cursor = database.query(FriendsDB.FRIENDS_TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }


    private User cursorToUser(Cursor cursor) {
        User user= new User();
        String result = cursor.getString(0);
        user.setUserID(UUID.fromString(result));
        user.setUsername(cursor.getString(1));
        user.setImageURL(cursor.getString(2));
        return user;
    }
}
