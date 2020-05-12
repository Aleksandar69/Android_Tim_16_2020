package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.Data;
import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.javamail.SendEmail;
import com.aleksandar69.PMSU2020Tim16.javamail.SendMultipartEmail;
import com.aleksandar69.PMSU2020Tim16.models.Account;
import com.aleksandar69.PMSU2020Tim16.models.Message;
import com.google.android.material.textfield.TextInputEditText;

public class CreateEmailActivity extends AppCompatActivity {

    private static final int PERM_CODE = 125;


    TextInputEditText fromEditBox;
    TextInputEditText toEditBox;
    TextInputEditText ccEditBox;
    TextInputEditText bccEditBox;
    TextInputEditText subjectEditBox;
    EditText contentEditBox;

    SharedPreferences mSharedPreferences;

    String filePath = null;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_email);

        fromEditBox = (TextInputEditText) findViewById(R.id.email_from);
        toEditBox = (TextInputEditText) findViewById(R.id.email_to);
        ccEditBox = (TextInputEditText) findViewById(R.id.email_cc);
        bccEditBox = (TextInputEditText) findViewById(R.id.email_bcc);
        subjectEditBox = (TextInputEditText) findViewById(R.id.email_subject);
        contentEditBox = (EditText) findViewById(R.id.email_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_email);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("New Message");

        mSharedPreferences = getSharedPreferences(LoginActivity.myPreferance, Context.MODE_PRIVATE);

        if (Data.isForward == true) {
            getForwardContent();
            Data.isForward = false;
        }

        if(Data.isReply == true){
            getReplyContent();
            Data.isReply = false;
        }
        if(Data.isReplyToAll == true){
            getReplyToAllContent();
            Data.isReplyToAll = false;
        }
    }

    public void getReplyContent(){
        String from = (String) getIntent().getExtras().get(Data.REPLY_FROM);
        String content = (String) getIntent().getExtras().get(Data.REPLY_CONTENT);
        toEditBox.setText(from);
        contentEditBox.setText("__________________\n\n" +  content + "\n__________________ \n\n");

    }

    public void getReplyToAllContent(){
        String from = (String) getIntent().getExtras().get(Data.REPLY_TO_ALL_FROM);
        String to = (String) getIntent().getExtras().get(Data.REPLY_TO_ALL_TO);
        String content = (String) getIntent().getExtras().get(Data.REPLY_TO_ALL_CONTENT);

        toEditBox.setText(from+";"+to);

        contentEditBox.setText("__________________\n\n" +  content + "\n__________________ \n\n");



    }

    public void getForwardContent() {
        String content = (String) getIntent().getExtras().get(Data.EMAIL_FORWARD_EXTRA);
        String from = (String) getIntent().getExtras().get(Data.FORWARD_FROM_EXTRA);
        String subject = (String) getIntent().getExtras().get(Data.FORWARD_SUBJECT_EXTRA);
        String cc = (String) getIntent().getExtras().get(Data.FORWARD_CC_EXTRA);
        String date = (String) getIntent().getExtras().get(Data.FORWARD_DATE_EXTRA);
        String to = (String) getIntent().getExtras().get(Data.FORWARD_TO_EXTRA);

        subjectEditBox.setText("FWD: " + subject);

        contentEditBox.setText("From: " + from +
                "\n To: " + to +
                "\n Sent: " + date +
                "\n CC:" + cc +
                "\n Subject: " + subject +
                "\n" +
                "\n" +
                "\n"
                + content);
    }


    public void openFileClick(View view) {
        openFile("*/*");
    }

    public void openFile(String mimeType) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");

        try {
            startActivityForResult(sIntent, PERM_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.email_cancel_button:
                Intent intent = new Intent(this, EmailsActivity.class);
                Toast.makeText(this, "Message Creation Cancelled", Toast.LENGTH_LONG).show();
                startActivity(intent);
                return true;
            case R.id.email_send_button:
                MessagesDBHandler dbHandler = new MessagesDBHandler(this);


                Message message = new Message(fromEditBox.getText().toString(), toEditBox.getText().toString(),
                        ccEditBox.getText().toString(), bccEditBox.getText().toString(), subjectEditBox.getText().toString(), contentEditBox.getText().toString());
                message.setLogged_user_id(mSharedPreferences.getInt(LoginActivity.userId, -1));
                /*  dbHandler.addMessage(message);*/

                if (filePath != null) {
                    SendMultipartEmail sendMessage = new SendMultipartEmail(this, message.getTo(), message.getSubject(), message.getContent(), filePath, message.getCc(), message.getBcc());
                    sendMessage.execute();
                } else {
                    SendEmail sendMessage = new SendEmail(message.getSubject(), message.getContent(), message.getCc(), message.getBcc(), message.getTo());
                    sendMessage.execute();
                }
                startActivity(new Intent(this, EmailsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERM_CODE) {
            if (null != data) { // checking empty selection
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (null != data.getClipData()) { // checking multiple selection or not
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            filePath = data.getClipData().getItemAt(i).getUri().getPath();
                            //uriList.add(uri);
                            Log.d("PATH AT: ", filePath);
                            Log.d("Path with envionment:", Environment.getExternalStorageDirectory().toString());
                            Log.d("Path with absolute:", Environment.getExternalStorageDirectory().getAbsolutePath());
                            Log.d("Path with parent:", Environment.getExternalStorageDirectory().getParent());
                        }
                    } else {
                        filePath = data.getData().getPath();
                        //uriList.add(uri);
                        Log.d("PATH AT: ", filePath);
                        Log.d("Path with functions:", Environment.getExternalStorageDirectory().getAbsolutePath() + "/5113lJZJQuL.JPG");
                        Log.d("Path with envionment:", Environment.getExternalStorageDirectory().toString());
                        Log.d("Path with absolute:", Environment.getExternalStorageDirectory().getAbsolutePath());
                        Log.d("Path with parent:", Environment.getExternalStorageDirectory().getParent());
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "An error has occured: API level requirements not met", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



/*    public void sendMessage(View view) {
        MessagesDBHandler dbHandler = new MessagesDBHandler(this);

       Message message = new Message(fromEditBox.getText().toString(), toEditBox.getText().toString(),
                ccEditBox.getText().toString(), bccEditBox.getText().toString(),subjectEditBox.getText().toString(), contentEditBox.getText().toString());

       message.setLogged_user_id(mSharedPreferences.getInt(LoginActivity.userId, -1));

        dbHandler.addMessage(message);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_email, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onStart() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
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
