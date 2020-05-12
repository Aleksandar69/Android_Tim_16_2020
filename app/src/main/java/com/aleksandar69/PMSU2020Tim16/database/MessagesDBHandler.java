package com.aleksandar69.PMSU2020Tim16.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aleksandar69.PMSU2020Tim16.database.provider.AccountsContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.AttachmentsContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.MessagesContentProvider;
import com.aleksandar69.PMSU2020Tim16.models.Account;
import com.aleksandar69.PMSU2020Tim16.models.Attachment;
import com.aleksandar69.PMSU2020Tim16.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 86;
    public static final String DATABASE_NAME = "EMAILDB";


    //emails

    public static final String TABLE_MESSAGES = "EMAILS";

    public static final String COLUMN_ID_EMAILS = "_id";
    private static final String COLUMN_FROM = "messagefrom";
    private static final String COLUMN_TO = "messageto";
    private static final String COLUMN_CC = "cc";
    private static final String COLUMN_BCC = "bcc";
    private static final String COLUMN_DATETIME = "datetime";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_ACCOUNTS_FK = "accounts_id";

    //accounts


    public static final String TABLE_ACCOUNTS = "ACCOUNTS";

    public static final String COLUMN_ID_ACCOUNTS = "_id";
    private static final String COLUMN_SMTPADDRESS = "smptadress";
    private static final String COLUMN_PORT = "port";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DISPLAYNAME = "displayname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ATTACH_ID = "attachment";

    public static final String TABLE_ATTACHMENTS = "ATTACHMENTS";

    public static final String COLUMN_ID_ATTACHMENTS = "_id";
    private static final String COLUMN_ATTACH_CONTENT = "content";
    public static final String COLUMN_FILENAME = "filename";

    private ContentResolver myContentResolver;

    private static String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES +
            "(" + COLUMN_ID_EMAILS + " INTEGER PRIMARY KEY, " +
            COLUMN_FROM + " TEXT, " + COLUMN_TO + " TEXT, " + COLUMN_CC + " TEXT, " +
            COLUMN_BCC + " TEXT, " + COLUMN_SUBJECT + " TEXT, " +
            COLUMN_CONTENT + " TEXT, " + COLUMN_DATETIME + " TEXT, " +
            COLUMN_ACCOUNTS_FK + " INTEGER, " +
            COLUMN_ATTACH_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_ACCOUNTS_FK + ") REFERENCES ACCOUNTS(_id), "
            + "FOREIGN KEY(" + COLUMN_ATTACH_ID + ") REFERENCES ATTACHMENTS(_id)" + ")";

    private static String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS +
            "(" + COLUMN_ID_ACCOUNTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SMTPADDRESS + " TEXT, " + COLUMN_PORT + " TEXT, " +
            COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " +
            COLUMN_DISPLAYNAME + " TEXT, " + COLUMN_EMAIL + " TEXT" + ")";

    private static String CREATE_ATTACHMENT_TABLE = "CREATE TABLE " + TABLE_ATTACHMENTS +
            "(" + COLUMN_ID_ATTACHMENTS + " INTEGER PRIMARY KEY, " +
            COLUMN_ATTACH_CONTENT + " TEXT, " + COLUMN_FILENAME + " TEXT" + ")";


    public MessagesDBHandler(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        db.execSQL(CREATE_ATTACHMENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTACHMENTS);
        onCreate(db);

    }

    /////////////////////////// MESSAGES /////////////////////////////////

    public void addMessage(Message message) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_EMAILS,message.get_id());
        values.put(COLUMN_FROM, message.getFrom());
        values.put(COLUMN_TO, message.getTo());
        values.put(COLUMN_CC, message.getCc());
        values.put(COLUMN_BCC, message.getBcc());
        values.put(COLUMN_DATETIME, message.getDateTime());
        values.put(COLUMN_SUBJECT, message.getSubject());
        values.put(COLUMN_CONTENT, message.getContent());
        values.put(COLUMN_ACCOUNTS_FK, message.getLogged_user_id());
        values.put(COLUMN_ATTACH_ID, message.getAttachmentId());

        myContentResolver.insert(MessagesContentProvider.CONTENT_URI, values);
    }

    public Message findMessage(int messageId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ATTACH_ID};
        String selection = "_id = \"" + messageId + "\"";

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
            message.setDateTime(cursor.getString(7));
            message.setAttachmentId(Integer.parseInt(cursor.getString(8)));

            cursor.close();
        } else {
            message = null;
        }
        return message;
    }

    public void dropTableMessages(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
    }

    public Cursor getAllMessages(int userId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ATTACH_ID};


        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection,
                COLUMN_ACCOUNTS_FK + "=" + userId, null, null);

        return cursor;
    }

    public Cursor getAllMessages2() {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ATTACH_ID};


        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection,
                null, null, null);

        return cursor;
    }



    public List<Message> queryAllMessages() {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ATTACH_ID};
        String selection = null;

        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection, selection, null, null);

        List<Message> messages = new ArrayList<>();

