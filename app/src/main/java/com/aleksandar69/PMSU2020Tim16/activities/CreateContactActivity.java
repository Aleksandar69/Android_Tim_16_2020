package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    Context context;
    private TextInputEditText firstNameEdit;
    private TextInputEditText lastNameEdit;
    private TextInputEditText displayNameEdit;
    private TextInputEditText emailEdit;
    MessagesDBHandler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        context = this;
        handler = new MessagesDBHandler(context);

        imageEdit = findViewById(R.id.image_contact);
        firstNameEdit = findViewById(R.id.new_contact_firstname);
        lastNameEdit = findViewById(R.id.new_contact_lastname);
        displayNameEdit = findViewById(R.id.new_contact_displayname);
        emailEdit = findViewById(R.id.new_contact_email);

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
                String first = firstNameEdit.getText().toString();
                String last = lastNameEdit.getText().toString();
                String display = displayNameEdit.getText().toString();
                String email = emailEdit.getText().toString();

                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(last) || !TextUtils.isEmpty(display) || !TextUtils.isEmpty(email)){
                    Contact contact = new Contact(first,last,display,email);
                    handler.addContact(contact);
                    startActivity(new Intent(context,ContactsActivity.class));

                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please fill at least one field! ").setNegativeButton("OK",null).show();
                }
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
