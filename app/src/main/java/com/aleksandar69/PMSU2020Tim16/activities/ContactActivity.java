package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.ContactsDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Contact;

import org.w3c.dom.Text;

import java.sql.SQLException;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT_NUMBER = "contactNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        int contactNo = (Integer) getIntent().getExtras().get(CONTACT_NUMBER);
        
        try{
            SQLiteOpenHelper contactDBhelper = new ContactsDBHandler(this);
            SQLiteDatabase db = contactDBhelper.getReadableDatabase();
            Cursor cursor = db.query(
                    "CONTACT",
                    new String[] {"FIRST", "LAST", "DISPLAY", "EMAIL", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(contactNo)},
                    null, null,null
            );

            if(cursor.moveToFirst()) {
                String firstText = cursor.getString(0);
                String lastText = cursor.getString(1);
                String displayText = cursor.getString(2);
                String emailText = cursor.getString(3);
                int photoId = cursor.getInt(4);

                TextView firstName = (TextView) findViewById(R.id.tv_ime);
                firstName.setText(firstText);
                TextView lastName = (TextView) findViewById(R.id.tv_prezime);
                lastName.setText(lastText);
               // TextView display = (TextView) findViewById(R.id.displayNameTV);
              //  display.setText(displayText);
                TextView email = (TextView) findViewById(R.id.tv_email);
                email.setText(emailText);
                ImageView photo = (ImageView) findViewById(R.id.slicica);
                photo.setImageResource(photoId);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e ) {
            Toast toast = Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

/*
        ImageView photo = (ImageView) findViewById(R.id.slicica);
        photo.setImageResource(contact.getImageSourceID());

        TextView first = (TextView) findViewById(R.id.tv_ime);
        first.setText(contact.getFirst());

        TextView last = (TextView) findViewById(R.id.tv_prezime);
        last.setText(contact.getLast());


        TextView email = (TextView) findViewById(R.id.tv_email);
        email.setText(contact.getEmail()); */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Contact");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
