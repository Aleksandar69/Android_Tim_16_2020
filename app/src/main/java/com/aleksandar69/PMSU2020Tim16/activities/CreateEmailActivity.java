package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Message;
import com.google.android.material.textfield.TextInputEditText;

public class CreateEmailActivity extends AppCompatActivity {

    TextInputEditText fromEditBox;
    TextInputEditText toEditBox;
    TextInputEditText ccEditBox;
    TextInputEditText bccEditBox;
    TextInputEditText subjectEditBox;
    EditText contentEditBox;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_email);

        fromEditBox = (TextInputEditText) findViewById(R.id.email_from);
        toEditBox = (TextInputEditText) findViewById(R.id.email_to);
        ccEditBox = (TextInputEditText) findViewById(R.id.email_cc);
        bccEditBox = (TextInputEditText) findViewById(R.id.email_bcc);
        subjectEditBox = (TextInputEditText) findViewById(R.id.email_subject);
        contentEditBox = (EditText) findViewById( R.id.email_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_email);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Message");

        mSharedPreferences = getSharedPreferences(LoginActivity.myPreferance, Context.MODE_PRIVATE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.email_cancel_button:
                Intent intent  = new Intent(this, EmailsActivity.class);
                Toast.makeText(this, "Message Creation Cancelled", Toast.LENGTH_LONG).show();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void sendMessage(View view) {


        MessagesDBHandler dbHandler = new MessagesDBHandler(this);

       Message message = new Message(fromEditBox.getText().toString(), toEditBox.getText().toString(),
                ccEditBox.getText().toString(), bccEditBox.getText().toString(),subjectEditBox.getText().toString(), contentEditBox.getText().toString());

       message.setLogged_user_id(mSharedPreferences.getInt(LoginActivity.userId, -1));

        dbHandler.addMessage(message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_email, menu);
        return super.onCreateOptionsMenu(menu);
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
