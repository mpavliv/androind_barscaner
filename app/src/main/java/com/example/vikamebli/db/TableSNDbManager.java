package com.example.vikamebli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TableSNDbManager {
    private Context context;
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;

    public TableSNDbManager(Context context){
        Log.i("MYLT","Start creating manager");
        this.context = context;
        sqlHelper = new SQLHelper(context);
    }

    public void openDb(){

        Log.i("MYLT","Opening database");
        db = sqlHelper.getWritableDatabase();
    }

    public void insertDB(SN sn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SNTableStructure.KEY_NUMBER, sn.number);
        contentValues.put(SNTableStructure.KEY_PRODUCT, sn.product);
        contentValues.put(SNTableStructure.KEY_FABRIC1, sn.fabric1);
        contentValues.put(SNTableStructure.KEY_FABRIC2, sn.fabric2);
        contentValues.put(SNTableStructure.KEY_FABRIC3, sn.fabric3);
        contentValues.put(SNTableStructure.KEY_ORDER, sn.myorder);
        contentValues.put(SNTableStructure.KEY_NOTES, sn.notes);
        db.insert(SNTableStructure.TABLE_NAME, null, contentValues);
    }

    public List<SN> readDB(String number){
        List<SN> listSN = new ArrayList<>();
        String[] numbers = {number};
        Cursor cursor = db.query(SNTableStructure.TABLE_NAME, null,
                SNTableStructure.KEY_NUMBER + " = ?", numbers, null, null, null);
        while (cursor.moveToNext()){
            int numberIndex = cursor.getColumnIndex(SNTableStructure.KEY_NUMBER);
            int productIndex = cursor.getColumnIndex(SNTableStructure.KEY_PRODUCT);
            int fabric1Index = cursor.getColumnIndex(SNTableStructure.KEY_FABRIC1);
            int fabric2Index = cursor.getColumnIndex(SNTableStructure.KEY_FABRIC2);
            int fabric3Index = cursor.getColumnIndex(SNTableStructure.KEY_FABRIC3);
            int orderIndex = cursor.getColumnIndex(SNTableStructure.KEY_ORDER);
            int notesIndex = cursor.getColumnIndex(SNTableStructure.KEY_NOTES);
            SN sn = new SN(cursor.getString(numberIndex),
                    cursor.getString(productIndex),
                    cursor.getString(fabric1Index),
                    cursor.getString(fabric2Index),
                    cursor.getString(fabric3Index),
                    cursor.getString(orderIndex),
                    cursor.getString(notesIndex)
            );
            listSN.add(sn);
        }
        cursor.close();
        return listSN;
    }

    public void deleteAllDB(){
        db.delete(SNTableStructure.TABLE_NAME, null, null);
    }

    public void closeDB(){
        sqlHelper.close();
    }

}
