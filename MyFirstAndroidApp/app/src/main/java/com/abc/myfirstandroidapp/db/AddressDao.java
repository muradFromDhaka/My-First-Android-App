package com.abc.myfirstandroidapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abc.myfirstandroidapp.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    private DatabaseHelper dbHelper;

    public AddressDao(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public Address insertAddress(Address address){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", address.getName());
        cv.put("city", address.getCity());
        cv.put("po", address.getPo());

        Long id = db.insert(dbHelper.TABLE_ADDRESS,null , cv);

        if(id != -1){
            address.setId(id);
            return address;
        }else {
            return null;
        }
    }


    public int updateAddress(Long id, Address address){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", address.getName());
        cv.put("city", address.getCity());
        cv.put("po", address.getPo());
        String[] arg = new String[]{String.valueOf(id)};

        return db.update(dbHelper.TABLE_ADDRESS, cv,"id=?", arg);
    }

    public List<Address> getAllAddress(){
        List<Address> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + dbHelper.TABLE_ADDRESS, null);
         Address ad = null;
        if(c.moveToFirst()){
            do {
                ad = new com.abc.myfirstandroidapp.entity.Address(
                        c.getString(1),
                        c.getString(2),
                        c.getString(3)
                );
                ad.setId(c.getLong(0));
                list.add(ad);
            }while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public Address getAddressId(Long id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_ADDRESS + " WHERE id=?", new String[]{String.valueOf(id)});

        Address ad = null;
        if(c != null){
            try {
                if(c.moveToFirst()){
                    ad = new Address(
                            c.getString(1),
                            c.getString(2),
                            c.getString(3)
                    );
                    ad.setId(c.getLong(0));
                }
            } finally {
                c.close();
            }
        }
        return ad;
    }

    public void deleteAddress(Long id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_ADDRESS , "id=?", new String[]{String.valueOf(id)});
    }

}
