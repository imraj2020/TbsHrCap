package com.cse.tbs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnactivity, attandancerentry, leaverequest,advsalary;
    boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnactivity = findViewById(R.id.buttonsecondactivity);
        attandancerentry = findViewById(R.id.attandance_regularization_entry);
        leaverequest = findViewById(R.id.leave_request);
        advsalary = findViewById(R.id.advance_salary);









        //boolean for network state
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;

            Toast.makeText(getApplicationContext(), "Please Enable Internet Connection", Toast.LENGTH_LONG).show();
            //Check if internet is enable
            buildAlertMessageNoInternet();
        }
        btnactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SelfAttendanceEntry.class);
                startActivity(i);
            }
        });
        attandancerentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AttandanceRegularizitionEntry.class);
                startActivity(i);
            }
        });

        leaverequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaveRequest.class);
                startActivity(i);
            }
        });

        advsalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdvanceSalary.class);
                startActivity(i);
            }
        });

    }
    private void buildAlertMessageNoInternet() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Internet seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Please Enable Internet and Gps first", Toast.LENGTH_LONG).show();
                        //Intent i = new Intent(SelfAttendanceEntry.this, MainActivity.class);
                        //startActivity(i);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}