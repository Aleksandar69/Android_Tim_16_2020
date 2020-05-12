package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.Data;
import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.javamail.DeleteEmail;
import com.aleksandar69.PMSU2020Tim16.models.Attachment;
import com.aleksandar69.PMSU2020Tim16.models.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class EmailActivity extends AppCompatActivity {


    private TextView tvFrom;
    private TextView tvSubject;
    private TextView tvContent;
    private TextView tvCC;
    private TextView tvTo;
    private Button buttonAttach;
    private Attachment attachment;


    private int itemId;
    private MessagesDBHandler emailsDb;
    private String emailId;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        tvFrom = (TextView) findViewById(R.id.from_tv);
        tvSubject = (TextView) findViewById(R.id.subject_tv);
        tvContent = (TextView) findViewById(R.id.content);
        tvCC = (TextView) findViewById(R.id.cc_tv);
        tvTo = (TextView) findViewById(R.id.to_tv);
        Button buttonAttach = (Button) findViewById(R.id.attachments);

        emailsDb = new MessagesDBHandler(this);


        emailId = (String) getIntent().getExtras().get(Data.MESS_ID_EXTRA);
        itemId = Integer.parseInt(emailId);
        message = emailsDb.findMessage(itemId);

        attachment = emailsDb.queryAttachForMessage(message.getAttachmentId());


        loadEmail();

        Toolbar toolbar = (Toolbar) findViewById(R.id.email_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Message");

        if (attachment == null) {
            buttonAttach.setVisibility(View.GONE);
        }
    }



/*        Intent chooserIntent;
        if (getPackageManager().resolveActivity(sIntent, 0) != null) {
            // it is device with Samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent,PERM_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }*/


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_email, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void decodeOnClick(View view) throws IOException {

        if (attachment != null) {

            String fileContent = decodeBase64(attachment.getContent());

            // String file = decodeBase64(message.getAttachments());

            byte[] imgBytes = Base64.decode(attachment.getContent(), Base64.DEFAULT);

            File storage = Environment.getExternalStorageDirectory();
            File dir = new File(storage.getAbsolutePath());

            Random random = new Random();

            String randomStr = String.valueOf(random.nextInt());

            File file1 = new File(dir, attachment.getFileName());

            FileOutputStream fos = new FileOutputStream(file1);
            fos.write(imgBytes);
            fos.flush();

            Toast.makeText(this, "Attachment Saved To Root", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Your message has no attachment included.", Toast.LENGTH_SHORT).show();
        }
    }


    private String decodeBase64(String coded) {
        byte[] valueDecoded = new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
        }
        return new String(valueDecoded);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String content = message.getContent();
        String from = message.getFrom();
        String subject = message.getSubject();
        String cc = message.getCc();
        String date = message.getDateTime();
        String to = message.getTo();
        switch (item.getItemId()) {
            case R.id.email_delete_button:
                emailsDb.deleteMessage(itemId);
                DeleteEmail deleteEmail = new DeleteEmail(this, message.get_id());
                deleteEmail.execute();
                startActivity(new Intent(this, EmailsActivity.class));
                return true;
            case R.id.forward:
                Intent intentForwrad = new Intent(this, CreateEmailActivity.class);
                intentForwrad.putExtra(Data.EMAIL_FORWARD_EXTRA, content);
                intentForwrad.putExtra(Data.FORWARD_FROM_EXTRA, from);
                intentForwrad.putExtra(Data.FORWARD_SUBJECT_EXTRA, subject);
                intentForwrad.putExtra(Data.FORWARD_CC_EXTRA, cc);
                intentForwrad.putExtra(Data.FORWARD_DATE_EXTRA, date);
                intentForwrad.putExtra(Data.FORWARD_TO_EXTRA, to);
                Data.isForward = true;
                startActivity(intentForwrad);
                return true;
            case R.id.reply_button:
                Intent intentReply = new Intent(this, CreateEmailActivity.class);
                intentReply.putExtra(Data.REPLY_FROM, from);
                intentReply.putExtra(Data.REPLY_CONTENT, content);
                Data.isReply = true;
                startActivity(intentReply);
                return true;
            case R.id.reply_to_all_button:
                Intent intentReplyToAll = new Intent(this, CreateEmailActivity.class);
                intentReplyToAll.putExtra(Data.REPLY_TO_ALL_FROM, from);
                intentReplyToAll.putExtra(Data.REPLY_TO_ALL_TO, to);
                intentReplyToAll.putExtra(Data.REPLY_TO_ALL_CONTENT, content);
                Data.isReplyToAll = true;
                startActivity(intentReplyToAll);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadEmail() {

        tvFrom.setText(message.getFrom());
        tvSubject.setText(message.getSubject());
        tvContent.setText(message.getContent());
        tvCC.setText(message.getCc());
        tvTo.setText(message.getTo());

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
