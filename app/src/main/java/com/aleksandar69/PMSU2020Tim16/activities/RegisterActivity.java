package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.javamail.AuthenticateMail;
import com.aleksandar69.PMSU2020Tim16.models.Account;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    //TextInputEditText smtpAddressinput;
    //TextInputEditText portinput;
    TextInputEditText usernameinput;
    TextInputEditText passwordinput;
    TextInputEditText displayNameinput;
    TextInputEditText eMailinput;

    MessagesDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");

        // smtpAddressinput = (TextInputEditText) findViewById(R.id.smtpTV);
        // portinput = (TextInputEditText) findViewById(R.id.portTV);
        usernameinput = findViewById(R.id.usernameTV);
        passwordinput = findViewById(R.id.passwordTV);
        displayNameinput = findViewById(R.id.displayNameTV);
        eMailinput = findViewById(R.id.eMailTV);

    }


    public void makeNewAccountFromInput(View view) {
        //String smtpAdress = smtpAddressinput.getText().toString();
        //String port = portinput.getText().toString();
        String userName = usernameinput.getText().toString();
        String password = passwordinput.getText().toString();
        String displayName = displayNameinput.getText().toString();
        String email = eMailinput.getText().toString();

        Account account;
        dbHandler = new MessagesDBHandler(this);

        if (email.contains("@gmail.com")) {
            String smtpPort = "465";
            String smtphost = "smtp.gmail.com";
            String imaphost = "imap.googlemail.com";

            AuthenticateMail checkExistance = new AuthenticateMail(imaphost, email, password);
            checkExistance.execute();

                account = new Account(smtpPort, smtpPort, userName, password, displayName, email, smtphost, imaphost);
                dbHandler.addAccount(account);

            } else if (email.contains("@hotmail.com") || email.contains("outlook.com")) {
            String smtpPort = "587";
            String imaphost = "outlook.office365.com";
            String smtphost = "smtp.office365.com";

            AuthenticateMail checkExistance = new AuthenticateMail(imaphost, email, password);
            checkExistance.execute();

                account = new Account(smtpPort, smtpPort, userName, password, displayName, email, smtphost, imaphost);
                dbHandler.addAccount(account);


        } /*else if(email.contains("yahoo.com")){
            String smtpPort = "995";
            String smtphost = "smtp.mail.yahoo.com";
            String imaphost = "imap.mail.yahoo.com";

            account = new Account(smtpPort, smtpPort, userName, password, displayName, email, smtphost, imaphost);

        }*/ else {
            Toast.makeText(this, "Please use gmail or hotmail accounts.", Toast.LENGTH_SHORT);
            return;
        }


        try {
        } catch (Exception e) {
            Log.e("Account error", "Can't make new account");
        }

        startActivity(new Intent(this, LoginActivity.class));

    }
}
