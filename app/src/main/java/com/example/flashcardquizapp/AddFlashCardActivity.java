package com.example.flashcardquizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddFlashCardActivity extends AppCompatActivity {

    EditText etQuestion, etAnswer, etCategory;
    Button btnSave;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flash_card);

        etQuestion = findViewById(R.id.etQuestion);
        etAnswer = findViewById(R.id.etAnswer);
        etCategory = findViewById(R.id.etCategory);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {

            String question = etQuestion.getText().toString().trim();
            String answer = etAnswer.getText().toString().trim();
            String category = etCategory.getText().toString().trim();

            if (question.isEmpty() || answer.isEmpty() || category.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();

            } else {

                boolean saved = databaseHelper.addFlashcard(
                        question,
                        answer,
                        category
                );

                if (saved) {

                    Toast.makeText(this,
                            "Flashcard saved!",
                            Toast.LENGTH_SHORT).show();

                    // Clear the fields
                    etQuestion.setText("");
                    etAnswer.setText("");
                    etCategory.setText("");

                } else {

                    Toast.makeText(this,
                            "Error saving flashcard",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}