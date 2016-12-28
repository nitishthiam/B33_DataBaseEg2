package com.techpalle.b33_databaseeg2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by skillgun on 10/17/2016.
 */
public class MyDatabase {
    //DECLARE ALL VARIABLES
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;

    //CONSTRUCTOR TO CREATE HELPER OBJECT
    public MyDatabase(Context context){
        myHelper = new MyHelper(context, "techpalle.db", null, 1);
    }
    //OPEN DATABASE - CREATE DATABASE OBJECT
    public void open(){
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    // ... DML OPERATIONS ..
    public void insertCourse(String cname, int cdur, String ctrainer){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cname", cname); //LEFT SIDE COLUMN NAME, RIGHT SIDE PARAMETER
        contentValues.put("cdur", cdur);
        contentValues.put("ctrainer", ctrainer);
        sqLiteDatabase.insert("courses", null, contentValues);
    }
    public long insertStudent(String sname, String semail, String scourse){
        ContentValues contentValues = new ContentValues();
        contentValues.put("sname", sname);
        contentValues.put("semail", semail);
        contentValues.put("scourse", scourse);
        long val = sqLiteDatabase.insert("students", null, contentValues);
        return val;
    }
    public Cursor queryCourse(){
        Cursor c = sqLiteDatabase.query("courses", null, null, null, null, null, null);
        return c;
    }
    public Cursor queryStudent(String sub){
        Cursor c = sqLiteDatabase.query("students", null, "scourse = ?",
                new String[]{sub}, null, null, null);
        return c;
    }

    //CLOSE DATABASE
    public void close(){
        sqLiteDatabase.close();
    }

    public class MyHelper extends SQLiteOpenHelper{
        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //creating tables
            sqLiteDatabase.execSQL("create table courses(_id integer primary key, cname text, cdur integer, ctrainer text);");
            sqLiteDatabase.execSQL("create table students(_id integer primary key, sname text, semail text, scourse text);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }
}
