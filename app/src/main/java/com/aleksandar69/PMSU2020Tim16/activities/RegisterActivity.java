package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Account;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText smtpAddressinput;
    TextInputEditText portinput;
    TextInputEditText usernameinput;
    TextInputEditText passwordinput;
    TextInputEditText displayNameinput;
    TextInputEditText eMailinput;

    MessagesDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");

        smtpAddressinput = (TextInputEditText) findViewById(R.id.smtpTV);
        portinput = (TextInputEditText) findViewById(R.id.portTV);
        usernameinput = (TextInputEditText) findViewById(R.id.usernameTV);
        passwordinput = (TextInputEditText) findViewById(R.id.passwordTV);
        displayNameinput = (TextInputEditText) findViewById(R.id.displayNameTV);
        eMailinput = (TextInputEditText) findViewById(R.id.eMailTV);

    }


    public void makeNewAccountFromInput(View view){
        String smtpAdress = smtpAddressinput.getText().toString();
        String port = portinput.getText().toString();
        String userName = usernameinput.getText().toString();
        String password = passwordinput.getText().toString();
        String displayName = displayNameinput.getText().toString();
        String email = eMailinput.getText().toString();

        Account account = new Account(smtpAdress,port,userName,password,displayName,email);

        dbHandler = new MessagesDBHandler(this);

        dbHandler.addAccount(account);

    }


}
