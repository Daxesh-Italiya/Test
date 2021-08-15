package com.TST.Crash_Investigation.Function;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class temp_db extends SQLiteOpenHelper {

    private Context context;

    public temp_db(Context context) {
        super(context, "Case", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Case_List (Case_No NUMBER primary key,Case_Name TEXT,FIR_Number TEXT, Date Text, Area Text, Location Text, Car NUMBER, Person NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Case_List");
    }

    public Boolean Insert_Case_data(int no,String Case_name,String Fir_Number,String Case_Date,String Case_Area, String Case_Location)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Case_No",no);
        contentValues.put("Case_Name",Case_name);
        contentValues.put("FIR_Number",Fir_Number);
        contentValues.put("Date",Case_Date);
        contentValues.put("Area",Case_Area);
        contentValues.put("Location",Case_Location);
        contentValues.put("Car",0);
        contentValues.put("Person",0);

        String Table_name="Case_List";
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
        String Table_name="Case_List";
        long result =DB.delete(Table_name,null,null);
        if(result==-1)
        {
            return false;
        }else
        {
            return true;
        }

    }

    public Boolean Update_data(int id,int count)
    {

        SQLiteDatabase DB=this.getWritableDatabase();
        String Table_name="Case_List";

        ContentValues data=new ContentValues();
        data.put("Car",count);


        long result = DB.update(Table_name, data, "Case_No = " + id, null);

        if(result==0)
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
        String Table_name="Case_List";
        Cursor cursor=DB.rawQuery("Select * from  "+Table_name.replaceAll("\\s",""), null);
        return cursor;
    }

    public Cursor Get_Car(int id)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        String Table_name="Case_List";
        Cursor cursor=DB.rawQuery("Select * from  "+Table_name.replaceAll("\\s","")+" WHERE Case_No="+id, null);
        return cursor;

    }

    public void Drop_Subject_data(String Branch_name, String Sem)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        DB.execSQL("drop Table if exists "+Branch_name+Sem);

    }


}
