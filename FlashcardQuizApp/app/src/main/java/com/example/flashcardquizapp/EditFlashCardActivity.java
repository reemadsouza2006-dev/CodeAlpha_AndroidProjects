package com.example.flashcardquizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditFlashCardActivity extends AppCompatActivity {

    EditText etEditQuestion, etEditAnswer, etEditCategory;
    Button btnUpdate;

    DatabaseHelper databaseHelper;

    int flashcardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flash_card);

        etEditQuestion = findViewById(R.id.etEditQuestion);
        etEditAnswer = findViewById(R.id.etEditAnswer);
        etEditCategory = findViewById(R.id.etEditCategory);
        btnUpdate = findViewById(R.id.btnUpdate);

        databaseHelper = new DatabaseHelper(this);

        flashcardId = getIntent().getIntExtra("flashcard_id", -1);

        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        String category = getIntent().getStringExtra("category");

        etEditQuestion.setText(question);
        etEditAnswer.setText(answer);
        etEditCategory.setText(category);

        btnUpdate.setOnClickListener(v -> updateFlashcard());
    }

    private void updateFlashcard() {

        String question = etEditQuestion.getText().toString().trim();
        String answer = etEditAnswer.getText().toString().trim();
        String category = etEditCategory.getText().toString().trim();

        if (question.isEmpty() || answer.isEmpty() || category.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        boolean updated = databaseHelper.updateFlashcard(
                flashcardId,
                question,
                answer,
                category
        );

        if (updated) {

            Toast.makeText(
                    this,
                    "Flashcard updated!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Update failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}