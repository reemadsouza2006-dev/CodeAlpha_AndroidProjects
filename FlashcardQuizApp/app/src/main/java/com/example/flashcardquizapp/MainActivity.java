package com.example.flashcardquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStudy = findViewById(R.id.btnStudy);
        Button btnFlashcards = findViewById(R.id.btnFlashcards);
        Button btnQuiz = findViewById(R.id.btnQuiz);
        Button btnAdd = findViewById(R.id.btnAdd);

        // Start Studying
        btnStudy.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    StudyActivity.class
            );
            startActivity(intent);
        });

        // My Flashcards
        btnFlashcards.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    FlashcardListActivity.class
            );
            startActivity(intent);
        });

        // Take Quiz
        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    QuizActivity.class
            );
            startActivity(intent);
        });

        // Add Flashcard
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddFlashCardActivity.class
            );
            startActivity(intent);
        });
    }
}