package com.example.afsah.climatecalc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    Button button1;
    Button button2;

    TextView textView2;
    TextView textView4;
    TextView textView6;
    TextView textView8;
    TextView textView10;
    TextView textView12;
    TextView textView14;
    TextView textView16;
    TextView textView18;
    TextView textView20;

    EditText editText;

    double lat = 6.9;
    double lon = 6.9;

    getLocation getLocation_obj;
    private GoogleApiClient mGoogleApiClient;

   // URL url;

    String place;
  //  String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateUI();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        getLocation_obj = new getLocation(mGoogleApiClient);
    }
    public void activateUI(){

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView6 = (TextView)findViewById(R.id.textView6);
        textView8 = (TextView)findViewById(R.id.textView8);
        textView10 = (TextView)findViewById(R.id.textView10);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView14 = (TextView)findViewById(R.id.textView14);
        textView16 = (TextView)findViewById(R.id.textView16);
        textView18 = (TextView)findViewById(R.id.textView18);
        textView20 = (TextView)findViewById(R.id.textView20);
        editText  = (EditText)findViewById(R.id.editText);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                textView2.setText("Loading...");
                try {
                    place = editText.getText().toString();
                    getData objclass = new getData(place);
                    objclass.execute(place).get();

                    double temp = Double.parseDouble(objclass.temp);
                    temp = temp - 273.15;
                    textView2.setText(Double.toString(temp));

                    double pressure = Double.parseDouble(objclass.pressure);
                    pressure *=0.1;
                    textView4.setText(Double.toString(pressure));

                    textView6.setText(objclass.humidity);

                    double temp_min = Double.parseDouble(objclass.temp_min);
                    temp_min = temp_min - 273.15;
                    textView8.setText(Double.toString(temp_min));

                    double temp_max = Double.parseDouble(objclass.temp_max);
                    temp_max = temp_max - 273.15;
                    textView10.setText(Double.toString(temp_max));

                    textView12.setText(objclass.speed);
                    textView14.setText(objclass.sunrise);
                    textView16.setText(objclass.sunset);
                    textView18.setText(objclass.description);
                    textView20.setText(objclass.country);

                    editText.setText(objclass.name);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {

        System.out.println("inside onConnected***************");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("no permission****************");

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            System.out.println("obj **********************"+ mLastLocation);
            System.out.println("lat**********************" + mLastLocation.getLatitude());
            System.out.println("lon***********************" + mLastLocation.getLongitude());
            //lat.setText(String.valueOf(mLastLocation.getLatitude()));
            //lon.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean isGPSEnabled() {
        LocationManager cm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return cm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void onButtonClick(View v){
        System.out.println("inside button click ***********");
        if(v.getId() == R.id.button2){

            System.out.println("in gps button ***************");
            if(!isGPSEnabled()){
                new AlertDialog.Builder(this)
                        .setMessage("Please activate your GPS Location!")
                        .setCancelable(false)
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                //textLat.setText(String.valueOf(lat));
                //textLong.setText(String.valueOf(lon));

                System.out.println("lat**********************" +  lat);
                System.out.println("lon***********************" + lon);
            }

        }
    }
}
