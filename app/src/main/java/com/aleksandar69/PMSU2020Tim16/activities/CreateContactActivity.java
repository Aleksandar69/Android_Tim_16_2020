package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.util.ByteArrayDataSource;

import de.hdodenhof.circleimageview.CircleImageView;
public class CreateContactActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1000;
    private ProgressBar progressBarEdit;
    Button chooseButton;
    Context context;
    public static ImageView imageEdit;
    private TextInputEditText firstNameEdit;
    private TextInputEditText lastNameEdit;
    private TextInputEditText displayNameEdit;
    private TextInputEditText emailEdit;
    public static MessagesDBHandler handler;
    private TextView showContacts;
    private Uri imageUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        context = this;
        handler = new MessagesDBHandler(context);

        showContacts = findViewById(R.id.text_view_show_contacts);
        imageEdit = findViewById(R.id.image_contact);
        chooseButton = findViewById(R.id.choose_image_button);
        firstNameEdit = findViewById(R.id.new_contact_firstname);
        lastNameEdit = findViewById(R.id.new_contact_lastname);
        displayNameEdit = findViewById(R.id.new_contact_displayname);
        emailEdit = findViewById(R.id.new_contact_email);

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        showContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //klik na show contacts iz layout-a
                openContactsActivity();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_create_contact);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
    }

    private void openContactsActivity() {
        Intent intent = new Intent(this,ContactsActivity.class);
        startActivity(intent);
    }

    //metoda sluzi za dobavljanje ekstenzije
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.cancel_contact_creation:
                Intent intent = new Intent(this,ContactsActivity.class);
                Toast.makeText(this,"Contact creation canceled", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            case R.id.save_new_contact:
                String first =  firstNameEdit.getText().toString().trim();
                String last = lastNameEdit.getText().toString().trim();
                String display = displayNameEdit.getText().toString().trim();
                String email = emailEdit.getText().toString().trim();
                byte[] image = imageViewToByte(imageEdit);

                if(first == null) {
                    Toast.makeText(CreateContactActivity.this, "Please, insert first name", Toast.LENGTH_SHORT).show();
                }
                else if (last == null) {
                    Toast.makeText(CreateContactActivity.this, "Please, insert last name", Toast.LENGTH_SHORT).show();
                }
                else if (display == null) {
                    Toast.makeText(CreateContactActivity.this, "Please, insert display name", Toast.LENGTH_SHORT).show();
                }
                else if (email == null) {
                    Toast.makeText(CreateContactActivity.this, "Please, insert email", Toast.LENGTH_SHORT).show();
                }
                else if (image == null) {
                    Toast.makeText(CreateContactActivity.this, "Please, insert image ", Toast.LENGTH_SHORT).show();
                } else {
                    handler.insertData(first,last,display,email,image);
                    Toast.makeText(CreateContactActivity.this, "Contact successfully added !", Toast.LENGTH_SHORT).show();
                }
                /*
                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(last) || !TextUtils.isEmpty(display) ||
                !TextUtils.isEmpty(email) || image !=null) {
                    handler.insertData(first, last,display,email,image);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please fill at least one field! ").setNegativeButton("OK",null).show();
                }
                Toast.makeText(CreateContactActivity.this, "Added successfully", Toast.LENGTH_SHORT).show()
                 */
                //PROVJERA ZA UNOS
                /*
                if(!TextUtils.isEmpty(first) || !TextUtils.isEmpty(last) || !TextUtils.isEmpty(display) || !TextUtils.isEmpty(email)){
                    Contact contact = new Contact(first,last,display,email);
                    handler.addContact(contact);
                    startActivity(new Intent(context,ContactsActivity.class));
                } else{

                }
                 */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray= stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    //when we pick out file this is called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageEdit.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Picasso.get().load(imageUri).into(imageEdit);
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

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