/*        Message[] messages = new Message[cursor.getCount()];
        int i = 0;*/

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = new Message();
            message.set_id(Integer.parseInt(cursor.getString(0)));
            message.setFrom(cursor.getString(1));
            message.setTo(cursor.getString(2));
            message.setCc(cursor.getString(3));
            message.setBcc(cursor.getString(4));
            message.setSubject(cursor.getString(5));
            message.setContent(cursor.getString(6));
            message.setDateTime(cursor.getString(7));
            message.setAttachmentId(Integer.parseInt(cursor.getString(8)));

            messages.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return messages;

    }

    public boolean deleteMessage(int id) {

        boolean result = false;

        //  String selection = "id = " + "\"" + id + "\"";

        int rowsDeleted = myContentResolver.delete(MessagesContentProvider.CONTENT_URI, COLUMN_ID_EMAILS + " = " + id, null);

        if (rowsDeleted > 0)
            result = true;

        return result;


    }


    /////////////////////////// ACCOUNTS ////////////////////////////


    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SMTPADDRESS, account.getSmtpAddress());
        values.put(COLUMN_PORT, account.getPort());
        values.put(COLUMN_USERNAME, account.getUsername());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_DISPLAYNAME, account.getDisplayName());
        values.put(COLUMN_EMAIL, account.geteMail());

        myContentResolver.insert(AccountsContentProvider.CONTENT_URI, values);
    }

    public List<Account> queryAccounts() {

        String[] projection = {COLUMN_ID_ACCOUNTS, COLUMN_SMTPADDRESS, COLUMN_PORT, COLUMN_USERNAME,
                COLUMN_PASSWORD, COLUMN_DISPLAYNAME, COLUMN_EMAIL};

        Cursor cursor = myContentResolver.query(AccountsContentProvider.CONTENT_URI, projection, null, null, null);

        List<Account> accounts = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = new Account();
            account.set_id(Integer.parseInt(cursor.getString(0)));
            account.setSmtpAddress(cursor.getString(1));
            account.setPort(cursor.getString(2));
            account.setUsername(cursor.getString(3));
            account.setPassword(cursor.getString(4));
            account.setDisplayName(cursor.getString(5));
            account.seteMail(cursor.getString(6));

            accounts.add(account);
            cursor.moveToNext();
        }
        cursor.close();
        return accounts;
    }


    public boolean deleteAccount(String userName) {
        boolean result = false;

        String selection = "subject = ?";
        String[] selectionArgs = {userName};

        int rowsDeleted = myContentResolver.delete(AccountsContentProvider.CONTENT_URI, selection, selectionArgs);

        if (rowsDeleted > 0)
            result = true;

        return result;
    }

    public Account findAccount(String username, String password) {
        String[] projection = {COLUMN_ID_ACCOUNTS, COLUMN_SMTPADDRESS, COLUMN_PORT, COLUMN_USERNAME,
                COLUMN_PASSWORD, COLUMN_DISPLAYNAME, COLUMN_EMAIL};

        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = myContentResolver.query(AccountsContentProvider.CONTENT_URI, projection, selection, selectionArgs, null);

        Account account = new Account();

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            account = new Account();
            account.set_id(Integer.parseInt(cursor.getString(0)));
            account.setSmtpAddress(cursor.getString(1));
            account.setPort(cursor.getString(2));
            account.setUsername(cursor.getString(3));
            account.setPassword(cursor.getString(4));
            account.setDisplayName(cursor.getString(5));
            account.seteMail(cursor.getString(6));
            cursor.close();
        } else {
            account = null;
        }
        return account;

    }

    //ATTACHMENTS

    public void addAttachment(Attachment attachment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTACH_CONTENT, attachment.getContent());
        values.put(COLUMN_FILENAME, attachment.getFileName());

        myContentResolver.insert(AttachmentsContentProvider.CONTENT_URI, values);
    }

    public Attachment queryAttachForMessage(int messageAttId) {
        String[] projection = {COLUMN_ID_ATTACHMENTS, COLUMN_ATTACH_CONTENT, COLUMN_FILENAME};

        Cursor cursor = myContentResolver.query(AttachmentsContentProvider.CONTENT_URI,
                projection, COLUMN_ID_ATTACHMENTS + "=" + messageAttId, null, null);

        Attachment attachment;

        if (cursor.moveToFirst()) {
            attachment = new Attachment();
            attachment.set_id(Integer.parseInt(cursor.getString(0)));
            attachment.setContent(cursor.getString(1));
            attachment.setFileName(cursor.getString(2));
            cursor.close();
        }
        else{
            attachment = null;
        }
        return attachment;

    }

    public Attachment queryAttachbyName(String messageName) {
        String[] projection = {COLUMN_ID_ATTACHMENTS, COLUMN_ATTACH_CONTENT, COLUMN_FILENAME};

        String selection = COLUMN_FILENAME + " = ? ";

        String[] selectionArgs = {messageName};

        Cursor cursor = myContentResolver.query(AttachmentsContentProvider.CONTENT_URI,
                projection, selection,selectionArgs,null);

        Attachment attachment;


        if (cursor.moveToFirst()) {
            attachment = new Attachment();
            attachment.set_id(Integer.parseInt(cursor.getString(0)));
            attachment.setContent(cursor.getString(1));
            attachment.setFileName(cursor.getString(2));
            cursor.close();
        }
        else{
            attachment = null;
        }
        return attachment;


    }

}
