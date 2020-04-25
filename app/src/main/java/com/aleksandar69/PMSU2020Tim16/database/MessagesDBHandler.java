package com.aleksandar69.PMSU2020Tim16.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aleksandar69.PMSU2020Tim16.database.provider.MessagesContentProvider;
import com.aleksandar69.PMSU2020Tim16.models.Message;

import java.sql.SQLData;

public class MessagesDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "MessagesDB";
    public static final String TABLE_MESSAGES = "Messages";

    public static final String COLUMN_ID = "_id";
    private static final String COLUMN_FROM = "messagefrom";
    private static final String COLUMN_TO = "messageto";
    private static final String COLUMN_CC = "cc";
    private static final String COLUMN_BCC = "bcc";
    //   private static final String COLUMN_DATETIME = "dateTime";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_CONTENT = "content";

    private ContentResolver myContentResolver;


    public MessagesDBHandler(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FROM + " TEXT, " + COLUMN_TO + " TEXT, " + COLUMN_CC + " TEXT, " +
                COLUMN_BCC + " TEXT, " + /*COLUMN_DATETIME + " DATETIME," +*/ COLUMN_SUBJECT + " TEXT, " +
                COLUMN_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_MESSAGES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);

    }

    public void addMessage(Message message) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM, message.getFrom());
        values.put(COLUMN_TO, message.getTo());
        values.put(COLUMN_CC, message.getCc());
        values.put(COLUMN_BCC, message.getBcc());
        //values.put(COLUMN_DATETETIME, message.getDateTime());
        values.put(COLUMN_SUBJECT, message.getSubject());
        values.put(COLUMN_CONTENT, message.getContent());

        myContentResolver.insert(MessagesContentProvider.CONTENT_URI, values);
    }

    public Message findMessage(String messageSubject) {

      /*  String query = "Select * FROM " + TABLE_MESSAGES + " WHERE " + COLUMN_SUBJECT
                + " = \"" + messageSubject + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Message message = new Message();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            message.set_id(Integer.parseInt(cursor.getString(0)));
            message.setFrom(cursor.getString(1));
            message.setTo(cursor.getString(2));
            message.setCc(cursor.getString(3));
            message.setBcc(cursor.getString(4));
            message.setSubject(cursor.getString(5));
            message.setContent(cursor.getString(6));
            cursor.close();
        } else {
            message = null;
        }
        db.close();
        return message;*/

        String[] projection = {COLUMN_ID, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT};
        String selection = "subject = \"" + messageSubject + "\"";

        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection, selection, null, null);

        Message message = new Message();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            message.set_id(Integer.parseInt(cursor.getString(0)));
            message.setFrom(cursor.getString(1));
            message.setTo(cursor.getString(2));
            message.setCc(cursor.getString(3));
            message.setBcc(cursor.getString(4));
            message.setSubject(cursor.getString(5));
            message.setContent(cursor.getString(6));
            cursor.close();
        } else {
            message = null;
        }
        return message;
    }

    public boolean deleteMessage(String messageSubject) {

        boolean result = false;

/*        String query = "Select * FROM " + TABLE_MESSAGES + " WHERE " + COLUMN_SUBJECT
                + " = \"" + messageSubject + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Message message = new Message();

        if(cursor.moveToFirst()){
            message.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_MESSAGES,COLUMN_ID + " = ?",new String[]{String.valueOf(message.get_id())} );
            cursor.close();
            result = true;
        }
        db.close();
        return result;*/

        String selection = "subject = " + "\"" + messageSubject + "\"";

        int rowsDeleted = myContentResolver.delete(MessagesContentProvider.CONTENT_URI, selection, null);

        if (rowsDeleted > 0)
            result = true;

        return result;


    }
}
