package com.example.dailyexpanse.Db_modelClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "daily_expense";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "hisabnikas";
    private static final String primary_key = "id";
    private static final String EXPANSE_AMOUNT = "expense_amount";
    private static final String EXPANSE_DATE= "expense_date";
    private static final String EXPANSE_TIME = "expense_time";
    private static final String EXPANSE_IMAGE = "expense_image";
    private static final String EXPANSE_ITEM = "expense_item";

    private static final String CREATE_TABLE_CART = "CREATE TABLE "
            + TABLE_NAME + "(" + primary_key
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + EXPANSE_AMOUNT +" TEXT,"+EXPANSE_DATE+" TEXT,"+EXPANSE_TIME
            +" TEXT,"+EXPANSE_ITEM +" TEXT,"+EXPANSE_IMAGE+ " BLOB);";


    public Dbhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }
    public int deleteDataFromDatabase(int rowId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int deleteId = sqLiteDatabase.delete(TABLE_NAME, primary_key + "=" + rowId, null);
        return deleteId;
    }
    //getSpecificData
    public Cursor getData(String sql) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public long insertData(String amount, String edate, String etime, String eimage, String nmselecteditm)
    {


        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPANSE_AMOUNT, amount);
        contentValues.put(EXPANSE_DATE, edate);
        contentValues.put(EXPANSE_TIME, etime);
        contentValues.put(EXPANSE_ITEM, nmselecteditm);
        if (eimage!=null){
            contentValues.put(EXPANSE_IMAGE, eimage);

        }


        long id = dbb.insert(TABLE_NAME, null , contentValues);

        if (id==-1){
            Log.e("Error ","unsuccesfull to insert "+id);
        }else{
            Log.e("Success "," succesfull to insert "+id);
        }
        return id;
    }

    public boolean updatedata(String amount, String time,String edate,String uid ){
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPANSE_AMOUNT, amount);
        contentValues.put(EXPANSE_DATE, edate);
        contentValues.put(EXPANSE_TIME, time);

        long id = dbb.update(TABLE_NAME,contentValues,EXPANSE_TIME+" = ?", new String[]{uid});

        if (id==-1){
            Log.e("Error ","unsuccesfull to insert "+id);
        }else{
            Log.e("Success "," succesfull to insert "+id);
        }
        return true;
    }



    public boolean deletedata(String time){
        SQLiteDatabase dbb = this.getWritableDatabase();
        dbb.delete(TABLE_NAME,EXPANSE_TIME+" = ?", new String[]{time});
        return true;
    }
    public int getamountbydate(String expansedatefrom, String expansedateto){
        int amount=0;
        Log.e("my date ",expansedatefrom+" "+expansedateto);
        SQLiteDatabase dbb=this.getReadableDatabase();
        String sql="SELECT SUM("+EXPANSE_AMOUNT+") AS total FROM "+TABLE_NAME+" WHERE "+EXPANSE_DATE+" BETWEEN '"+expansedatefrom+"' AND '"+expansedateto+"'";
       Cursor cursor=dbb.rawQuery(sql,null);
        if (cursor!=null&&cursor.getColumnCount()>0) {
            if (cursor.moveToFirst()) {
                amount = cursor.getInt(cursor.getColumnIndex("total"));
            }

        }
        return amount;
    }

    public int getamountbyitem(String nmselecteditm){
        int amount=0;
        SQLiteDatabase dbb=this.getReadableDatabase();
        String sql="SELECT SUM("+EXPANSE_AMOUNT+") AS total FROM "+TABLE_NAME+" WHERE "+EXPANSE_ITEM+" ='"+nmselecteditm+"'";
        Cursor cursor=dbb.rawQuery(sql,null);
        if (cursor!=null&&cursor.getColumnCount()>0) {
            if (cursor.moveToFirst()) {
                amount = cursor.getInt(cursor.getColumnIndex("total"));
            }

        }
        return amount;
    }

    public int getallamount() {

        int amount = 0;
        Cursor cursor = null;
        SQLiteDatabase dbb=this.getReadableDatabase();

               String sql="SELECT SUM("+EXPANSE_AMOUNT+") AS total FROM "+TABLE_NAME;
               cursor=dbb.rawQuery(sql,null);
              if (cursor!=null&&cursor.getColumnCount()>0) {
                  if (cursor.moveToFirst()) {
                      amount = cursor.getInt(cursor.getColumnIndex("total"));
                  }
              }

        return amount;
    }


    public Cursor getDataFromDatabase() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        return cursor;
    }

    ///show all data

}
