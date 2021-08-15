package com.TST.Crash_Investigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TST.Crash_Investigation.Function.Case_DB;
import com.TST.Crash_Investigation.Function.Image_table;

import java.io.File;
import java.util.ArrayList;

public class Starting_Slide extends AppCompatActivity {

    //---------- Front end Object ---------------
    private RecyclerView Case_List_RecyclerView;
    private TextView Heading;
    private LinearLayout view_data;
    private ImageView Save_button;

    //--------- Variable ---------------------
    private ArrayList<String> Case_List;
    private Image_table image_table;

    //------------- Local Database -----------------
    private Case_DB db;

    private ImageView UP_line,Up_line_2;
    private TextView Text1,Text2;

    private int REQUEST_CODE_PERMISSION =101;
    private String[] REQUEST_PERMISSION=new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};

    int car_count=0,person_count=0,CASE_ID;

    private TextView Case_name,FIR_Number,Case_Date,Case_area,Case_Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting__slide);
        Define();

    }

    private void Animation() {

        UP_line.setVisibility(View.VISIBLE);
        UP_line.setAlpha(0f);
        UP_line.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(900);
        Text1.setVisibility(View.VISIBLE);
        Text1.setAlpha(0f);
        Text1.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(900);

        Up_line_2.setVisibility(View.VISIBLE);
        Up_line_2.setAlpha(0f);
        Up_line_2.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(900);
        Text2.setVisibility(View.VISIBLE);
        Text2.setAlpha(0f);
        Text2.animate().rotationBy(0f).scaleX(1f).scaleY(1f).alpha(1f).setDuration(900);

    }

    private void Hide_suggestions(){
        UP_line.setVisibility(View.GONE);
        Text1.setVisibility(View.GONE);
        Up_line_2.setVisibility(View.GONE);
        Text2.setVisibility(View.GONE);
    }

    //*************  Assign Object *************
    private void Define() {
        CASE_ID=getIntent().getIntExtra("id",0);
        image_table= new Image_table(Starting_Slide.this);
        Case_List_RecyclerView=(RecyclerView)findViewById(R.id.List);
        Case_List_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Case_List=new ArrayList<>();
        UP_line=(ImageView) findViewById(R.id.arrow_1);
        Up_line_2=(ImageView) findViewById(R.id.curve);
        Save_button=(ImageView) findViewById(R.id.Save_button);
        Text1=(TextView) findViewById(R.id.Text1);
        Text2=(TextView) findViewById(R.id.Text2);
        Heading=(TextView) findViewById(R.id.Heading);
        Heading.setText(getIntent().getStringExtra("heading"));
        UP_line.setVisibility(View.GONE);
        Up_line_2.setVisibility(View.GONE);
        Text1.setVisibility(View.GONE);
        Text2.setVisibility(View.GONE);
        db = new Case_DB(this);

        Cursor rsc=db.Get_Car(CASE_ID);
        rsc.moveToNext();

        view_data=(LinearLayout) findViewById(R.id.view_data);
        view_data.setVisibility(View.GONE);

        Case_name=(TextView)findViewById(R.id.Case_name);
        FIR_Number=(TextView)findViewById(R.id.FIR_Number);
        Case_Date=(TextView)findViewById(R.id.Case_Date);
        Case_area=(TextView)findViewById(R.id.Case_area);
        Case_Location=(TextView)findViewById(R.id.Case_Location);


        Case_name.setText(rsc.getString(1));
        FIR_Number.setText(rsc.getString(2));
        Case_Date.setText(rsc.getString(3));
        Case_area.setText(rsc.getString(4));
        Case_Location.setText(rsc.getString(5));

        if(getIntent().getStringExtra("source").equals("View"))
        {
            Save_button.setVisibility(View.GONE);
        }
    }


    //************* On Click Events ****************

    public void Add_Car(View view)
    {
        if(allPermissionGranted())
        {
            startActivity(new Intent(Starting_Slide.this,ADD_Car.class)
                    .putExtra("id",CASE_ID)
                    .putExtra("type","car")
                    .putExtra("count",car_count+1));
        }else
        {
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }




    }

    public void Add_Person(View view)
    {
        if(allPermissionGranted())
        {
            startActivity(new Intent(Starting_Slide.this,ADD_Car.class)
                    .putExtra("id",CASE_ID)
                    .putExtra("type","person")
                    .putExtra("count",person_count+1));
        }else
        {
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }


    }

    public void I_button_Click(View view)
    {
        view_data.setVisibility(View.VISIBLE);
    }

    public void On_Save_Click(View view)
    {

        if(Case_List.size()==0)
        {
            Toast.makeText(Starting_Slide.this,"Please add at least one data",Toast.LENGTH_SHORT).show();
        }else
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            boolean result=db.Update_Status(CASE_ID,1);

                            if (result)
                            {
                                Toast.makeText(Starting_Slide.this,"New case Created",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Starting_Slide.this,View_Case.class));
                                finish();
                            }else
                            {
                                Toast.makeText(Starting_Slide.this,"Facing Error to create case\nTry Again",Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(Starting_Slide.this);
            builder.setMessage("Are you sure to save the case ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }


    }

    public void On_Back_click(View view)
    {
        view_data.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Case_List.clear();
        car_count=0;
        person_count=0;
        Cursor res=image_table.Get_Case_data();
        int i=0;
//        Toast.makeText(Starting_Slide.this,String.valueOf(CASE_ID),Toast.LENGTH_LONG).show();
        while (res.moveToNext())
        {
            if(Integer.parseInt(res.getString(0).substring(0,1))==CASE_ID)
                Case_List.add(res.getString(0));
        }
        Car_Adapter case_adapter;

        if(Case_List.size()==0)
        {
            Animation();
        }else
        {
            Hide_suggestions();
        }
        case_adapter =new Car_Adapter(Starting_Slide.this,Case_List);
        Case_List_RecyclerView.setAdapter(case_adapter);


    }

    public class Car_Adapter extends RecyclerView.Adapter<Car_Adapter.MyViewHolder> {
        Context context;
        ArrayList<String> arrayList;

        public Car_Adapter(Context c, ArrayList<String> p) {
            context = c;
            arrayList = p;
        }

        @NonNull
        @Override
        public Car_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Car_Adapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.case_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final Car_Adapter.MyViewHolder holder, final int position) {

            Cursor res=image_table.Get_Car(arrayList.get(position));
            res.moveToNext();

            if(res.getString(5).equals("car"))
            {
                holder.car_icon.setImageResource(R.drawable.brown_car_icon);
                car_count++;
                holder.Case_name.setText("Car "+car_count);
            }else
            {
                holder.car_icon.setImageResource(R.drawable.brown_person_icon);
                person_count++;
                holder.Case_name.setText("Person "+person_count);
            }


            holder.Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Starting_Slide.this,Display_Image.class)
                            .putExtra("ID",arrayList.get(position))
                            .putExtra("type",holder.Case_name.getText().toString().contains("Car")?"car":"bike")
                            .putExtra("heading",holder.Case_name.getText().toString())
                            .putExtra("Case_ID",CASE_ID));
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Case_name;
            LinearLayout Card;
            ImageView car_icon;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                Case_name = (TextView) itemView.findViewById(R.id.Case_name);
                Card = (LinearLayout) itemView.findViewById(R.id.Card);
                car_icon = (ImageView) itemView.findViewById(R.id.car_icon);
            }
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

    @Override
    public void onBackPressed() {
        if(view_data.getVisibility()==View.VISIBLE)
        {
            view_data.setVisibility(View.GONE);
        }else
        {
            if(getIntent().getStringExtra("source").equals("View"))
            {
                finish();
            }else {
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

                AlertDialog.Builder builder = new AlertDialog.Builder(Starting_Slide.this);
                builder.setMessage("Case data was not saved \nAre you sure to go back?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }



        }
    }

    public void Delete_case(View view)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

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
//                                    Toast.makeText(Starting_Slide.this, i+" Deleted",Toast.LENGTH_SHORT).show();
                                    }else
                                    {
//                                    Toast.makeText(Starting_Slide.this, "Something Went wrong",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }catch (Exception e)
                            {
//                                Toast.makeText(Starting_Slide.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(db.Update_Status(CASE_ID,2))
                        {
                            Toast.makeText(Starting_Slide.this, "Case Deleted",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Starting_Slide.this,View_Case.class));
                            finish();
                        }else
                        {
                            Toast.makeText(Starting_Slide.this, "Case can not be Deleted\ntry again",Toast.LENGTH_SHORT).show();
                        }



                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Starting_Slide.this);
        builder.setMessage("Are you sure to delete "+Case_name.getText().toString()+" ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


}
