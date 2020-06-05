package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Contact;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateContactActivity extends AppCompatActivity {

    //i ovde sliku dodati
    private CircleImageView imageEdit;
    private TextInputEditText firstNameEdit;
    private TextInputEditText lastNameEdit;
    private TextInputEditText displayNameEdit;
    private TextInputEditText emailEdit;
    MessagesDBHandler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        imageEdit = findViewById(R.id.image_contact);
        firstNameEdit = findViewById(R.id.new_contact_firstname);
        lastNameEdit = findViewById(R.id.new_contact_lastname);
        displayNameEdit = findViewById(R.id.new_contact_displayname);
        emailEdit = findViewById(R.id.new_contact_email);

        handler = new MessagesDBHandler(this);

        Toolbar toolbar = findViewById(R.id.toolbar_create_contact);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
