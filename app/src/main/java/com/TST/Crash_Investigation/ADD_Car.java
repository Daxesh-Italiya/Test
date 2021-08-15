package com.TST.Crash_Investigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.TST.Crash_Investigation.Function.Case_DB;
import com.TST.Crash_Investigation.Function.Image_table;
import com.TST.Crash_Investigation.object.ShowCamara;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ADD_Car extends AppCompatActivity {

    //---------- Front end Object ---------------
    private ImageView car1,car2,car3,car4,P_Image1,P_Image2,GUID,car_guide,icon;
    private ScrollView list_page;
    private RelativeLayout camera_page;
    private LinearLayout P_view1,P_view2,view1,view2,view3,view4,Car_Image_Page,Person_Image_Page;

    //------------- Local Database -----------------
    private Case_DB db;
    private int Index=0;

    private int REQUEST_CODE_PERMISSION =101;
    private String[] REQUEST_PERMISSION=new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};

    private FrameLayout mframeLayout;
    private ImageView image;
    private Camera mCamera;
    private ShowCamara showCamara;
    private Image_table image_table;
    private boolean flag=false,Camera_flag1=false,Camera_flag2=false,Camera_flag3=false,Camera_flag4=false,P1_flag=false,P2_flag=false,camera_button_flag=true;
    private String Current_path,path1="NO",path2="NO",path3="NO",path4="NO",P_path1="NO",P_path2="NO";
    private TextView Heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d_d__car);
        Define();
        if(allPermissionGranted())
        {
            flag=true;

        }else
        {
            flag=false;
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    //*************  Assign Object *************
    private void Define() {
        db=new Case_DB(this);
        Index=getIntent().getIntExtra("id",0);
//        if(Index!=0)
//        {
//            Cursor res=db.Get_Car(Index);
//             res.moveToNext();
//             Toast.makeText(ADD_Car.this,"Case NO : "+ res.getString(0)+
//                             "\nCase Name : "+ res.getString(1)+
//                             "\nFIR : "+ res.getString(2)+
//                             "\nDate : "+ res.getString(3)+
//                             "\nAre : "+ res.getString(4)+
//                             "\nLocation Name : "+ res.getString(5)+
//                             "\nCar : "+ res.getString(6)+
//                             "\nPerson Name : "+ res.getString(7)
//                     ,Toast.LENGTH_LONG).show();
//
//        }
//        else
//            Toast.makeText(ADD_Car.this,"ID",Toast.LENGTH_SHORT).show();
        car1=(ImageView)findViewById(R.id.Image1);
        car2=(ImageView)findViewById(R.id.Image2);
        car3=(ImageView)findViewById(R.id.Image3);
        car4=(ImageView)findViewById(R.id.Image4);
        GUID=(ImageView)findViewById(R.id.GUID);
        icon=(ImageView)findViewById(R.id.icon);
        Heading=(TextView) findViewById(R.id.Heading);
        car_guide=(ImageView)findViewById(R.id.car_guide);
        car_guide.setVisibility(View.GONE);
        GUID.setVisibility(View.GONE);

        P_Image1=(ImageView)findViewById(R.id.P_Image1);
        P_Image2=(ImageView)findViewById(R.id.P_Image2);

        view1=(LinearLayout) findViewById(R.id.view1);
        view2=(LinearLayout) findViewById(R.id.view2);
        view3=(LinearLayout) findViewById(R.id.view3);
        view4=(LinearLayout) findViewById(R.id.view4);

        P_view1=(LinearLayout) findViewById(R.id.P_view1);
        P_view2=(LinearLayout) findViewById(R.id.P_view2);

        Car_Image_Page=(LinearLayout) findViewById(R.id.Car_Image_Page);
        Person_Image_Page=(LinearLayout) findViewById(R.id.Person_Image_Page);

        if(getIntent().getStringExtra("type").equals("car"))
        {
            Car_Image_Page.setVisibility(View.VISIBLE);
            Person_Image_Page.setVisibility(View.GONE);
            Heading.setText("Car "+getIntent().getIntExtra("count",1));
            icon.setImageResource(R.drawable.car_icon);

        }else
        {
            Car_Image_Page.setVisibility(View.GONE);
            Person_Image_Page.setVisibility(View.VISIBLE);
            Heading.setText("Person "+getIntent().getIntExtra("count",1));
            icon.setImageResource(R.drawable.person_icon);

        }

        list_page=(ScrollView) findViewById(R.id.list_page);
        camera_page=(RelativeLayout) findViewById(R.id.camera_page);
        list_page.setVisibility(View.VISIBLE);
        camera_page.setVisibility(View.GONE);

        image_table= new Image_table(ADD_Car.this);
        image=(ImageView) findViewById(R.id.left);

        Cursor res=image_table.Get_Case_data();
        int i=0;
        while (res.moveToNext())
        {
            i++;
        }

//        Toast.makeText(this,"Entry In Image Table"+ i,Toast.LENGTH_SHORT).show();


    }

    //************ On click Event ***************

    public void Image1_Click(View view)
    {
        if (flag)
        {
            Set_Flag(1);
            list_page.setVisibility(View.GONE);
            camera_page.setVisibility(View.VISIBLE);
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
            car_guide.setImageResource(R.drawable.guid_car_left);
            car_guide.setVisibility(View.VISIBLE);
            GUID.setVisibility(View.GONE);


        }else
        {
            Toast.makeText(ADD_Car.this,"Permission is needed",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    public void Image2_Click(View view)
    {
        if (flag)
        {
            Set_Flag(2);
            list_page.setVisibility(View.GONE);
            camera_page.setVisibility(View.VISIBLE);
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
            car_guide.setImageResource(R.drawable.guid_car_right);
            car_guide.setVisibility(View.VISIBLE);
            GUID.setVisibility(View.GONE);
        }else
        {
            Toast.makeText(ADD_Car.this,"Permission is needed",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    public void Image3_Click(View view)
    {
        if (flag)
        {
            Set_Flag(3);
            list_page.setVisibility(View.GONE);
            camera_page.setVisibility(View.VISIBLE);
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
            car_guide.setImageResource(R.drawable.guid_front);
            car_guide.setVisibility(View.VISIBLE);
            GUID.setVisibility(View.GONE);
        }else
        {
            Toast.makeText(ADD_Car.this,"Permission is needed",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    public void Image4_Click(View view)
    {
        if (flag)
        {

            Set_Flag(4);
            list_page.setVisibility(View.GONE);
            camera_page.setVisibility(View.VISIBLE);
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
            car_guide.setImageResource(R.drawable.guid_rear);
            car_guide.setVisibility(View.VISIBLE);
            GUID.setVisibility(View.GONE);

        }else
        {
            Toast.makeText(ADD_Car.this,"Permission is needed",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    public void P_Image1_Click(View view)
    {
        if (flag)
        {

            Set_P_Flag(1);
            list_page.setVisibility(View.GONE);
            camera_page.setVisibility(View.VISIBLE);
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
            GUID.setImageResource(R.drawable.guid_above);
            car_guide.setVisibility(View.GONE);
            GUID.setVisibility(View.VISIBLE);
        }else
        {
            Toast.makeText(ADD_Car.this,"Permission is needed",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    public void P_Image2_Click(View view)
    {
        if (flag)
        {

            Set_P_Flag(2);
            list_page.setVisibility(View.GONE);
            camera_page.setVisibility(View.VISIBLE);
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
            GUID.setImageResource(R.drawable.guid_below);
            car_guide.setVisibility(View.GONE);
            GUID.setVisibility(View.VISIBLE);
        }else
        {
            Toast.makeText(ADD_Car.this,"Permission is needed",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    private void Set_Flag(int i)
    {
        Camera_flag1=false;
        Camera_flag2=false;
        Camera_flag3=false;
        Camera_flag4=false;

        if(i==1)
        {
            Camera_flag1=true;
        }else if(i==2)
        {
            Camera_flag2=true;
        }else if(i==3)
        {
            Camera_flag3=true;
        }else if(i==4)
        {
            Camera_flag4=true;
        }
    }

    private void Set_P_Flag(int i)
    {
        P1_flag=false;
        P2_flag=false;

        if(i==1)
        {
            P1_flag=true;
        }else if(i==2)
        {
            P2_flag=true;
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

    Camera.PictureCallback mP=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile= getOutputMediaFile();
            if(pictureFile==null)
            {
                return;
            }else
            {
                try {
                    int bytesRead=0;
                    FileOutputStream foe = new FileOutputStream(pictureFile);
                    foe.write(data);
                    foe.close();
                    InputStream inputStream = new FileInputStream(pictureFile);
                    byte[] result = data;
                    bytesRead = inputStream.read(result);
//                    Bitmap bmp = BitmapFactory.decodeByteArray(result, 0, result.length);
                    Bitmap bmp = BitmapFactory.decodeByteArray(result, 0, bytesRead);
                    if(Camera_flag1)
                    {
                        path1=Current_path;
                        car1.setImageBitmap(bmp);
                        Hide1();
                    }else if(Camera_flag2)
                    {
                        path2=Current_path;
                        car2.setImageBitmap(bmp);
                        Hide2();
                    }else if(Camera_flag3)
                    {
                        path3=Current_path;
                        car3.setImageBitmap(bmp);
                        Hide3();
                    }else if(Camera_flag4)
                    {
                        path4=Current_path;
                        car4.setImageBitmap(bmp);
                        Hide4();
                    }else if(P1_flag)
                    {
                        P_path1=Current_path;
                        P_Image1.setImageBitmap(bmp);
                        Hide_P_1();
                    }
                    else if(P2_flag)
                    {
                        P_path2=Current_path;
                        P_Image2.setImageBitmap(bmp);
                        Hide_P_2();
                    }
                    camera_button_flag=true;
//                    Picasso.get().load(Uri.parse(Current_path)).fit().centerCrop().into(car1);
                    list_page.setVisibility(View.VISIBLE);
                    camera_page.setVisibility(View.GONE);
//                    Toast.makeText(ADD_Car.this,"Saved",Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ADD_Car.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    private File getOutputMediaFile() {
        String state= Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(ADD_Car.this,"Muted",Toast.LENGTH_SHORT).show();
            return  null;
        }
        else
        {
            File folder_gui=new File(getFilesDir()+File.separator+"GUI");
            if(!folder_gui.exists())
            {
                folder_gui.mkdir();
            }
            File outputFile=new File(folder_gui,System.currentTimeMillis()+".jpg");

            Current_path=outputFile.getPath();
//            Toast.makeText(ADD_Car.this,Current_path,Toast.LENGTH_SHORT).show();
            return outputFile;
        }
    }

    public void Capture(View view)
    {

        if (camera_button_flag)
        {
            if(mCamera!=null)
            {
                camera_button_flag=false;
                mCamera.takePicture(null,null,mP);
            }
        }

    }

    public void Save_Click(View view)
    {
        db=new Case_DB(this);
        Cursor res=db.Get_Car(Index);
        res.moveToNext();

        if(getIntent().getStringExtra("type").equals("car"))
        {
            if(path1.equals("NO") && path2.equals("NO") && path3.equals("NO") && path4.equals("NO"))
            {
                Toast.makeText(ADD_Car.this,"Please Take At list one Image",Toast.LENGTH_SHORT).show();
            }else
            {
                if (image_table.Insert_Case_data(Index+String.valueOf(res.getInt(6)+1),path1,path2,path3,path4,getIntent().getStringExtra("type")))
                {

                    if(db.Update_data(Index,res.getInt(6)+1))
                    {
                        Toast.makeText(ADD_Car.this,"Car Image saved successfully",Toast.LENGTH_SHORT).show();

                        finish();
                    }else
                    {
                        Toast.makeText(ADD_Car.this,"Car Image not saved",Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(ADD_Car.this,"Car Image not saved",Toast.LENGTH_SHORT).show();
                }
            }

        }else
        {

            if(P_path1.equals("NO") && P_path2.equals("NO") )
            {
                Toast.makeText(ADD_Car.this,"Please Take At list one Image",Toast.LENGTH_SHORT).show();
            }else {
                if (image_table.Insert_Case_data(Index + String.valueOf(res.getInt(6) + 1), P_path1, P_path2, "NO", "NO", getIntent().getStringExtra("type"))) {
                    if (db.Update_data(Index, res.getInt(6) + 1)) {
                        Toast.makeText(ADD_Car.this,"Person Image saved successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ADD_Car.this,"Person Image not saved",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ADD_Car.this,"Car Image not saved",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void Hide1()
    {
        view1.setVisibility(View.GONE);
    }
    private void Hide2()
    {
        view2.setVisibility(View.GONE);
    }
    private void Hide3()
    {
        view3.setVisibility(View.GONE);
    }
    private void Hide4()
    {
        view4.setVisibility(View.GONE);
    }

    private void Hide_P_1()
    {
        P_view1.setVisibility(View.GONE);
    }
    private void Hide_P_2()
    {
        P_view2.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        if(camera_page.getVisibility()==View.VISIBLE)
        {
            list_page.setVisibility(View.VISIBLE);
            camera_page.setVisibility(View.GONE);
        }else
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

            AlertDialog.Builder builder = new AlertDialog.Builder(ADD_Car.this);
            builder.setMessage(Heading.getText().toString()+" is not saved \nAre you sure to go back?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    }
}
