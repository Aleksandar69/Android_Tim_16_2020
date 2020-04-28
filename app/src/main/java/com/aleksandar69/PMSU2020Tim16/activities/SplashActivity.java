package com.aleksandar69.PMSU2020Tim16.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aleksandar69.PMSU2020Tim16.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new InitTask().execute();
    }


    private class InitTask extends AsyncTask<Void, Void, Boolean> {
        private long startTime;

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            isNetworkAvailable();

            return isNetworkAvailable();
        }

        private Boolean isNetworkAvailable() {
            long timeLeft = SPLASH_TIME_OUT - (System.currentTimeMillis() - startTime);
            if (timeLeft < 0) timeLeft = 0;
            SystemClock.sleep(timeLeft);

            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mMobData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected() || mMobData.isConnected()) {
                startMainActivity();
                return true;
            } else {
                //Toast.makeText(getApplicationContext(), "Niste povezani na internet", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {

            if (!success) {
                CharSequence text = "Niste povezani na internet";
                //Toast.makeText(getApplicationContext(), "Niste povezani na internet", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_splash), text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Open WiFi Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);

                    }
                });
                snackbar.show();
            }
        }
    }
    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
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
