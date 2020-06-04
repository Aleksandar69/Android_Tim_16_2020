package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.aleksandar69.PMSU2020Tim16.Data;
import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.adapters.RecyclerViewContactsAdapter;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.aleksandar69.PMSU2020Tim16.models.Contact;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ListView.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewContactsAdapter recyclerViewContactsAdapter;
    private ArrayList<Contact> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //inicijalizacija recycler-a
        recyclerView = findViewById(R.id.recycler_v);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MessagesDBHandler handler = new MessagesDBHandler(ContactsActivity.this);

        /*
          //dodavanje kontakta u DB
            Contact elena = new Contact();
            //elena.set_id(1);
            elena.setFirst("Elena");
            elena.setLast("Krunic");
            elena.setDisplay("Elena krunic");
            elena.setEmail("elenakrunic@gmail.com");
            elena.setImageSourceID(1);
            handler.addContacts(elena);

            Contact pera = new Contact();
           // pera.set_id(2);
            pera.setFirst("Pera");
            pera.setLast("Krunic");
            pera.setDisplay("Pera krunic");
            pera.setEmail("perakrunic@gmail.com");
            pera.setImageSourceID(2);
            handler.addContacts(pera);


         */

        /*
        contactArrayList = new ArrayList<>();

            List<Contact> contactList = handler.getAllContactsList();
            for(Contact contact : contactList) {
                Log.d("Elena", "ID" + contact.get_id() + "\n" +
                        "First name " + contact.getFirst() + "\n"
                + "Last name" + contact.getLast() + "\n"
                + "Display name " + contact.getDisplay() + "\n"
                + "Email " + contact.getEmail() + "\n"
                );

                contactArrayList.add(contact);
            }


         */
            //recyclerViewContactsAdapter = new RecyclerViewContactsAdapter(ContactsActivity.this, contactArrayList);
            //recyclerView.setAdapter(recyclerViewContactsAdapter);

            List<Contact> contactList = handler.getAllContactsList();
            recyclerViewContactsAdapter = new RecyclerViewContactsAdapter(this,contactList);
            recyclerView.setAdapter(recyclerViewContactsAdapter);

            Log.d("Elena", "U bazi imate "+ handler.getCount() + " kontakata");

        //sve vezano za toolbar

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

    /*
    public void populateList() {
        cursor = handler.getAllContacts();
        ContactsCursorAdapter contactsAdapter = new ContactsCursorAdapter(this,cursor);
        contacts.setOnItemClickListener(this);
        contacts.setAdapter(contactsAdapter);

    }
*/
    public void onProfileClicked(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
  //      populateList();
        getMenuInflater().inflate(R.menu.menu_contacts, menu);

       //ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);

        //closeButton.setOnClickListener(new View.OnClickListener() {

          //  @Override
            //public void onClick(View v) {
              //  populateList();
                //EditText editText = (EditText) findViewById(R.id.search_src_text);
                //editText.setText("");
            //}
        //});

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




    public void onTempButtonClickedFind(View view){
        onSearchRequested();
    }

/*
    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if(query == "" || query.isEmpty()) {
                Cursor c = handler.getAllContacts();
                ContactsCursorAdapter contactsCursorAdapter = new ContactsCursorAdapter(this,c);
                contacts.setAdapter(contactsCursorAdapter);
            }
            Cursor c = handler.filterContacts(query);
            ContactsCursorAdapter contactsAdapter = new ContactsCursorAdapter(this,c);
            contacts.setAdapter(contactsAdapter);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
        super.onNewIntent(intent);
    }
*/
}
