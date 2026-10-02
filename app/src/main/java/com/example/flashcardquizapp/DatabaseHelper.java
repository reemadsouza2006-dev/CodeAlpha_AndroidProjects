package com.example.flashcardquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flashcards.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE flashcards (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT, " +
                "answer TEXT, " +
                "category TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS flashcards");
        onCreate(db);
    }

    public boolean addFlashcard(String question, String answer, String category) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("question", question);
        values.put("answer", answer);
        values.put("category", category);

        long result = db.insert("flashcards", null, values);

        return result != -1;
    }

    public boolean deleteFlashcard(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "flashcards",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    public boolean updateFlashcard(
            int id,
            String question,
            String answer,
            String category
    ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("question", question);
        values.put("answer", answer);
        values.put("category", category);

        int result = db.update(
                "flashcards",
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
}