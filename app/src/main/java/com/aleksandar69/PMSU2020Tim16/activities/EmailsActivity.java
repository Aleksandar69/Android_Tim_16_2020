package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.adapters.EmailsCursorAdapter;
import com.aleksandar69.PMSU2020Tim16.database.MessagesDBHandler;
import com.google.android.material.navigation.NavigationView;

public class EmailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListView.OnItemClickListener {

    Cursor cursor;
    SQLiteDatabase db;
    ListView emails;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);

        emails = (ListView) findViewById(R.id.emails_list_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.emails_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Inbox");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // populateListFromDB();

        sharedPreferences = getSharedPreferences(LoginActivity.myPreferance, Context.MODE_PRIVATE);

        populateList();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        String email_id = cursor.getString(0);

        MessagesDBHandler emailsDb = new MessagesDBHandler(this);

        Intent intent = new Intent(this, EmailActivity.class);
        intent.putExtra(EmailActivity.MESS_ID_EXTRA, email_id);
        startActivity(intent);
    }

    public void populateList() {

       try {

        MessagesDBHandler handler = new MessagesDBHandler(this);

            cursor = handler.getAllMessages(sharedPreferences.getInt(LoginActivity.userId, -1));

            ListView lvItems = (ListView) findViewById(R.id.emails_list_view);

            EmailsCursorAdapter emailsAdapter = new EmailsCursorAdapter(this, cursor);
            emails.setOnItemClickListener(this);
            lvItems.setAdapter(emailsAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void onProfileClicked(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

/*
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onTempButtonClickedFind(View view) {
        EditText editText = (EditText) findViewById(R.id.searchText);
        EditText editText1 = (EditText) findViewById(R.id.searchText2);
        String edit = editText.getText().toString();
        String edit1 = editText1.getText().toString();

        MessagesDBHandler messagesDBHandler = new MessagesDBHandler(this);
        List <Message> messages = messagesDBHandler.findMessagesTest(edit, edit1);


*/
/*        List<Message> messages = messagesDBHandler.queryAllMessages();*//*


        Message[] listMessages = new Message[messages.size()];
        listMessages = messages.toArray(listMessages);

        //Message[] messages = messagesDBHandler.queryAllMessages();


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMessages);
        emails.setOnItemClickListener(this);
        emails.setAdapter(adapter);
    }
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_emails, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_message:
                Intent intent = new Intent(this, CreateEmailActivity.class);
                Toast.makeText(this, "Create email selected", Toast.LENGTH_LONG).show();
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
            case R.id.nav_contacts:
                intent = new Intent(this, ContactsActivity.class);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, LoginActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                break;
            default:
                intent = new Intent(this, EmailsActivity.class);
        }

        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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


}
