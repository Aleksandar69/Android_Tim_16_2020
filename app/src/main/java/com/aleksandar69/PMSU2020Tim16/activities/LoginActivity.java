package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Account;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTV;
    private ListView testView;
    private EditText usernameText;
    private EditText passwordText;
    MessagesDBHandler dbHandler;

    SharedPreferences sharedPreferences;
    public static String myPreferance = "mypref";
    public static String userId = "useridKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerTV = (TextView) findViewById(R.id.register);
        testView = (ListView) findViewById(R.id.test_accounts);
        usernameText = (EditText) findViewById(R.id.username_field);
        passwordText = (EditText) findViewById(R.id.password_field);
        dbHandler = new MessagesDBHandler(this);

        sharedPreferences = getSharedPreferences(myPreferance, Context.MODE_PRIVATE);

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


        Account account = dbHandler.findAccount(usernameText.getText().toString(),passwordText.getText().toString());

        if(account != null) {
            Intent intent = new Intent(this, EmailsActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(userId, account.get_id());
            editor.commit();
        } else{
            Toast.makeText(this,"The credentials you entered are not valid", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void tempButtClick(View view){
        Intent intent = new Intent(this, EmailsActivity.class);
        startActivity(intent);
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
