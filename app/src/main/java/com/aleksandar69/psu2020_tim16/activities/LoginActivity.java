package com.aleksandar69.psu2020_tim16.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aleksandar69.psu2020_tim16.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButtonClicked(View view){
        Intent intent = new Intent(this, EmailsActivity.class);
        startActivity(intent);
    }
}
