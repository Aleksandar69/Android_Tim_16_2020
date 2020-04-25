package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
    }


    public void sendMessage(View view) {


        MessagesDBHandler dbHandler = new MessagesDBHandler(this);

       Message message = new Message(fromEditBox.getText().toString(), toEditBox.getText().toString(),
                ccEditBox.getText().toString(), bccEditBox.getText().toString(),subjectEditBox.getText().toString(), contentEditBox.getText().toString());

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
