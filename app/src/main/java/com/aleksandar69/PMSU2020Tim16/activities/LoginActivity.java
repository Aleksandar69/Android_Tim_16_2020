package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.Data;
import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Account;
import com.aleksandar69.PMSU2020Tim16.services.EmailSyncService;
import com.aleksandar69.PMSU2020Tim16.services.EmailsJobSchedulerSyncService;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTV;
    private ListView testView;
    private EditText usernameText;
    private EditText passwordText;
    MessagesDBHandler dbHandler;

    SharedPreferences sharedPreferences;
    //public static String myPreferance = "mypref";
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        registerTV = (TextView) findViewById(R.id.register);
        testView = (ListView) findViewById(R.id.test_accounts);
        usernameText = (EditText) findViewById(R.id.username_field);
        passwordText = (EditText) findViewById(R.id.password_field);
        dbHandler = new MessagesDBHandler(this);
        mProgressDialog = new ProgressDialog(this);

        //sharedPreferences = getSharedPreferences(myPreferance, Context.MODE_PRIVATE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

/*
        Data.syncTime = sharedPreferences.getString(getString(R.string.pref_syncConnectionType),"60000" );
        Data.allowSync = sharedPreferences.getBoolean(getString((R.string.pref_sync)),false);
        Data.prefSort = sharedPreferences.getString(getString(R.string.pref_sort),"descending");
*/


        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        fillAccountsList();
    }


    public void onLoginButtonClicked(View view) {


        Data.account = dbHandler.findAccount(usernameText.getText().toString(), passwordText.getText().toString());


        if (Data.account != null) {

            mProgressDialog.setMessage("Logging you in, please wait.");
            mProgressDialog.show();
            mProgressDialog.setTitle("Logging in");

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.cancel();
                }
            };

            Handler pdCancel = new Handler();
            pdCancel.postDelayed(runnable, 3000);

            Intent intent = new Intent(this, EmailsActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Data.userEmail, Data.account.geteMail());
            editor.putString(Data.userPassworrd, Data.account.getPassword());
            editor.putInt(Data.userId, Data.account.get_id());
            editor.commit();
        } else {
            Toast.makeText(this, "The credentials you entered are not valid", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void tempButtClick(View view) {

        mProgressDialog.setMessage("Logging you in, please wait.");
        mProgressDialog.setTitle("Logging in");
        mProgressDialog.show();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mProgressDialog.cancel();
                Intent intent = new Intent(LoginActivity.this, EmailsActivity.class);
                startActivity(intent);
            }
        };

        Handler pdCancel = new Handler();
        pdCancel.postDelayed(runnable, 6000);


/*        Intent intent = new Intent(this, EmailsActivity.class);
        startActivity(intent);*/
/*        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(userId, account.get_id());
        editor.commit();*/
    }


    //PRIVREMENO
    public void fillAccountsList() {

        dbHandler = new MessagesDBHandler(this);

        List<Account> accounts = dbHandler.queryAccounts();


        Account[] listAccounts = new Account[accounts.size()];
        listAccounts = accounts.toArray(listAccounts);

        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listAccounts);
            testView.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(this, "Object null", Toast.LENGTH_LONG).show();
        }


/*        List<Message> messages = dbHandler.queryAllNoContentP();

        Message[] listMessages = new Message[messages.size()];
        listMessages = messages.toArray(listMessages);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMessages);
        testView.setAdapter(adapter);*/
    }

    @Override
    protected void onStart() {

        fillAccountsList();
        super.onStart();

    }

    @Override
    protected void onResume() {
        fillAccountsList();
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
/*        Intent i = new Intent(this, EmailsForegroundService.class);
        startService(i);*/
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
