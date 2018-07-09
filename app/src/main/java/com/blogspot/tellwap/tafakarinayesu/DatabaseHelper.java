package com.blogspot.tellwap.tafakarinayesu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.ParcelUuid;

/**
 * Created by chami epimark on 2017-12-22.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tafakari.db";
    public static final String TABLE_NAME = "tafakari_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "DAY";
    public static final String col_3 = "EVENT";
    public static final String col_4 = "SOMO1";
    public static final String col_5 = "SOMO2";

    public static final String col_6 = "INJILI";
    public static final String col_7 = "SOMO1LINE";
    public static final String col_8 = "SOMO2LINE";
    public static final String col_9 = "WIMBOWAKATIKATI";
    public static final String col_10 = "WIMBOWAKATIKATILINE";
    public static final String col_11 = "INJILI";
    public static final String col_12 = "INJILILINE";
    public static final String col_13 = "WIMBOWAMWANZO";
    public static final String col_14 = "WIMBOWAMWANZOLINE";
    public static final String col_15 = "SHANGILIO";
    public static final String col_16 = "SHANGILIOLINE";
    public static final String col_17 = "PALSM";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(" +
                "ID INTEGER," +
                "DAY TEXT," +
                "PALSM TEXT,"+
                "EVENT TEXT," +
                "YEAR TEXT," +
                "SOMO1 TEXT," +
                "SOMO2 TEXT," +
                "INJILI TEXT," +
                "SOMO1LINE TEXT," +
                "SOMO2LINE TEXT," +
                "INJILILINE TEXT," +
                "WIMBOWAKATIKATI TEXT," +
                "WIMBOWAKATIKATILINE TEXT," +
                "WIMBOWAMWANZO TEXT," +
                "WIMBOWAMWANZOLINE TEXT," +
                "SHANGILIO TEXT, " +
                "SHANGILIOLINE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(
                              String id,
                              String day,
                              String palsm,
                              String event,

                              String somo1,
                              String somo2,
                              String injili,
                              String somo1line,
                              String somo2line,
                              String injililine,
                              String wimbowakatikati,
                              String wimbowakatikatiline,
                              String wimbowamwanzo,
                              String wimbowamwanzoline,
                              String shangilio,
                              String shangilioline){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,day);
        contentValues.put(col_1,id);
        contentValues.put(col_3,event);
        contentValues.put(col_4,somo1);
        contentValues.put(col_5,somo2);
        contentValues.put(col_6,injili);
        contentValues.put(col_7,somo1line);
        contentValues.put(col_8,somo2line);
        contentValues.put(col_9,wimbowakatikati);
        contentValues.put(col_10,wimbowakatikatiline);
        contentValues.put(col_11,injili);
        contentValues.put(col_12,injililine);
        contentValues.put(col_13,wimbowamwanzo);
        contentValues.put(col_14,wimbowamwanzoline);
        contentValues.put(col_15,shangilio);
        contentValues.put(col_16,shangilioline);
        contentValues.put(col_17,palsm);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }


    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }
}
