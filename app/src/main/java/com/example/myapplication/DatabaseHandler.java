package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "databaselist";
    private static int DATABASE_VERSION = 1;
    private String TABLE_NAME = "lists";
    private String COLUMN_ID = "id";
    private String COLUMN_STATUS = "status";
    private String COLUMN_KEGIATAN = "kegiatan";
    private String COLUMN_PENTING = "penting";

    public DatabaseHandler(@Nullable Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLStmt = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY," +
                COLUMN_STATUS + " BOOL," +
                COLUMN_KEGIATAN + " TEXT(256),"+
                COLUMN_PENTING + " BOOL" +
                ")";
        db.execSQL(SQLStmt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList getDatas(){
        SQLiteDatabase database = getReadableDatabase();
        String SQLStmt = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(SQLStmt,null);
        ArrayList<Queries> queries = new ArrayList<Queries>();
        while (cursor.moveToNext()){
            Queries quereis = new Queries();
            quereis.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            if (cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) == 1){
                quereis.setStatus(true);
            }else{
                quereis.setStatus(false);
            }
            if (cursor.getInt(cursor.getColumnIndex(COLUMN_PENTING)) == 1){
                quereis.setPenting(true);
            }else{
                quereis.setPenting(false);
            }
            quereis.setKegiatan(cursor.getString(cursor.getColumnIndex(COLUMN_KEGIATAN)));
//            Log.d("Status ",String.valueOf(quereis.isStatus()));
//            Log.d("Kegiatan ",quereis.getKegiatan());
//            Log.d("Penting ",String.valueOf(quereis.isPenting()));
            queries.add(quereis);
        }
        return queries;
    }
    public void insertData(Boolean status, String kegiatan, Boolean penting){
        SQLiteDatabase database = getWritableDatabase();
        String SQLStmt = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_STATUS + "," +
                COLUMN_KEGIATAN + "," +
                COLUMN_PENTING +
                ") VALUES (" + status + ",'" + kegiatan + "'," + penting + ")";
        database.execSQL(SQLStmt);
    }
    public void updateData(int id, Boolean status,String kegiatan, Boolean penting){
        SQLiteDatabase database = getWritableDatabase();
        String SQLStmt = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_STATUS + " = " + status + "," +
                COLUMN_KEGIATAN + " = '" + kegiatan + "'," +
                COLUMN_PENTING + " = " + penting + " WHERE "+
                COLUMN_ID + " = " + id;
        database.execSQL(SQLStmt);

    }
}
