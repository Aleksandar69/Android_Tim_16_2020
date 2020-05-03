package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Message;

import org.w3c.dom.Text;

public class EmailActivity extends AppCompatActivity {

    public static final String MESS_ID_EXTRA = "messId";

    TextView tvFrom;
    TextView tvSubject;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        tvFrom = (TextView) findViewById(R.id.from_tv);
        tvSubject = (TextView) findViewById(R.id.subject_tv);
        tvContent = (TextView) findViewById(R.id.content);

        loadEmail();

        Toolbar toolbar = (Toolbar) findViewById(R.id.email_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Message");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_email, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void loadEmail(){
        String emailId = (String) getIntent().getExtras().get(MESS_ID_EXTRA);
        int intId = Integer.parseInt(emailId);
        MessagesDBHandler emailsDb = new MessagesDBHandler(this);

        Message message = emailsDb.findMessage(intId);
        tvFrom.setText(message.getFrom());
        tvSubject.setText(message.getSubject());
        tvContent.setText(message.getContent());

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
