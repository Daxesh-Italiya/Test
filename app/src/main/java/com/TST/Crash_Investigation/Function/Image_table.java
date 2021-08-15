package com.TST.Crash_Investigation.Function;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Image_table extends SQLiteOpenHelper {

    public Image_table(Context context) {
        super(context, "Car", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Car_Table (Car_no TEXT primary key,Image1 TEXT,Image2 TEXT,Image3 TEXT,Image4 TEXT,Type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Car_Table");
    }

    public Boolean Insert_Case_data(String Car_no,String Image1,String Image2,String Image3,String Image4,String Type)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("Car_no",Car_no);
        contentValues.put("Image1",Image1);
        contentValues.put("Image2",Image2);
        contentValues.put("Image3",Image3);
        contentValues.put("Image4",Image4);
        contentValues.put("Type",Type);

        String Table_name="Car_Table";
        long result =DB.insert(Table_name,null,contentValues);
        if(result==-1)
        {
            return false;
        }else
        {
            return true;
        }

    }

    public Boolean Delete_data()
    {


        SQLiteDatabase DB=this.getWritableDatabase();
        String Table_name="Car_Table";
        long result =DB.delete(Table_name,null,null);
        if(result==-1)
        {
            return false;
        }else
        {
            return true;
        }

    }

    public Cursor Get_Case_data()
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        String Table_name="Car_Table";
        Cursor cursor=DB.rawQuery("Select * from  "+Table_name.replaceAll("\\s",""), null);
        return cursor;

    }

    public Cursor Get_Car(String id)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from  Car_Table WHERE Car_no = "+id, null);
        return cursor;

    }

    public void Drop_Subject_data(String Branch_name, String Sem)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        DB.execSQL("drop Table if exists "+Branch_name+Sem);

    }

    public boolean Delete_Car(String id)
    {
        String Table_name="Car_Table";
        SQLiteDatabase DB=this.getWritableDatabase();
        return DB.delete(Table_name,"Car_no = " + id, null)!=0;

    }

}
