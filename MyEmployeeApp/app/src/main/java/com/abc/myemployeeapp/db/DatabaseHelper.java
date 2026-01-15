package com.abc.myemployeeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employee_db";
    private static final int DATABASE_VERSION = 2; // ⬅️ version bump

    public static final String TABLE_EMPLOYEES = "employees";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EMPLOYEE_TABLE =
                "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "email TEXT, " +
                        "phone TEXT, " +
                        "age INTEGER, " +
                        "salary REAL, " +
                        "active TEXT, " +
                        "joiningDate INTEGER, " +
                        "department TEXT, " +
                        "skills TEXT, " +
                        "imagePath TEXT" +
                        ")";

        db.execSQL(CREATE_EMPLOYEE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        onCreate(db);
    }
}
