package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
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

import javax.mail.MessagingException;

public class RegisterActivity extends AppCompatActivity {

    //TextInputEditText smtpAddressinput;
    //TextInputEditText portinput;
    private TextInputEditText usernameinput;
    private TextInputEditText passwordinput;
    private TextInputEditText displayNameinput;
    private TextInputEditText eMailinput;

    private MessagesDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");

        // smtpAddressinput = (TextInputEditText) findViewById(R.id.smtpTV);
        // portinput = (TextInputEditText) findViewById(R.id.portTV);
        usernameinput = (TextInputEditText) findViewById(R.id.usernameTV);
        passwordinput = (TextInputEditText) findViewById(R.id.passwordTV);
        displayNameinput = (TextInputEditText) findViewById(R.id.displayNameTV);
        eMailinput = (TextInputEditText) findViewById(R.id.eMailTV);


    }


    public void makeNewAccountFromInput(View view) throws MessagingException {
        //String smtpAdress = smtpAddressinput.getText().toString();
        //String port = portinput.getText().toString();

        final String userName = usernameinput.getText().toString();
        final String password = passwordinput.getText().toString();
        final String displayName = displayNameinput.getText().toString();
        final String email = eMailinput.getText().toString();

        Account account;
        dbHandler = new MessagesDBHandler(this);

        if (email.contains("@gmail.com")) {
            final String smtpPort = "465";
            final String smtphost = "smtp.gmail.com";
            final String imaphost = "imap.googlemail.com";


            AuthenticateMail checkExistance = (AuthenticateMail) new AuthenticateMail(imaphost, email, password, new AuthenticateMail.AsyncResponse() {
                @Override
                public void processFinish(Boolean isConnected) {


                    Log.d("iConnRegisterAc", isConnected.toString());

                    if (isConnected) {
                        Account account = new Account(smtpPort, smtpPort, userName, password, displayName, email, smtphost, imaphost);
                        dbHandler.addAccount(account);
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Could not connect your account, please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).execute();


        } else if (email.contains("@hotmail.com") || email.contains("outlook.com")) {
            final String smtpPort = "587";
            final String imaphost = "outlook.office365.com";
            final String smtphost = "smtp.office365.com";

            AuthenticateMail checkExistance = (AuthenticateMail) new AuthenticateMail(imaphost, email, password, new AuthenticateMail.AsyncResponse() {
                @Override
                public void processFinish(Boolean isConnected) {

                    Log.d("iConnRegisterAc", isConnected.toString());
                    if (isConnected) {
                        Account account = new Account(smtpPort, smtpPort, userName, password, displayName, email, smtphost, imaphost);
                        dbHandler.addAccount(account);
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect username/password, please try again.", Toast.LENGTH_SHORT).show();

                    }
                }
            }).execute();

        } else {
            Toast.makeText(this, "Incorrect username/password, please try again.", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
        } catch (Exception e) {
            Log.e("Account error", "Can't make new account");
        }


    }
}
