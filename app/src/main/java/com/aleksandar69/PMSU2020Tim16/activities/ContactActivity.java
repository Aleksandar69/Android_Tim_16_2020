package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.adapters.RecyclerViewContactsAdapter;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Contact;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    /*
    public static final String CONTACT_NUMBER = "contactNo";
    List<String> contact;
    RecyclerViewContactsAdapter adapter;
    MessagesDBHandler handler;
     */

    private ArrayAdapter arrayAdapter;
    private List contactsList = new ArrayList<>();
    private TextInputEditText firstNameEditt;
    private TextInputEditText lastNameEditt;
    private TextInputEditText displayNameEditt;
    private TextInputEditText emailEditt;
    MessagesDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        handler = new MessagesDBHandler(this);

        firstNameEditt = (TextInputEditText) findViewById(R.id.new_contact_firstnamee);
        lastNameEditt = (TextInputEditText) findViewById(R.id.new_contact_lastnamee);
        displayNameEditt = (TextInputEditText) findViewById(R.id.new_contact_displaynamee);
        emailEditt = (TextInputEditText) findViewById(R.id.new_contact_emaill);

        Intent intent = getIntent();
        String first = intent.getStringExtra("efirst");
        String last = intent.getStringExtra("elast");
        String display = intent.getStringExtra("edisplay");
        String email = intent.getStringExtra("eemail");
        firstNameEditt.setText(String.valueOf(first));
        lastNameEditt.setText(String.valueOf(last));
        displayNameEditt.setText(String.valueOf(display));
        emailEditt.setText(String.valueOf(email));

        List<Contact> list = handler.getAllContactsList();
        for (Contact contact1 : list) {
            Log.d("Elena", "\n" + "Id kontakta sa ContactsActivity je: " + contact1.get_id() + "\n" +
                    "First name kontakta sa ContactsActivity je: " + contact1.getFirst() + "\n" +
                    "Last name kontakta sa ContactsActivity je: " + contact1.getLast() + "\n" +
                    "Display name kontaktasa ContactsActivity je: " + contact1.getDisplay() + "\n" +
                    "Email kontakta sa ContactsActivity je: " + contact1.getEmail() + "\n");
        }

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
        switch (item.getItemId()) {
            case R.id.save_contact:
                return true;
          default:
              return super.onOptionsItemSelected(item);
    }

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
