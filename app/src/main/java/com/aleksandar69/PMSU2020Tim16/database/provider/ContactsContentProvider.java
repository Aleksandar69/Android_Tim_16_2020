package com.aleksandar69.PMSU2020Tim16.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;

public class ContactsContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.aleksandar69.PMSU2020Tim16.database.provider.MessagesContentProvider";
    private static final String CONTACTS_TABLE = "CONTACTS";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACTS_TABLE);

    public static final int CONTACTS = 1;
    public static final int CONTACT_ID = 2;

    private MessagesDBHandler myDB;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public ContactsContentProvider() {

    }

    @Override
    public boolean onCreate() {
        myDB = new MessagesDBHandler(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MessagesDBHandler.TABLE_CONTACTS);

        int uriType = URI_MATCHER.match(uri);

        switch (uriType) {
            case CONTACT_ID:
                queryBuilder.appendWhere(MessagesDBHandler.COLUMN_ID_CONTACTS + "="
                + uri.getLastPathSegment());
                break;
            case CONTACTS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI ");
        }

        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = URI_MATCHER.match(uri);

        SQLiteDatabase sqlDB = myDB.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case CONTACTS:
                id = sqlDB.insert(MessagesDBHandler.TABLE_CONTACTS,null,values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI : "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse(CONTACTS_TABLE + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = URI_MATCHER.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsDeleteted = 0;

        switch (uriType) {
            case CONTACTS:
                rowsDeleteted = sqlDB.delete(MessagesDBHandler.TABLE_CONTACTS,
                        selection,selectionArgs);
                break;

            case CONTACT_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)) {
                    rowsDeleteted = sqlDB.delete(MessagesDBHandler.TABLE_CONTACTS,
                            MessagesDBHandler.COLUMN_ID_CONTACTS + "=" + id,null);

                } else {
                    rowsDeleteted = sqlDB.delete(MessagesDBHandler.TABLE_CONTACTS,
                            MessagesDBHandler.COLUMN_ID_CONTACTS + "=" + id + " and " + selection,
                           selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleteted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int uriType = URI_MATCHER.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case CONTACTS:
                rowsUpdated = sqlDB.update(MessagesDBHandler.TABLE_CONTACTS,values, selection,selectionArgs);
                break;
            case CONTACT_ID:
                String id =uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(MessagesDBHandler.TABLE_CONTACTS,values,
                    MessagesDBHandler.COLUMN_ID_CONTACTS + "=" + id,  null         );
                } else {
                    rowsUpdated = sqlDB.update(MessagesDBHandler.TABLE_CONTACTS,values,
                            MessagesDBHandler.COLUMN_ID_CONTACTS + "=" + id + "and" + selection,selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }
}
