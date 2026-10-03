package com.example.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FitnessDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final int DATABASE_VERSION = 2;

    public FitnessDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE activities (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "exercise TEXT, " +
                        "minutes INTEGER, " +
                        "calories INTEGER, " +
                        "steps INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS activities");
        onCreate(db);
    }

    public void addActivity(String exercise, int minutes, int calories, int steps) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("exercise", exercise);
        values.put("minutes", minutes);
        values.put("calories", calories);
        values.put("steps", steps);

        db.insert("activities", null, values);
        db.close();
    }

    public int getTotalMinutes() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT SUM(minutes) FROM activities",
                null
        );

        int total = 0;

        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            total = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return total;
    }

    public int getTotalCalories() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT SUM(calories) FROM activities",
                null
        );

        int total = 0;

        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            total = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return total;
    }

    public int getTotalSteps() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT SUM(steps) FROM activities",
                null
        );

        int total = 0;

        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            total = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return total;
    }
}