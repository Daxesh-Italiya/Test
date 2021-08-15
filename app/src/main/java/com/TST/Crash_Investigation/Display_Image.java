package com.TST.Crash_Investigation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.TST.Crash_Investigation.Function.Case_DB;
import com.TST.Crash_Investigation.Function.Image_table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Display_Image extends AppCompatActivity {
    private ImageView Image1,Image2,Image3,Image4,image_preview,icon;
    private Image_table image_table;
    private TextView Heading, left_text, right_text, front_text, rear_text;
    private LinearLayout view_data;
    private TextView Case_name,FIR_Number,Case_Date,Case_area,Case_Location;
    private Case_DB db;
    private ScrollView Main_page;
    private String CAR_ID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__image);

        CAR_ID=getIntent().getStringExtra("ID");
        image_table= new Image_table(Display_Image.this);
        Image1=(ImageView)findViewById(R.id.Image1);
        Image2=(ImageView)findViewById(R.id.Image2);
        Image3=(ImageView)findViewById(R.id.Image3);
        Image4=(ImageView)findViewById(R.id.Image4);
        Heading=(TextView)findViewById(R.id.Heading);

        Case_name=(TextView)findViewById(R.id.Case_name);
        FIR_Number=(TextView)findViewById(R.id.FIR_Number);
        Case_Date=(TextView)findViewById(R.id.Case_Date);
        Case_area=(TextView)findViewById(R.id.Case_area);
        Case_Location=(TextView)findViewById(R.id.Case_Location);

        left_text=(TextView)findViewById(R.id.left_text);
        right_text=(TextView)findViewById(R.id.right_text);
        front_text=(TextView)findViewById(R.id.front_text);
        rear_text=(TextView)findViewById(R.id.rear_text);

        left_text.setVisibility(View.GONE);
        right_text.setVisibility(View.GONE);
        front_text.setVisibility(View.GONE);
        rear_text.setVisibility(View.GONE);

        Main_page=(ScrollView) findViewById(R.id.Main_page);

        view_data=(LinearLayout) findViewById(R.id.view_data);
        view_data.setVisibility(View.GONE);

        image_preview=(ImageView)findViewById(R.id.image_preview);

        db = new Case_DB(this);

        icon=(ImageView)findViewById(R.id.icon);

        if(getIntent().getStringExtra("type").equals("car"))
        {
            icon.setImageResource(R.drawable.car_icon);
        }else
        {
            icon.setImageResource(R.drawable.person_icon);
            left_text.setText("Above Abdomen");
            right_text.setText("Below Abdomen");
            Image1.setRotation(90);
            Image2.setRotation(90);
            image_preview.setRotation(90);

        }

        Heading.setText(getIntent().getStringExtra("heading"));

        Image1.setVisibility(View.GONE);
        Image2.setVisibility(View.GONE);
        Image3.setVisibility(View.GONE);
        Image4.setVisibility(View.GONE);
        image_preview.setVisibility(View.GONE);

        final Cursor res=image_table.Get_Car(CAR_ID);
        res.moveToNext();

        final Cursor Case_info=db.Get_Car(getIntent().getIntExtra("Case_ID",0));
        Case_info.moveToNext();

        Case_name.setText(Case_info.getString(1));
        FIR_Number.setText(Case_info.getString(2));
        Case_Date.setText(Case_info.getString(3));
        Case_area.setText(Case_info.getString(4));
        Case_Location.setText(Case_info.getString(5));


        Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b1 = null;
                try {
                    image_preview.setVisibility(View.VISIBLE);
                    File outputFile1=new File(res.getString(1));
                    b1 = BitmapFactory.decodeStream(new FileInputStream(outputFile1));
                    image_preview.setImageBitmap(b1);
                    Main_page.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        Image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b2 = null;
                try {
                    image_preview.setVisibility(View.VISIBLE);
                    File outputFile1=new File(res.getString(2));
                    b2 = BitmapFactory.decodeStream(new FileInputStream(outputFile1));
                    image_preview.setImageBitmap(b2);
                    Main_page.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        Image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b3 = null;
                try {
                    image_preview.setVisibility(View.VISIBLE);
                    File outputFile1=new File(res.getString(3));
                    b3 = BitmapFactory.decodeStream(new FileInputStream(outputFile1));
                    image_preview.setImageBitmap(b3);
                    Main_page.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        Image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b4 = null;
                try {
                    image_preview.setVisibility(View.VISIBLE);
                    File outputFile1=new File(res.getString(4));
                    b4 = BitmapFactory.decodeStream(new FileInputStream(outputFile1));
                    image_preview.setImageBitmap(b4);
                    Main_page.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

//        Cursor res=image_table.Get_Case_data();
//        while (res.moveToNext())
//        {
//            File outputFile=new File(res.getString(1));
//            Bitmap b = null;
//            try {
//                b = BitmapFactory.decodeStream(new FileInputStream(outputFile));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            Image1.setImageBitmap(b);
//            Toast.makeText(Display_Image.this,res.getString(1),Toast.LENGTH_SHORT).show();
//        }

            try {

                if (!res.getString(1).equals("NO"))
                {
                    Image1.setVisibility(View.VISIBLE);
                    left_text.setVisibility(View.VISIBLE);

                    File outputFile1=new File(res.getString(1));
                    Bitmap b1 = BitmapFactory.decodeStream(new FileInputStream(outputFile1));
                    Image1.setImageBitmap(b1);
                }

                if (!res.getString(2).equals("NO"))
                {
                    Image2.setVisibility(View.VISIBLE);
                    right_text.setVisibility(View.VISIBLE);
                    File outputFile2=new File(res.getString(2));
                    Bitmap b2 = BitmapFactory.decodeStream(new FileInputStream(outputFile2));
                    Image2.setImageBitmap(b2);
                }

                if (!res.getString(3).equals("NO"))
                {
                    Image3.setVisibility(View.VISIBLE);
                    front_text.setVisibility(View.VISIBLE);
                    File outputFile3=new File(res.getString(3));
                    Bitmap b3 = BitmapFactory.decodeStream(new FileInputStream(outputFile3));
                    Image3.setImageBitmap(b3);
                }

                if (!res.getString(4).equals("NO"))
                {
                    Image4.setVisibility(View.VISIBLE);
                    rear_text.setVisibility(View.VISIBLE);
                    File outputFile4=new File(res.getString(4));
                    Bitmap b4 = BitmapFactory.decodeStream(new FileInputStream(outputFile4));
                    Image4.setImageBitmap(b4);
                }


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Display_Image.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    public void onBackPressed() {
        if(image_preview.getVisibility()==View.VISIBLE)
        {
            image_preview.setVisibility(View.GONE);
            Main_page.setVisibility(View.VISIBLE);
        }else if(view_data.getVisibility()==View.VISIBLE){
            view_data.setVisibility(View.GONE);
        }
        else
        {
            finish();
        }
    }

    public void I_button_Click(View view)
    {
        view_data.setVisibility(View.VISIBLE);
    }

    public void On_Back_click(View view)
    {
        view_data.setVisibility(View.GONE);
    }

    public void Delete_Car(View view)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        final Cursor res=image_table.Get_Car(CAR_ID);
                        res.moveToNext();

                        if(Image1.getVisibility()==View.VISIBLE)
                        {
                            File file = new File(res.getString(1));
                            boolean deleted = file.delete();
//                            Toast.makeText(Display_Image.this, String.valueOf(deleted),Toast.LENGTH_SHORT).show();
                        }

                        if(Image1.getVisibility()==View.VISIBLE)
                        {
                            File file = new File(res.getString(2));
                            boolean deleted = file.delete();
                        }

                        if(Image1.getVisibility()==View.VISIBLE)
                        {
                            File file = new File(res.getString(3));
                            boolean deleted = file.delete();
                        }

                        if(Image1.getVisibility()==View.VISIBLE)
                        {
                            File file = new File(res.getString(4));
                            boolean deleted = file.delete();
                        }

                        if(image_table.Delete_Car(res.getString(0)))
                        {

                            Toast.makeText(Display_Image.this, getIntent().getStringExtra("heading")+" Removed",Toast.LENGTH_SHORT).show();
                            finish();
                        }else
                        {
                            Toast.makeText(Display_Image.this, "Something Went wrong",Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Display_Image.this);
        builder.setMessage("Are you sure to delete "+Heading.getText().toString()+" ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();




    }

    public void Delete_case(View view)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        int CASE_ID=Integer.parseInt(CAR_ID.substring(0,1));
                        final Cursor Case_db=db.Get_Car(CASE_ID);
                        Case_db.moveToNext();

                        int car=Case_db.getInt(6);

                        for (int i=1;i<=car;i++)
                        {
                            try {

                                final Cursor res=image_table.Get_Car(String.valueOf(CASE_ID)+i);
                                if(res.moveToNext())
                                {
                                    if(!res.getString(1).equals("NO"))
                                    {
                                        File file = new File(res.getString(1));
                                        boolean deleted = file.delete();
//                            Toast.makeText(Display_Image.this, String.valueOf(deleted),Toast.LENGTH_SHORT).show();
                                    }

                                    if(!res.getString(2).equals("NO"))
                                    {
                                        File file = new File(res.getString(2));
                                        boolean deleted = file.delete();
                                    }

                                    if(!res.getString(3).equals("NO"))
                                    {
                                        File file = new File(res.getString(3));
                                        boolean deleted = file.delete();
                                    }

                                    if(!res.getString(4).equals("NO"))
                                    {
                                        File file = new File(res.getString(4));
                                        boolean deleted = file.delete();
                                    }

                                    if(image_table.Delete_Car(res.getString(0)))
                                    {
//                                        Toast.makeText(Display_Image.this, i+" Deleted",Toast.LENGTH_SHORT).show();
                                    }else
                                    {
//                                        Toast.makeText(Display_Image.this, "Something Went wrong",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }catch (Exception e)
                            {
//                                Toast.makeText(Display_Image.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(db.Update_Status(CASE_ID,2))
                        {
                            Toast.makeText(Display_Image.this, "Case Deleted",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Display_Image.this,View_Case.class));
                            finish();
                        }else
                        {
                            Toast.makeText(Display_Image.this, "Case can not be Deleted\ntry again",Toast.LENGTH_SHORT).show();
                        }



                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Display_Image.this);
        builder.setMessage("Are you sure to delete "+Heading.getText().toString()+" ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
