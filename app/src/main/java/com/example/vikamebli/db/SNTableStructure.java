package com.example.vikamebli.db;

public class SNTableStructure {
    public static final int DATABASE_VERSION = 1;
    public static final String  DATABASE_NAME = "vikaMebli.db";
    public static final String  TABLE_NAME = "sn";
    public static final String  KEY_ID = "_id";
    public static final String  KEY_NUMBER = "number";
    public static final String  KEY_PRODUCT = "product";
    public static final String  KEY_FABRIC1 = "fabric1";
    public static final String  KEY_FABRIC2 = "fabric2";
    public static final String  KEY_FABRIC3 = "fabric3";
    public static final String  KEY_ORDER = "myorder";
    public static final String  KEY_NOTES = "notes";
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY, "
                    + KEY_NUMBER + " TEXT, "
                    + KEY_PRODUCT + " TEXT, "
                    + KEY_FABRIC1 + " TEXT, "
                    + KEY_FABRIC2 + " TEXT, "
                    + KEY_FABRIC3 + " TEXT, "
                    + KEY_ORDER + " TEXT, "
                    + KEY_NOTES + " TEXT)"
            ;

}
