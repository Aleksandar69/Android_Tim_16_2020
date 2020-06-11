package com.aleksandar69.PMSU2020Tim16.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aleksandar69.PMSU2020Tim16.Data;
import com.aleksandar69.PMSU2020Tim16.database.provider.AccountsContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.AttachmentsContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.ContactsContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.FoldersContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.MessagesContentProvider;
import com.aleksandar69.PMSU2020Tim16.database.provider.TagsContentProvider;
import com.aleksandar69.PMSU2020Tim16.models.Account;
import com.aleksandar69.PMSU2020Tim16.models.Attachment;
import com.aleksandar69.PMSU2020Tim16.models.Contact;
import com.aleksandar69.PMSU2020Tim16.models.Message;
import com.aleksandar69.PMSU2020Tim16.models.Tag;
import com.aleksandar69.PMSU2020Tim16.models.Folder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aleksandar69.PMSU2020Tim16.Data.TABLE_CONTACTS;

public class MessagesDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 372;
    public static final String DATABASE_NAME = "EMAILDB";

    //folders

    public static final String TABLE_FOLDERS = "FOLDERS";
    public static final String COLUMN_ID_FOLDER = "_id";
    public static final String COLUMN_NAME = "name";

    //contacts
    public static final String TABLE_CONTACTS = "CONTACT";

    public static final String COLUMN_ID_CONTACTS = "_id";
    private static final String COLUMN_FIRST = "firstname";
    private static final String COLUMN_LAST = "lastname";
    private static final String COLUMN_DISPLAY = "displaytext";
    //DODATI PLAIN,HTML
    private static final String COLUMN_CONTACT_EMAIL = "emailtext";
    private static final String COLUMN_IMAGE_RESOURCE = "imageresourceid";

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
    private static final String COLUMN_ISUNREAD = "isunreadmessage";
    private static final String COLUMN_TAGS = "tagsinmail";
    private static final String COLUMN_ID_FOLDERS_FK = "folder_id";

    //accounts


    public static final String TABLE_ACCOUNTS = "ACCOUNTS";

    public static final String COLUMN_ID_ACCOUNTS = "_id";
    private static final String COLUMN_SMTPADDRESS = "smptadress";
    private static final String COLUMN_PORT = "port";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DISPLAYNAME = "displayname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_SMTPHOST = "smtphost";
    private static final String COLUMN_IMAPHOST = "imaphost";

    //attachments

    public static final String TABLE_ATTACHMENTS = "ATTACHMENTS";

    public static final String COLUMN_ID_ATTACHMENTS = "_id";
    private static final String COLUMN_ATTACH_CONTENT = "content";
    public static final String COLUMN_FILENAME = "filename";
    public static final String COLUMN_EMAIL_ID_FK = "linkedemailid";

    //TAG

    public static final String TABLE_TAGS = "TAGS";
    public static final String COLUMN_ID_TAG = "_id";
    private static final String COLUMN_TAG_TEXT = "text";
    private static final String COLUMN_EMAIL_TAG_ID_FK = "emailtagfk";

    private ContentResolver myContentResolver;

    private static String CREATE_FOLDERS_TABLE = "CREATE TABLE " + TABLE_FOLDERS +
            "(" + COLUMN_ID_FOLDER + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT " + ")";

    private static String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES +
            "(" + COLUMN_ID_EMAILS + " INTEGER PRIMARY KEY, " +
            COLUMN_FROM + " TEXT, " + COLUMN_TO + " TEXT, " + COLUMN_CC + " TEXT, " +
            COLUMN_BCC + " TEXT, " + COLUMN_SUBJECT + " TEXT, " +
            COLUMN_CONTENT + " TEXT, " + COLUMN_DATETIME + " TEXT, " +
            COLUMN_ACCOUNTS_FK + " INTEGER, " +
            COLUMN_ID_FOLDERS_FK + " INTEGER, " +
            COLUMN_ISUNREAD + " INTEGER NOT NULL DEFAULT 1 CHECK(isunreadmessage IN (0,1)), " +
            COLUMN_TAGS + " TEXT, " +
            "FOREIGN KEY(" + COLUMN_ID_FOLDERS_FK + ") REFERENCES FOLDERS(_id), " +
            "FOREIGN KEY(" + COLUMN_ACCOUNTS_FK + ") REFERENCES ACCOUNTS(_id) " + ")";
            //"FOREIGN KEY(" + COLUMN_ID_FOLDERS_FK + ") REFERENCES FOLDERS(_id)" +


    private static String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS +
            "(" + COLUMN_ID_ACCOUNTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SMTPADDRESS + " TEXT, " + COLUMN_PORT + " TEXT, " +
            COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " +
            COLUMN_DISPLAYNAME + " TEXT, " + COLUMN_EMAIL + " TEXT, " +
            COLUMN_SMTPHOST + " TEXT, " + COLUMN_IMAPHOST + " TEXT" +
            ")";

    private static String CREATE_ATTACHMENT_TABLE = "CREATE TABLE " + TABLE_ATTACHMENTS +
            "(" + COLUMN_ID_ATTACHMENTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ATTACH_CONTENT + " TEXT, " + COLUMN_FILENAME + " TEXT, "
            + COLUMN_EMAIL_ID_FK + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_EMAIL_ID_FK + ") REFERENCES EMAILS(_id)" + ")";

    public static String CREATE_TAG_TABLE = "CREATE TABLE " + TABLE_TAGS +
            "(" + COLUMN_ID_TAG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TAG_TEXT + " TEXT, " + COLUMN_EMAIL_TAG_ID_FK + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_EMAIL_TAG_ID_FK + ") REFERENCES EMAILS(_id)" + ")";

    private static String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
            "(" + COLUMN_ID_CONTACTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRST + " TEXT, " + COLUMN_LAST + " TEXT, " +
            COLUMN_DISPLAY + " TEXT, " + COLUMN_CONTACT_EMAIL + " TEXT, " + COLUMN_IMAGE_RESOURCE + " INTEGER" + ")";



    public MessagesDBHandler(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FOLDERS_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        db.execSQL(CREATE_ATTACHMENT_TABLE);
        Log.d("Elena", "Pokrenuto kreiranje tabela");
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL("insert into " + TABLE_FOLDERS + "(" + COLUMN_ID_FOLDER + "," + COLUMN_NAME + ") values(1,'Drafts')");
        db.execSQL("insert into " + TABLE_FOLDERS + "(" + COLUMN_ID_FOLDER + "," + COLUMN_NAME + ") values(2,'Trash')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTACHMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLDERS);


        onCreate(db);

    }

    //////////////////////////FOLDERS //////////////////////////////
    public void addFolder(Folder folder){
        //if(CheckFolderInDB(folder.getName()) == false) {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID_FOLDER, folder.getId());
            values.put(COLUMN_NAME, folder.getName());
            myContentResolver.insert(FoldersContentProvider.CONTENT_URI, values);
        //}

    }



//    public boolean CheckFolderInDB(String name) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        try{
//            String Query = "Select * from " + TABLE_FOLDERS + " where " + COLUMN_NAME + " = " + name;
//
//            Cursor cursor = db.rawQuery(Query, null);
//            if(cursor.getCount() <= 0){
//                cursor.close();
//                return false;
//            }
//            cursor.close();
//        }
//
//        catch (Exception e){}
//        return true;
//    }

    public Cursor getAllFolders(){

        String[] projection = {COLUMN_ID_FOLDER, COLUMN_NAME};

        Cursor cursor = myContentResolver.query(FoldersContentProvider.CONTENT_URI, projection,
                null, null, null);

        return cursor;
    }

    public String getMessagesInFolderCount(int folderID){

        String countQuery = "SELECT  * FROM " + TABLE_MESSAGES + " WHERE " + COLUMN_ID_FOLDERS_FK + " = " + folderID ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        if(count == 0){
            return "Nema poruka";
        }
        else {
            return String.valueOf(count);
        }

//        if(count = ){
//            return 0;
//        }
//        else {
//            return count;
//        }
    }

    /////////////////////////// MESSAGES /////////////////////////////////

    public void addMessage(Message message) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_EMAILS, message.get_id());
        values.put(COLUMN_FROM, message.getFrom());
        values.put(COLUMN_TO, message.getTo());
        values.put(COLUMN_CC, message.getCc());
        values.put(COLUMN_BCC, message.getBcc());
        values.put(COLUMN_DATETIME, message.getDateTime());
        values.put(COLUMN_SUBJECT, message.getSubject());
        values.put(COLUMN_CONTENT, message.getContent());
        values.put(COLUMN_ACCOUNTS_FK, message.getLogged_user_id());
        values.put(COLUMN_ID_FOLDERS_FK, message.getFolder_id());
        // values.put(COLUMN_ISUNREAD,message.isUnread());
        if (message.isUnread() == true) {
            values.put(COLUMN_ISUNREAD, 1);
        } else if (message.isUnread() == false) {
            values.put(COLUMN_ISUNREAD, 0);
        }
        StringBuffer tagsStr = new StringBuffer();
        for (Tag tag : message.getTags()) {
            tagsStr.append(tag.getName() + ";");
        }
        values.put(COLUMN_TAGS, tagsStr.toString());

        myContentResolver.insert(MessagesContentProvider.CONTENT_URI, values);
    }

    public void updateisRead(Message message) {

        ContentValues values = new ContentValues();

        if (message.isUnread() == true) {
            values.put(COLUMN_ISUNREAD, 1);
        } else if (message.isUnread() == false) {
            values.put(COLUMN_ISUNREAD, 0);
        }

        myContentResolver.update(MessagesContentProvider.CONTENT_URI, values, COLUMN_ID_EMAILS + "=" + message.get_id(), null);
    }

    public Cursor filterEmail(String term) {
        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};

        String selection = COLUMN_CONTENT + " LIKE ? OR " + COLUMN_SUBJECT + " LIKE ? OR " + COLUMN_TAGS + " LIKE ? OR " + COLUMN_TO + " LIKE ? OR " +
                COLUMN_FROM + " LIKE ? OR " + COLUMN_BCC + " LIKE ? OR " + COLUMN_CC + " LIKE ?";

        String[] selectionArgs = {"%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%"};

        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection, selection, selectionArgs, null);

        return cursor;
    }

    public Cursor sortEmailAsc(int userId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};

        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection,
                null , null,COLUMN_ID_EMAILS + " ASC");

        return cursor;
    }

    public Cursor sortEmailDesc(int userId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};

        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection,
                null, null, COLUMN_ID_EMAILS + " DESC");

        return cursor;
    }


    public Message findMessage(int messageId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};
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
            message.setLogged_user_id(Integer.parseInt(cursor.getString(8)));
            int unread = Integer.parseInt(cursor.getString(9));
            if (unread == 1) {
                message.setUnread(true);
            } else if (unread == 0) {
                message.setUnread(false);
            }

            String tags = cursor.getString(10);

            String[] tagList = tags.split(";[ ]*");

            List<Tag> tagsList = new ArrayList<>();

            for (String tag : tagList) {
                Tag tagobj = new Tag();
                tagobj.setName(tag);
                tagsList.add(tagobj);
            }

            message.setTags(tagsList);

            cursor.close();
        } else {
            message = null;
        }
        return message;
    }

    public Cursor getAllMessages(int userId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};


        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection,
                COLUMN_ACCOUNTS_FK + "=" + userId, null, null);

        return cursor;
    }

    public Cursor getAllMessages2() {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};


        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection,
                null, null, null);

        return cursor;
    }

    public List<Message> queryAllMessages(int userId) {

        String[] projection = {COLUMN_ID_EMAILS, COLUMN_FROM, COLUMN_TO, COLUMN_CC, COLUMN_BCC, COLUMN_SUBJECT, COLUMN_CONTENT, COLUMN_DATETIME, COLUMN_ACCOUNTS_FK, COLUMN_ISUNREAD, COLUMN_TAGS};
        String selection = null;

        Cursor cursor = myContentResolver.query(MessagesContentProvider.CONTENT_URI, projection, COLUMN_ACCOUNTS_FK + "=" + userId, null, null);

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
            message.setLogged_user_id(Integer.parseInt(cursor.getString(8)));
            int unread = Integer.parseInt(cursor.getString(9));
            if (unread == 1) {
                message.setUnread(true);
            } else if (unread == 0) {
                message.setUnread(false);
            }
            String tags = cursor.getString(10);

            String[] tagList = tags.split(";[ ]*");

            List<Tag> tagsList = new ArrayList<>();

            for (String tag : tagList) {
                Tag tagobj = new Tag();
                tagobj.setName(tag);
                tagsList.add(tagobj);
            }

            message.setTags(tagsList);

            messages.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return messages;

    }

    public boolean deleteMessage(int id) {

        boolean result = false;

        //String selection = "_id = " + "\"" + id + "\"";

        int rowsDeleted = myContentResolver.delete(MessagesContentProvider.CONTENT_URI, COLUMN_ID_EMAILS + " = " + id, null);

        if (rowsDeleted > 0)
            result = true;

        return result;


    }


    /////////////////////////// ACCOUNTS ////////////////////////////


    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SMTPADDRESS, account.getSmtpPort());
        values.put(COLUMN_PORT, account.getPort());
        values.put(COLUMN_USERNAME, account.getUsername());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_DISPLAYNAME, account.getDisplayName());
        values.put(COLUMN_EMAIL, account.geteMail());
        values.put(COLUMN_SMTPHOST, account.getSmtphost());
        values.put(COLUMN_IMAPHOST, account.getImapHost());

        myContentResolver.insert(AccountsContentProvider.CONTENT_URI, values);
    }

    public List<Account> queryAccounts() {

        String[] projection = {COLUMN_ID_ACCOUNTS, COLUMN_SMTPADDRESS, COLUMN_PORT, COLUMN_USERNAME,
                COLUMN_PASSWORD, COLUMN_DISPLAYNAME, COLUMN_EMAIL, COLUMN_SMTPHOST, COLUMN_IMAPHOST};

        Cursor cursor = myContentResolver.query(AccountsContentProvider.CONTENT_URI, projection, null, null, null);

        List<Account> accounts = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = new Account();
            account.set_id(Integer.parseInt(cursor.getString(0)));
            account.setSmtpPort(cursor.getString(1));
            account.setPort(cursor.getString(2));
            account.setUsername(cursor.getString(3));
            account.setPassword(cursor.getString(4));
            account.setDisplayName(cursor.getString(5));
            account.seteMail(cursor.getString(6));
            account.setSmtphost(cursor.getString(7));
            account.setImapHost(cursor.getString(8));

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
                COLUMN_PASSWORD, COLUMN_DISPLAYNAME, COLUMN_EMAIL, COLUMN_SMTPHOST, COLUMN_IMAPHOST};

        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = myContentResolver.query(AccountsContentProvider.CONTENT_URI, projection, selection, selectionArgs, null);

        Account account = new Account();

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            account = new Account();
            account.set_id(Integer.parseInt(cursor.getString(0)));
            account.setSmtpPort(cursor.getString(1));
            account.setPort(cursor.getString(2));
            account.setUsername(cursor.getString(3));
            account.setPassword(cursor.getString(4));
            account.setDisplayName(cursor.getString(5));
            account.seteMail(cursor.getString(6));
            account.setSmtphost(cursor.getString(7));
            account.setImapHost(cursor.getString(8));
            cursor.close();
        } else {
            account = null;
        }
        return account;

    }

    /////////////////////////////////////////ATTACHMENTS/////////////////////////////////////////

    public void addAttachment(Attachment attachment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTACH_CONTENT, attachment.getContent());
        values.put(COLUMN_FILENAME, attachment.getFileName());
        values.put(COLUMN_EMAIL_ID_FK, attachment.getMessageId());

        myContentResolver.insert(AttachmentsContentProvider.CONTENT_URI, values);
    }

    public List<Attachment> queryAttachForMessage(int messageAttId) {
        String[] projection = {COLUMN_ID_ATTACHMENTS, COLUMN_ATTACH_CONTENT, COLUMN_FILENAME, COLUMN_EMAIL_ID_FK};

        Cursor cursor = myContentResolver.query(AttachmentsContentProvider.CONTENT_URI,
                projection, COLUMN_EMAIL_ID_FK + "=" + messageAttId, null, null);

        List<Attachment> attachemntList = new ArrayList<>();

        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                Attachment attachment = new Attachment();
                attachment.set_id(Integer.parseInt(cursor.getString(0)));
                attachment.setContent(cursor.getString(1));
                attachment.setFileName(cursor.getString(2));
                attachemntList.add(attachment);
                cursor.moveToNext();
            }
        } catch (NullPointerException e) {
            Log.e("NULL", "Cursor attachment can't be null");
        }
        cursor.close();
        return attachemntList;

    }

    public Attachment queryAttachbyName(String messageName) {
        String[] projection = {COLUMN_ID_ATTACHMENTS, COLUMN_ATTACH_CONTENT, COLUMN_FILENAME, COLUMN_EMAIL_ID_FK};

        String selection = COLUMN_FILENAME + " = ? ";

        String[] selectionArgs = {messageName};

        Cursor cursor = myContentResolver.query(AttachmentsContentProvider.CONTENT_URI,
                projection, selection, selectionArgs, null);

        Attachment attachment;


        if (cursor.moveToFirst()) {
            attachment = new Attachment();
            attachment.set_id(Integer.parseInt(cursor.getString(0)));
            attachment.setContent(cursor.getString(1));
            attachment.setFileName(cursor.getString(2));
            cursor.close();
        } else {
            attachment = null;
        }
        return attachment;
    }

    //////////////////////////////////////////////TAGS/////////////////////////////////////

    public void addTag(Tag tag) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAG_TEXT, tag.getName());
        values.put(COLUMN_EMAIL_TAG_ID_FK, tag.getMessageId());

        myContentResolver.insert(TagsContentProvider.CONTENT_URI, values);
    }

    public List<Tag> queryTagByMessID(int id) {
        String[] projection = {COLUMN_ID_TAG, COLUMN_TAG_TEXT, COLUMN_EMAIL_TAG_ID_FK};

        Cursor cursor = myContentResolver.query(TagsContentProvider.CONTENT_URI, projection, COLUMN_EMAIL_TAG_ID_FK + "=" + id, null, null);

        List<Tag> tagsList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tag tag = new Tag();
            tag.set_id(Integer.parseInt(cursor.getString(0)));
            tag.setName(cursor.getString(1));
            tag.setMessageId(Integer.parseInt(cursor.getString(2)));
            tagsList.add(tag);
            cursor.moveToNext();

        }
        cursor.close();
        return tagsList;
    }


    /////////////////////////// CONTACTS /////////////////////////////////


