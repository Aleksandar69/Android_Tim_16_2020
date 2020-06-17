package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.aleksandar69.PMSU2020Tim16.Data;
import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.adapters.ContactsArrayAdapter;
import com.aleksandar69.PMSU2020Tim16.adapters.ContactsBaseAdapter;
import com.aleksandar69.PMSU2020Tim16.adapters.ContactsCursorAdapter;
import com.aleksandar69.PMSU2020Tim16.adapters.RecyclerViewContactsAdapter;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Contact;
import com.aleksandar69.PMSU2020Tim16.models.Photo;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ListView.OnItemClickListener {
    private ProgressBar mProgessCircle;
    MessagesDBHandler handler;
    private ListView contactsListView;
    Context context;
    ContactsBaseAdapter adapter = null;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

       // mProgessCircle = findViewById(R.id.progress_bar_circle);
        //inicijalizacija listview-a
        contactsListView = (ListView) findViewById(R.id.recycler_v);
        contacts = new ArrayList<>();
        adapter = new ContactsBaseAdapter(this, R.layout.row, contacts);
        context = this;
        handler = new MessagesDBHandler(context);
        contactsListView.setAdapter(adapter);

        //get data from sqlite
        Cursor cursor = handler.getData("SELECT * FROM CONTACT");
        contacts.clear();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String first = cursor.getString(1);
            String last = cursor.getString(2);
            String display = cursor.getString(3);
            String email = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            contacts.add(new Contact(id, first,last,display,email,image));
        }
        adapter.notifyDataSetChanged();

        contactsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ContactsActivity.this);

                dialog.setTitle("Choose an action: ");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0 ) {
                            //update
                            Cursor c = handler.getData("SELECT _id FROM CONTACT");
                            ArrayList<Integer> arrayID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrayID.add(c.getInt(0));
                            }
                            showDialogUpdate(ContactsActivity.this, arrayID.get(position));
                            Toast.makeText(getApplicationContext(), "Update.." , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Delete.." , Toast.LENGTH_SHORT).show();
                            //delete
                            //update
                            Cursor c = handler.getData("SELECT _id FROM CONTACT");
                            ArrayList<Integer> arrayID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrayID.add(c.getInt(0));
                            }
                            showDialogDelete(arrayID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        //inicijalizacija liste kontakata
        //contacts = new ArrayList<>();

        //prikaz liste kontakata iz baze
        //contacts = handler.getListOfAllContacts();
        //ContactsArrayAdapter contactsArrayAdapter = new ContactsArrayAdapter(this,R.layout.row,contacts);
        //contactsListView.setAdapter(contactsArrayAdapter);

        //kad kliknem na pojedinacan kontakt, on mi se prikazuje u ContactActivity-u sa svojim vrijednostima

        //contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
          //  public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            //    final Contact contact = contacts.get(position);
              //  Intent intent = new Intent(ContactsActivity.this,ContactActivity.class);
                //intent.putExtra("eid",contact.get_id());
                //intent.putExtra("efirst", contact.getFirst());
                //intent.putExtra("elast",contact.getLast());
                //intent.putExtra("edisplay",contact.getDisplay());
                //intent.putExtra("eemail",contact.getEmail());
            //    startActivity(intent);
            //}
        //});

        Toolbar toolbar = findViewById(R.id.contacts_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_get);
        actionBar.setTitle("Contacts");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_contacts);
        navigationView.setNavigationItemSelectedListener(this);
    }

    ImageView imageViewContact;
    //provjera za update
    private void showDialogUpdate(Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_contact);

        imageViewContact = (ImageView) dialog.findViewById(R.id.image_single_contact);
        final TextInputEditText first = (TextInputEditText) dialog.findViewById(R.id.new_contact_firstnamee);
        final TextInputEditText last = (TextInputEditText) dialog.findViewById(R.id.new_contact_lastnamee);
        final TextInputEditText display = (TextInputEditText) dialog.findViewById(R.id.new_contact_displaynamee);
        final TextInputEditText email = (TextInputEditText) dialog.findViewById(R.id.new_contact_emaill);
        Button updateButton = (Button) dialog.findViewById(R.id.new_button_update);

        dialog.getWindow();
        dialog.show();

        imageViewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zahtjev za photo library
                ActivityCompat.requestPermissions(ContactsActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},888);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    handler.updateData(
                            position,
                            first.getText().toString().trim(),
                            last.getText().toString().trim(),
                            display.getText().toString().trim(),
                            email.getText().toString().trim(),
                            CreateContactActivity.imageViewToByte(imageViewContact)
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    Log.e("Update error : ", error.getMessage());
                }
                updateContactsList();
            }
        });
    }

    private void showDialogDelete(int idContact) {
        AlertDialog.Builder dialogDelete = new  AlertDialog.Builder(ContactsActivity.this);
        dialogDelete.setTitle("Warning! ");
        dialogDelete.setMessage("Are you sure you want to delete this contact ? ");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void updateContactsList() {
        //get data from sqlite
        Cursor cursor = handler.getData("SELECT * FROM CONTACT");
        contacts.clear();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String first = cursor.getString(1);
            String last = cursor.getString(2);
            String display = cursor.getString(3);
            String email = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            contacts.add(new Contact(id, first,last,display,email,image));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //when we pick out file this is called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 888 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewContact.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Picasso.get().load(imageUri).into(imageEdit);
        }
    }

    public void onProfileClicked(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_contact:
                Intent intent = new Intent(this, CreateContactActivity.class);
                Toast.makeText(this, "Create new contact selected", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        switch (id) {

            case R.id.nav_folders:
                intent = new Intent(this, FoldersActivity.class);
                break;
            case R.id.nav_inbox:
                intent = new Intent(this, EmailsActivity.class);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                intent = new Intent(this, ContactsActivity.class);
        }

        startActivity(intent);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        String contact_id = cursor.getString(0);

        Intent intent = new Intent(this,ContactActivity.class);
        intent.putExtra(Data.CONTACT_ID_EXTRA, contact_id);
        startActivity(intent);
    }
}
