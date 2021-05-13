package com.example.extree.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CALCULATOR_TABLE = "CALCULATOR_TABLE";
    public static final String COLUMN_CALCULATOR_EXPRESSION = "CALCULATOR_EXPRESSION";
    public static final String COLUMN_CALCULATOR_RESULT = "CALCULATOR_RESULT";
    public static final String COLUMN_CALCULATOR_RESULT_DOUBLE = "CALCULATOR_RESULT_DOUBLE";
    public static final String COLUMN_ID = "ID";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + CALCULATOR_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CALCULATOR_EXPRESSION + " TEXT, " + COLUMN_CALCULATOR_RESULT + " TEXT, " + COLUMN_CALCULATOR_RESULT_DOUBLE + " DOUBLE)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CALCULATOR_TABLE;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Calculator.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean addOne(CalculatorModel calculatorModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CALCULATOR_EXPRESSION, calculatorModel.getExpression());
        cv.put(COLUMN_CALCULATOR_RESULT, calculatorModel.getResultString());
        cv.put(COLUMN_CALCULATOR_RESULT_DOUBLE, calculatorModel.getResultDouble());
        final long insert = db.insert(CALCULATOR_TABLE, null, cv);
        return insert != -1;
    }

    public List<CalculatorModel> getEveryone() {
        List<CalculatorModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + CALCULATOR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int calculatorID = cursor.getInt(0);
                String calculatorExpression = cursor.getString(1);
                String calculatorResult = cursor.getString(2);
                Double calculatorResultDouble = cursor.getDouble(3);
                CalculatorModel calculatorModel = new CalculatorModel(calculatorExpression, calculatorResult, calculatorResultDouble, calculatorID);
                returnList.add(calculatorModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
