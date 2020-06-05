package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.adapters.RecyclerViewContactsAdapter;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Contact;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT_NUMBER = "contactNo";
    MessagesDBHandler handler;
    private TextInputEditText firstNameEditt;
    private TextInputEditText lastNameEditt;
    private TextInputEditText displayNameEditt;
    private TextInputEditText emailEditt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Intent intent = getIntent();
        String first = intent.getStringExtra("RFirst");
        String last = intent.getStringExtra("RLast");
        String display = intent.getStringExtra("RDisplay");
        String email = intent.getStringExtra("REmail");
        //int photoID = intent.getIntExtra("RPhoto",0);

        //ImageView photoI = findViewById(R.id.slicica);
        //photoI.setId(photoID);

        firstNameEditt = findViewById(R.id.new_contact_firstnamee);
        firstNameEditt.setText(first);

        lastNameEditt = findViewById(R.id.new_contact_lastnamee);
        lastNameEditt.setText(last);

        displayNameEditt = findViewById(R.id.new_contact_displaynamee);
        displayNameEditt.setText(display);

        emailEditt = findViewById(R.id.new_contact_displaynamee);
        emailEditt.setText(email);

        handler = new MessagesDBHandler(this);

        //iz dokumentacije
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Capture the layout's TextView and set the string as its text
        //TextView textView = findViewById(R.id.textView);
        //textView.setText(message);

        /*
        Intent intent = getIntent();
        String first = intent.getStringExtra("RFirst name");
        String last = intent.getStringExtra("RLast name");
        String display = intent.getStringExtra("RDisplay");
        String email = intent.getStringExtra("REmail");


        TextView firstNameView = findViewById(R.id.tv_ime);
        firstNameView.setText(first);

        TextView lastNameView = findViewById(R.id.tv_prezime);
        lastNameView.setText(last);

        TextView displayView = findViewById(R.id.displayNameTV);
        displayView.setText(display);

        TextView emailView = findViewById(R.id.tv_email);
        emailView.setText(email);
        */



        /*
        int contactNo = (Integer) getIntent().getExtras().get(CONTACT_NUMBER);
        
        try{
            SQLiteOpenHelper contactDBhelper = new MessagesDBHandler(this);
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

                TextView firstName = findViewById(R.id.tv_ime);
                firstName.setText(firstText);
                TextView lastName = findViewById(R.id.tv_prezime);
                lastName.setText(lastText);
               // TextView display = (TextView) findViewById(R.id.displayNameTV);
              //  display.setText(displayText);
                TextView email = findViewById(R.id.tv_email);
                email.setText(emailText);
                ImageView photo = findViewById(R.id.slicica);
                photo.setImageResource(photoId);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e ) {
            Toast toast = Toast.makeText(this, "Database is unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        /*

/*


        TextView first = (TextView) findViewById(R.id.tv_ime);
        first.setText(contact.getFirst());

        TextView last = (TextView) findViewById(R.id.tv_prezime);
        last.setText(contact.getLast());


        TextView email = (TextView) findViewById(R.id.tv_email);
        email.setText(contact.getEmail()); */

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
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
        /*
        RecyclerViewContactsAdapter adapter = new RecyclerViewContactsAdapter();
        boolean isUpdated = handler.updateContact2(firstNameEditt.getText().toString(), lastNameEditt.getText().toString(),
                displayNameEditt.getText().toString(), emailEditt.getText().toString());
        if (isUpdated == true) {
            Toast.makeText(ContactActivity.this, "Data updated", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ContactsActivity.class));
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(ContactActivity.this, "Data is not updated", Toast.LENGTH_LONG).show();
        }
        return isUpdated;

         */
        RecyclerViewContactsAdapter adapter = new RecyclerViewContactsAdapter();
        Contact contact = new Contact();
        contact.setFirst(firstNameEditt.getText().toString());
        contact.setLast(lastNameEditt.getText().toString());
        contact.setDisplay(displayNameEditt.getText().toString());
        contact.setEmail(emailEditt.getText().toString());
        handler.updateContact(contact);
        Toast.makeText(this,"Contact is updated",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,ContactsActivity.class));

        adapter.notifyDataSetChanged();
        return true;
    }



      /*
       switch(item.getItemId()) {
        case R.id.cancel_contact_creation:
            Intent intent = new Intent(this,ContactsActivity.class);
            Toast.makeText(this,"Contact creation canceled", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        case R.id.save_new_contact:
            Contact contact = new Contact();
            contact.setFirst(firstNameEdit.getText().toString());
            contact.setLast(lastNameEdit.getText().toString());
            contact.setDisplay(displayNameEdit.getText().toString());
            contact.setEmail(emailEdit.getText().toString());
            handler.addContacts(contact);
            startActivity(new Intent(this,ContactsActivity.class));
              default:
                return super.onOptionsItemSelected(item);
*/


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
        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        //InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        //inputMethodManager.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);

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
