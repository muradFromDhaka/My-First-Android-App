package com.abc.myfirstandroidapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final  String db_name = "app_db";
    public static  final  int db_version = 1;
    public static  final  String TABLE_USER = "users";

    public DatabaseHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_NAME_USER =
                "CREATE TABLE " + TABLE_USER + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                        "name TEXT," +
                        "email TEXT," +
                        "password TEXT," +
                        "phone TEXT," +
                        "gender TEXT," +
                        "dob TEXT," +
                        "country TEXT," +
                        "skills TEXT," +
                        "terms INTEGER" +
                         ")";

        db.execSQL(TABLE_NAME_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
