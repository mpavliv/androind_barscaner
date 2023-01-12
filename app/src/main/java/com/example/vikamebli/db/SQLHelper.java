package com.example.vikamebli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLHelper extends SQLiteOpenHelper {

    public SQLHelper(Context context) {

        super(context, "vikaMebli.db", null, 1);
        String path = context.getDatabasePath("vikaMebli.db").getAbsolutePath();
        Log.i("MYLT","Database path: " + path);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("MYLT","Start creating table");
        Log.i("MYLT",SNTableStructure.SQL_CREATE_TABLE);
        db.execSQL(SNTableStructure.SQL_CREATE_TABLE);
        Log.i("MYLT","Ended creating table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
