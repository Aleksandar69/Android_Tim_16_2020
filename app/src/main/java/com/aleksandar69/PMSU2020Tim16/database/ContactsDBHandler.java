package com.aleksandar69.PMSU2020Tim16.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aleksandar69.PMSU2020Tim16.R;

public class ContactsDBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contactsDB";
    public static final int DATABASE_VERSION = 1;

     public ContactsDBHandler(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE CONTACT (_id INTEGER PRIMARY KEY AUTOINCREMENT , "
                 + "FIRST TEXT , "
                 + "LAST TEXT , "
                 + "DISPLAY TEXT, "
                 + "EMAIL TEXT ,"
                 + "IMAGE_RESOURCE_ID INTEGER);");
        insertContact(db, "Elena", "Krunic", "Elena Krunic", "elenakrunic@gmail.com", R.mipmap.ic_launcher_round);
        insertContact(db, "Vincent", "Andolini", "Vincent Andolini", "vincent@gmail.com", R.mipmap.ic_launcher_round);
        insertContact(db, "Mico", "Micic", "Mico Micic", "mico@gmail.com",  R.mipmap.ic_launcher_round);

    }

    private static void insertContact(SQLiteDatabase db, String first, String last, String display, String email, int resourceId) {
        ContentValues contactValues = new ContentValues();
        contactValues.put("FIRST", first);
        contactValues.put("LAST", last);
        contactValues.put("DISPLAY", display);
        contactValues.put("EMAIL", email);
        contactValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("CONTACT", null, contactValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
