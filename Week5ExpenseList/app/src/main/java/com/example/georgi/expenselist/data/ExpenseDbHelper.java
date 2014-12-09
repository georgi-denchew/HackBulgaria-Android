package com.example.georgi.expenselist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Georgi on 3.12.2014 г..
 */
public class ExpenseDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "expense.db";

    public ExpenseDbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }



    public static enum ExpenseTable {
        ID, DESCRIPTION, PRICE
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE =
                "CREATE TABLE " + ExpenseTable.class.getSimpleName() + " ("  +
                        ExpenseTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ExpenseTable.DESCRIPTION + " varchar(100) NOT NULL, " +
                        ExpenseTable.PRICE + " double NOT NULL) " +
                         ";";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExpenseTable.class.getName());
        onCreate(db);
    }
}
