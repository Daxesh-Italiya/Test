package com.TST.Crash_Investigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.TST.Crash_Investigation.Function.Case_DB;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

import static android.service.controls.ControlsProviderService.TAG;

public class New_Case extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    //------------- Object Define -----------------
    private EditText Case_Name,Fir_Number,Area;
    private LinearLayout DateLayout,Get_Location_btn_layout,Get_Location_btn;
    private TextView Date,Location,Location_facing_warning;
    FusedLocationProviderClient fusedLocationProviderClient;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    //------------- Local Database -----------------
    private Case_DB db;

    //--------------- Other Variable -------------------
    private int Case_Index;

    DatePickerDialog datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__case);
        Define();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

//        locationManager = (LocationManager) getSystemService(New_Case.LOCATION_SERVICE);
    }

    //------------- Initialize Object -----------------
    private void Define() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.setMessage("Fetching current Location");
        progressDialog.show();
        db=new Case_DB(this);
        Case_Name=(EditText)findViewById(R.id.Case_name);
        Date=(TextView) findViewById(R.id.Date);
        Area=(EditText)findViewById(R.id.Area);
        Fir_Number=(EditText)findViewById(R.id.FIR_Number);
        Location=(TextView) findViewById(R.id.Location);
        Location_facing_warning=(TextView) findViewById(R.id.Location_facing_warning);
        Location_facing_warning.setVisibility(View.GONE);
        DateLayout=(LinearLayout) findViewById(R.id.DateLayout);
        Get_Location_btn=(LinearLayout) findViewById(R.id.Get_Location_btn);
        Get_Location_btn_layout=(LinearLayout) findViewById(R.id.Get_Location_btn_layout);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Get_Location_btn_layout.setVisibility(View.GONE);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

//        Get_Location_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ActivityCompat.checkSelfPermission(New_Case.this
//                        , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//
//                }else
//                {
//                    ActivityCompat.requestPermissions(New_Case.this
//                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//                }
//            }
//        });



        DateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(New_Case.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

        }else{
            showGPSDisabledAlertToUser();
            Toast.makeText(New_Case.this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
        }



    }

    private void getLocation() {

//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
//            @Override
//            public void onComplete(@NonNull Task<android.location.Location> task) {
//
//                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                    Toast.makeText(New_Case.this, "GPS is Enabled in your davide", Toast.LENGTH_SHORT).show();
//                    Location location=task.getResult();
//
//                    try {
//                        Toast.makeText(New_Case.this,String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
//                    }catch (Exception e)
//                    {
//                        Toast.makeText(New_Case.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//
//                    if(location!=null)
//                    {
//                        try {
//                            Geocoder geocoder=new Geocoder(New_Case.this,
//                                    Locale.getDefault());
//                            List<Address> addresses=geocoder.getFromLocation(
//                                    location.getLatitude(),location.getLongitude(),1
//                            );
//                            String Ans="Longitude : "+addresses.get(0).getLongitude()+",\nLatitude : "+addresses.get(0).getLatitude();
//                            Location.setText(Ans);
//                            Get_Location_btn_layout.setVisibility(View.GONE);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            Toast.makeText(New_Case.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }else{
//                    showGPSDisabledAlertToUser();
//                    Toast.makeText(New_Case.this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
//                }
//
//
//
//            }
//        });



    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
            String Ans="Longitude : "+longitude+",\nLatitude : "+latitude;
            Location.setText(Ans);
            Get_Location_btn_layout.setVisibility(View.GONE);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
                Location_facing_warning.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

//             Toast.makeText(this, "Location not Detected Automatically", Toast.LENGTH_SHORT).show();

        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(500);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
        Toast.makeText(New_Case.this,"Connection Suspended",Toast.LENGTH_LONG).show();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
        Toast.makeText(New_Case.this,"Connection failed. Error: " + connectionResult.getErrorCode(),Toast.LENGTH_LONG).show();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
//        Toast.makeText(New_Case.this,"Connected",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if(Location_facing_warning.getVisibility()==View.VISIBLE)
        {
            Location_facing_warning.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

        if(Location.getText().toString().isEmpty())
        {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String Ans="Longitude : "+longitude+",\nLatitude : "+latitude;
            Location.setText(Ans);
        }


//        Toast.makeText(New_Case.this,String.valueOf(location.getLatitude()),Toast.LENGTH_LONG).show();
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    //------------- Onclick Events ---------------------

    public void Add_Data(View view){

        if(Case_Name.getText().toString().isEmpty())
        {
            Toast.makeText(New_Case.this,"Please Enter Case Name",Toast.LENGTH_SHORT).show();

        }else if(Fir_Number.getText().toString().isEmpty())
        {
            Toast.makeText(New_Case.this,"Please Enter Fire Number",Toast.LENGTH_SHORT).show();
        }else if(Date.getText().toString().isEmpty())
        {
            Toast.makeText(New_Case.this,"Please Enter Date",Toast.LENGTH_SHORT).show();
        }else if(Area.getText().toString().isEmpty())
        {
            Toast.makeText(New_Case.this,"Please Enter Area",Toast.LENGTH_SHORT).show();
        }else if(Location.getText().toString().isEmpty())
        {
            Toast.makeText(New_Case.this,"Please Enter Location",Toast.LENGTH_SHORT).show();
        }else
        {
            Cursor res=db.Get_Case_data();
            int i=0;
            while (res.moveToNext())
            {
                i=res.getInt(0);
            }

            boolean result = db.Insert_Case_data(i+1,Case_Name.getText().toString(),Fir_Number.getText().toString(),Date.getText().toString(),Area.getText().toString(),Location.getText().toString());

            if (result)
            {
                Toast.makeText(New_Case.this,"New Case Started",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(New_Case.this,Starting_Slide.class)
                        .putExtra("id",i+1)
                        .putExtra("heading",Case_Name.getText().toString())
                        .putExtra("source","New"));

                finish();
            }else
            {
                Toast.makeText(New_Case.this,"Getting Error For start new Case",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void showGPSDisabledAlertToUser(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                finish();
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

//    public void Location_click(View view)
//    {
//        Toast.makeText(New_Case.this,Location.getText().toString(),Toast.LENGTH_LONG).show();
//        if(Location.getText().toString().isEmpty())
//        {
//            Get_Location_btn_layout.setVisibility(View.VISIBLE);
//        }else
//        {
//            Get_Location_btn_layout.setVisibility(View.GONE);
//        }
//
//    }

    public void BackGround_click(View view)
    {
        Get_Location_btn_layout.setVisibility(View.GONE);
    }



}
