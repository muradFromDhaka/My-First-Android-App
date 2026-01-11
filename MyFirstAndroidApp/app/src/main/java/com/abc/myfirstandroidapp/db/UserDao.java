package com.abc.myfirstandroidapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abc.myfirstandroidapp.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

public  final DatabaseHelper dbHelper;

public UserDao(Context context){
    dbHelper = new DatabaseHelper(context);
}

public Long insertUser(User user){
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put("name", user.getName());
    cv.put("email", user.getEmail());
    cv.put("password", user.getPassword());
    cv.put("phone", user.getPhone());
    cv.put("gender", user.getGender());
    cv.put("dob", user.getDob());
    cv.put("country", user.getCountry());
    cv.put("skills", user.getSkills());
    cv.put("terms", user.isTermsAccepted() ? 1 : 0);

    return db.insert(dbHelper.TABLE_USER, null, cv );
}

public int updateUser(Long id, User user){
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put("name", user.getName());
    cv.put("email", user.getEmail());
    cv.put("password", user.getPassword());
    cv.put("phone", user.getPhone());
    cv.put("gender", user.getGender());
    cv.put("dob", user.getDob());
    cv.put("country", user.getCountry());
    cv.put("skills", user.getSkills());
    cv.put("terms", user.isTermsAccepted() ? 1 : 0);
    String[] arg = new String[]{String.valueOf(id)};

    return db.update(dbHelper.TABLE_USER, cv, "id=?" , arg);
}


public List<User> getAllUser(){
    List<User> list = new ArrayList<>();
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor c = db.rawQuery("select * from users", null);
    User u = null;

    if(c.moveToFirst()){
        do {
            u = new User(
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getInt(9)==1
            );
            u.setId(c.getLong(0));
            list.add(u);

        }while(c.moveToNext());
    }

    c.close();
    return list;

}


public User getUserById(Long id){
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor c = db.rawQuery("select * from users where id=?", new String[]{String.valueOf(id)});
    User u = null;
            if(c.moveToFirst()){
                u = new User(
                       c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getInt(9) ==1

                );
                u.setId(c.getLong(0));
            }

            c.close();
            return u;
}

public void deleteUser(Long id){
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    db.delete("users" , "id=?", new String[]{String.valueOf(id)});
}

}
