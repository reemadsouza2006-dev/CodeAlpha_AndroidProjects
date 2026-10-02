package com.example.flashcardquizapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudyActivity extends AppCompatActivity {

    TextView tvQuestion, tvAnswer;
    Button btnShowAnswer, btnNext;

    DatabaseHelper databaseHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvAnswer = findViewById(R.id.tvAnswer);
        btnShowAnswer = findViewById(R.id.btnShowAnswer);
        btnNext = findViewById(R.id.btnNext);

        databaseHelper = new DatabaseHelper(this);

        cursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT question, answer FROM flashcards",
                null
        );

        if (cursor.moveToFirst()) {
            showQuestion();
        } else {
            Toast.makeText(
                    this,
                    "No flashcards available",
                    Toast.LENGTH_SHORT
            ).show();
        }

        btnShowAnswer.setOnClickListener(v -> {
            tvAnswer.setVisibility(TextView.VISIBLE);
        });

        btnNext.setOnClickListener(v -> {

            if (cursor.moveToNext()) {
                showQuestion();
            } else {
                Toast.makeText(
                        this,
                        "No more flashcards",
                        Toast.LENGTH_SHORT
                ).show();

                cursor.moveToFirst();
                showQuestion();
            }
        });
    }

    private void showQuestion() {

        String question = cursor.getString(0);
        String answer = cursor.getString(1);

        tvQuestion.setText(question);
        tvAnswer.setText(answer);

        tvAnswer.setVisibility(TextView.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cursor != null) {
            cursor.close();
        }
    }
}