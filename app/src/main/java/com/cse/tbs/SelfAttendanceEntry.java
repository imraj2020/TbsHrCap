package com.cse.tbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelfAttendanceEntry extends AppCompatActivity implements LocationListener {
    TextView text_location, text_location_latitude,text_location_longitude, today_date, today_time;
    LocationManager locationManager;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_attendance_entry);
        text_location = findViewById(R.id.text_location);
        text_location_latitude = findViewById(R.id.text_location_latitude);
        text_location_longitude = findViewById(R.id.text_location_longitude);
        today_date = findViewById(R.id.today_date);
        today_time = findViewById(R.id.today_time);

        //Date formet
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        today_date.setText(date);



        //Time Formet
        Date d=new Date();
        SimpleDateFormat stf=new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = stf.format(d);
        today_time.setText(currentDateTimeString);

        //Progress dilogue start
        progressDialog = new ProgressDialog(SelfAttendanceEntry.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Getting Location Please Wait..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);
        progressDialog.show();



        //runtime permission
        if (ContextCompat.checkSelfPermission(SelfAttendanceEntry.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SelfAttendanceEntry.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }


        getlocation();
//        button_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getlocation();
//            }
//        });
    }

// alert dialogue while gps not enable
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Please Enable Internet and Gps first", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(SelfAttendanceEntry.this, MainActivity.class);
                        startActivity(i);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @SuppressLint("MissingPermission")
    private void getlocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,SelfAttendanceEntry.this);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Please check internet connection and turn on gps", Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onLocationChanged(Location location) {


        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(SelfAttendanceEntry.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            text_location.setText(address);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String stringdouble1= Double.toString(latitude);
            text_location_latitude.setText(stringdouble1);
            String stringdouble2= Double.toString(longitude);
            text_location_longitude.setText(stringdouble2);
            progressDialog.dismiss();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }


    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}