//============================AKTIVNO KORISTIM========================================================
    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST,contact.getFirst());
        values.put(COLUMN_LAST,contact.getLast());
        values.put(COLUMN_DISPLAY,contact.getDisplay());
        values.put(COLUMN_CONTACT_EMAIL,contact.getEmail());
        //poslije dodati za fotografiju i za display html

        db.insert(TABLE_CONTACTS,null,values);
        db.close();
    }


    public void updateContact333(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_CONTACTS,contact.get_id());
        values.put(COLUMN_FIRST, contact.getFirst());
        values.put(COLUMN_LAST, contact.getLast());
        values.put(COLUMN_DISPLAY, contact.getDisplay());
        values.put(COLUMN_CONTACT_EMAIL, contact.getEmail());
        db.update(TABLE_CONTACTS, values, COLUMN_ID_CONTACTS + "='" + contact.get_id() + "'", null);
        db.close();
    }

    //metoda za dobavljanje svih kontakata preko liste
    public List getAllContactsList() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //read from db
        String selection = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(selection, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setFirst(cursor.getString(1));
                contact.setLast(cursor.getString(2));
                contact.setDisplay(cursor.getString(3));
                contact.setEmail(cursor.getString(4));
                //               contact.setImageSourceID(Integer.parseInt(cursor.getString(5))); //zato sto baca NumberFormatException, zakomentarisala sam i u ContactsActivity
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

//    //============================AKTIVNO KORISTIM========================================================


    public void addContacts(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contactsValues = new ContentValues();
        //contactsValues.put(COLUMN_ID_CONTACTS, contact.get_id());
        contactsValues.put(COLUMN_FIRST, contact.getFirst());
        contactsValues.put(COLUMN_LAST, contact.getLast());
        contactsValues.put(COLUMN_DISPLAY, contact.getDisplay());
        contactsValues.put(COLUMN_CONTACT_EMAIL, contact.getEmail());
        contactsValues.put(COLUMN_IMAGE_RESOURCE, contact.getImageSourceID());
        db.insert(TABLE_CONTACTS, null, contactsValues);
        Log.d("Elena", "Contact is inserted");
        //  myContentResolver.insert(ContactsContentProvider.CONTENT_URI,contactsValues);
        db.close();
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contactValues = new ContentValues();
        contactValues.put(COLUMN_ID_CONTACTS,contact.get_id()); //OVO ZADNJE DODALA
        contactValues.put(COLUMN_FIRST, contact.getFirst());
        contactValues.put(COLUMN_LAST, contact.getLast());
        contactValues.put(COLUMN_DISPLAY, contact.getDisplay());
        contactValues.put(COLUMN_CONTACT_EMAIL, contact.getEmail());
        contactValues.put(COLUMN_IMAGE_RESOURCE, contact.getImageSourceID());
        return db.update(TABLE_CONTACTS, contactValues,   "_id = ? ",
                new String[]{String.valueOf(contact.get_id())});

    }

    //SA ID-IJEM, bez image resource-a
    public boolean updateContact22(String first, String last, String display,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contactValues = new ContentValues();
        //contactValues.put(COLUMN_ID_CONTACTS,id); //OVO ZADNJE DODALA
        contactValues.put(COLUMN_FIRST, first);
        contactValues.put(COLUMN_LAST, last);
        contactValues.put(COLUMN_DISPLAY, display);
        contactValues.put(COLUMN_CONTACT_EMAIL, email);
        //contactValues.put(COLUMN_IMAGE_RESOURCE, contact.getImageSourceID());
        db.update(TABLE_CONTACTS,contactValues, "firstname = ? ", new String[]{ first });
        getAllContactsList();
        return true;
    }

    public void deleteContactByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID_CONTACTS + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount() {
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
          TABLE_CONTACTS,
          new String []{COLUMN_ID_CONTACTS,COLUMN_FIRST,COLUMN_LAST,COLUMN_DISPLAY,COLUMN_CONTACT_EMAIL},
                COLUMN_ID_CONTACTS + "=?",
                new String[]{String.valueOf(id)},
                null,null,null,null
        );

        Contact contact;

        if(cursor!=null){
            cursor.moveToFirst();
            //dodati za display html i imagerseosurce id
            contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),cursor.getString(4));
            return contact;
        } else{
            return null;
        }
    }

    public ArrayList<Contact> getListOfAllContacts() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {
            do{
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setFirst(cursor.getString(1));
                contact.setLast(cursor.getString(2));
                contact.setDisplay(cursor.getString(3));
                contact.setEmail(cursor.getString(4));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        return contacts;
    }

    public int updateContact2(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST,contact.getFirst());
        values.put(COLUMN_LAST,contact.getLast());
        values.put(COLUMN_DISPLAY,contact.getDisplay());
        values.put(COLUMN_CONTACT_EMAIL,contact.getEmail());

        return db.update(TABLE_CONTACTS,values,null,null);
    }


    public void deleteContact2(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS,
                COLUMN_ID_CONTACTS + " = ?",
                new String[]{String.valueOf(contact.get_id())});
        db.close();
    }

    public int getContactCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    public void addContacts1(Contact contact) {
        //if(CheckFolderInDB(folder.getName()) == false) {
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID_FOLDER, folder.getId());
        values.put(COLUMN_FIRST, contact.getFirst());
        myContentResolver.insert(ContactsContentProvider.CONTENT_URI, values);
        //}
    }


    public Contact findContact(int contactId) {
        String[] projection = {COLUMN_ID_CONTACTS, COLUMN_FIRST, COLUMN_LAST, COLUMN_DISPLAY, COLUMN_CONTACT_EMAIL, COLUMN_IMAGE_RESOURCE};
        String selection = "_id = \"" + contactId + "\"";
        Cursor cursor = myContentResolver.query(ContactsContentProvider.CONTENT_URI, projection, selection, null, null);

        Contact contact = new Contact();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            contact.set_id(Integer.parseInt(cursor.getString(0)));
            contact.setFirst(cursor.getString(1));
            contact.setLast(cursor.getString(2));
            contact.setDisplay(cursor.getString(3));
            contact.setEmail(cursor.getString(4));
            contact.setImageSourceID(Integer.parseInt(cursor.getString(5)));

            cursor.close();
        } else {
            contact = null;
        }
        return contact;

    }

    public void dropTableContact(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    }

    public boolean deleteContact(int id) {
        boolean result = false;

        int rowsDeleted = myContentResolver.delete(ContactsContentProvider.CONTENT_URI,
                COLUMN_ID_CONTACTS + " = " + id, null);

        if (rowsDeleted > 0)
            result = true;

        return result;
    }

    public Contact queryContactByName(String contactFirstName) {
        String[] projection = {COLUMN_ID_CONTACTS, COLUMN_FIRST, COLUMN_LAST, COLUMN_DISPLAY, COLUMN_CONTACT_EMAIL, COLUMN_IMAGE_RESOURCE};
        String selection = COLUMN_FIRST + " = ?";
        String[] selectionArgs = {contactFirstName};

        Cursor cursor = myContentResolver.query(ContactsContentProvider.CONTENT_URI,
                projection, selection, selectionArgs, null);

        Contact contact;

        if (cursor.moveToFirst()) {
            contact = new Contact();
            contact.set_id(Integer.parseInt(cursor.getString(0)));
            contact.setFirst(cursor.getString(1));
            contact.setLast(cursor.getString(2));
            contact.setDisplay(cursor.getString(3));
            contact.setEmail(cursor.getString(4));
            contact.setImageSourceID(Integer.parseInt(cursor.getString(5)));
            cursor.close();
        } else {
            contact = null;
        }
        return contact;
    }

    public List<Contact> queryContacts() {
        String[] projection = {COLUMN_ID_CONTACTS, COLUMN_FIRST, COLUMN_LAST, COLUMN_DISPLAY, COLUMN_CONTACT_EMAIL, COLUMN_IMAGE_RESOURCE};
        Cursor cursor = myContentResolver.query(ContactsContentProvider.CONTENT_URI, projection, null, null, null);

        List<Contact> contacts = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Contact contact = new Contact();
            contact.set_id(Integer.parseInt(cursor.getString(0)));
            contact.setFirst(cursor.getString(1));
            contact.setLast(cursor.getString(2));
            contact.setDisplay(cursor.getString(3));
            contact.setEmail(cursor.getString(4));
            contact.setImageSourceID(Integer.parseInt(cursor.getString(5)));
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }

    //metoda za dobavljanje svih kontakata jedan
    public Cursor getAllContacts() {
        String[] projection = {COLUMN_ID_CONTACTS, COLUMN_FIRST, COLUMN_LAST, COLUMN_DISPLAY, COLUMN_CONTACT_EMAIL, COLUMN_IMAGE_RESOURCE};
        Cursor cursor = myContentResolver.query(ContactsContentProvider.CONTENT_URI, projection,
                null, null, null);
        return cursor;
    }

    //filtriranje kroz kontakte
    public Cursor filterContacts(String term) {
        String[] projection = {COLUMN_ID_CONTACTS, COLUMN_FIRST, COLUMN_LAST, COLUMN_DISPLAY, COLUMN_CONTACT_EMAIL, COLUMN_IMAGE_RESOURCE};
        String selection = COLUMN_FIRST + " LIKE ? OR " + COLUMN_LAST + " LIKE ?";
        String[] selectionArgs = {"%" + term + "%", "%" + term + "%"};
        Cursor cursor = myContentResolver.query(ContactsContentProvider.CONTENT_URI, projection, selection, selectionArgs, null);

        return cursor;
    }
}
