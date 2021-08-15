package com.TST.Crash_Investigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Starting_Page extends AppCompatActivity {

    //---------- Front end Object ---------------
    private ImageView logo1;
    private TextView heading;
    private LinearLayout Start_button,View_button,logo2;

    private int REQUEST_CODE_PERMISSION =101;
    private String[] REQUEST_PERMISSION=new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE","android.permission.ACCESS_FINE_LOCATION"};


    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting__page);
        Define();
        Animation();
    }

    //*************  Assign Object *************
    private void Define() {
        logo1=(ImageView)findViewById(R.id.logo1);
        logo2=(LinearLayout)findViewById(R.id.logo2);
        heading=(TextView)findViewById(R.id.hading);
        Start_button=(LinearLayout) findViewById(R.id.start);

        View_button=(LinearLayout) findViewById(R.id.view);

        logo1.setVisibility(View.GONE);
        logo2.setVisibility(View.GONE);
        heading.setVisibility(View.GONE);
        Start_button.setVisibility(View.GONE);
        View_button.setVisibility(View.GONE);


    }

    //************  Animation *******************
    private void Animation()
    {
        logo1.setVisibility(View.VISIBLE);
        logo1.setAlpha(0f);
        logo1.setScaleX(0f);
        logo1.setScaleY(0f);
        logo1.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(700);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                heading.setVisibility(View.VISIBLE);
                heading.setAlpha(0f);
                heading.setScaleX(2f);
                heading.setScaleY(2f);
                heading.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(700);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Start_button.setVisibility(View.VISIBLE);
                        Start_button.setAlpha(0f);
                        Start_button.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(700);

                        View_button.setVisibility(View.VISIBLE);
                        View_button.setAlpha(0f);
                        View_button.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(700);

                        new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        logo2.setVisibility(View.VISIBLE);
                                        logo2.setAlpha(0f);
                                        logo2.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(700);

                                        if(allPermissionGranted())
                                        {

                                        }else
                                        {
                                            ActivityCompat.requestPermissions(Starting_Page.this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
                                        }
                                    }
                                }, 400);



                    }
                }, 800);

            }
        }, 800);

    }

    //************ OnClick Method *************
    public void Start_new_Click(View view)
    {
        if(allPermissionGranted())
        {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                startActivity(new Intent(Starting_Page.this,New_Case.class));
            }else{
                showGPSDisabledAlertToUser();
//                Toast.makeText(Starting_Page.this, "GPS is not Enabled in your device", Toast.LENGTH_SHORT).show();
            }


        }else
        {
            ActivityCompat.requestPermissions(Starting_Page.this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    public void View_Case_Click(View view)
    {
        if(allPermissionGranted())
        {
            startActivity(new Intent(Starting_Page.this,View_Case.class));
        }else
        {
            ActivityCompat.requestPermissions(Starting_Page.this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }

    }

    private boolean allPermissionGranted() {
        for (String  permission: REQUEST_PERMISSION)
        {
            if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }


    public String getStorageDir() throws IOException {
        //create folder
//        String s=Environment.getExternalStorageDirectory().toString()+"/";
//        File dir = new File(s+"DCIM");
//
//        File file = new File(dir.getAbsolutePath() + "/Pic.jpg");
//        file.createNewFile();
//
////        if (!file.exists()) {
////            System.out.println(file.mkdirs());
////            Toast.makeText(Starting_Page.this,String.valueOf(file.mkdirs()),Toast.LENGTH_SHORT).show();
////        }else
////        {
////            Toast.makeText(Starting_Page.this,"Already Exist",Toast.LENGTH_SHORT).show();
////        }
//        String filePath = file.getAbsolutePath();

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "1" + ".jpg");
        Drawable drawable = getResources().getDrawable(R.drawable.car_left_view);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (!file.exists()) {
            Log.d("path", file.toString());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Toast.makeText(Starting_Page.this,"Saved",Toast.LENGTH_SHORT).show();
                fos.flush();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(Starting_Page.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            logo1.setImageBitmap(bm);
        }
        return file.getAbsolutePath();
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





}
