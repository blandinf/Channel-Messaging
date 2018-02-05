package com.example.blandinf.channelmessaging.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by blandinf on 05/02/2018.
 */
public class FriendsDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "friends.db";
    public static final String FRIENDS_TABLE_NAME = "Friends";
    public static final String KEY_USER_ID = "userID";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_IMAGE_URL = "imageURL";

    public static final String FRIENDS_CREATE_TABLE = "CREATE TABLE " + FRIENDS_TABLE_NAME + "(" + KEY_USER_ID + " TEXT," + KEY_USERNAME + " TEXT,"
            + KEY_IMAGE_URL + " TEXT);";

    public FriendsDB(Context context){
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FRIENDS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE_NAME);
        onCreate(db);
    }
}
