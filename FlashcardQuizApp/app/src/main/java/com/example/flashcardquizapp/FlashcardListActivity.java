package com.example.flashcardquizapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class FlashcardListActivity extends AppCompatActivity {

    ListView listViewFlashcards;
    Spinner spinnerCategory;
    DatabaseHelper databaseHelper;

    ArrayList<String> flashcards;
    ArrayList<Integer> flashcardIds;
    ArrayList<String> questions;
    ArrayList<String> answers;
    ArrayList<String> categories;

    ArrayList<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_list);

        listViewFlashcards = findViewById(R.id.listViewFlashcards);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        databaseHelper = new DatabaseHelper(this);

        loadCategories();

        spinnerCategory.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        loadFlashcards();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        loadFlashcards();
    }

    private void loadCategories() {

        categoryList = new ArrayList<>();
        categoryList.add("All Categories");

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT category FROM flashcards",
                null
        );

        Set<String> uniqueCategories = new LinkedHashSet<>();

        while (cursor.moveToNext()) {
            uniqueCategories.add(cursor.getString(0));
        }

        cursor.close();

        categoryList.addAll(uniqueCategories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryList
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerCategory.setAdapter(adapter);
    }

    private void loadFlashcards() {

        flashcards = new ArrayList<>();
        flashcardIds = new ArrayList<>();
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        categories = new ArrayList<>();

        String selectedCategory = "All Categories";

        if (spinnerCategory != null &&
                spinnerCategory.getSelectedItem() != null) {

            selectedCategory =
                    spinnerCategory.getSelectedItem().toString();
        }

        Cursor cursor;

        if (selectedCategory.equals("All Categories")) {

            cursor = databaseHelper.getReadableDatabase().rawQuery(
                    "SELECT id, question, answer, category FROM flashcards",
                    null
            );

        } else {

            cursor = databaseHelper.getReadableDatabase().rawQuery(
                    "SELECT id, question, answer, category " +
                            "FROM flashcards WHERE category = ?",
                    new String[]{selectedCategory}
            );
        }

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String question = cursor.getString(1);
            String answer = cursor.getString(2);
            String category = cursor.getString(3);

            flashcards.add(question);
            flashcardIds.add(id);
            questions.add(question);
            answers.add(answer);
            categories.add(category);
        }

        cursor.close();

        FlashcardAdapter adapter = new FlashcardAdapter();

        listViewFlashcards.setAdapter(adapter);

        listViewFlashcards.setOnItemClickListener(
                (parent, view, position, id) ->
                        showOptions(position)
        );
    }

    private class FlashcardAdapter extends ArrayAdapter<String> {

        FlashcardAdapter() {
            super(
                    FlashcardListActivity.this,
                    R.layout.item_flashcard,
                    flashcards
            );
        }

        @Override
        public View getView(
                int position,
                View convertView,
                ViewGroup parent) {

            View view;

            if (convertView == null) {

                view = LayoutInflater.from(
                        FlashcardListActivity.this
                ).inflate(
                        R.layout.item_flashcard,
                        parent,
                        false
                );

            } else {

                view = convertView;
            }

            TextView tvQuestion =
                    view.findViewById(R.id.tvItemQuestion);

            TextView tvAnswer =
                    view.findViewById(R.id.tvItemAnswer);

            TextView tvCategory =
                    view.findViewById(R.id.tvItemCategory);

            tvQuestion.setText(
                    "Question: " + questions.get(position)
            );

            tvAnswer.setText(
                    "Answer: " + answers.get(position)
            );

            tvCategory.setText(
                    "Category: " + categories.get(position)
            );

            return view;
        }
    }

    private void showOptions(int position) {

        String[] options = {
                "Edit",
                "Delete",
                "Cancel"
        };

        new AlertDialog.Builder(this)
                .setTitle("Flashcard Options")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {

                        editFlashcard(position);

                    } else if (which == 1) {

                        deleteFlashcard(position);
                    }
                })
                .show();
    }

    private void editFlashcard(int position) {

        Intent intent = new Intent(
                FlashcardListActivity.this,
                EditFlashCardActivity.class
        );

        intent.putExtra(
                "flashcard_id",
                flashcardIds.get(position)
        );

        intent.putExtra(
                "question",
                questions.get(position)
        );

        intent.putExtra(
                "answer",
                answers.get(position)
        );

        intent.putExtra(
                "category",
                categories.get(position)
        );

        startActivity(intent);
    }

    private void deleteFlashcard(int position) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Flashcard")
                .setMessage(
                        "Do you want to delete this flashcard?"
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            int flashcardId =
                                    flashcardIds.get(position);

                            boolean deleted =
                                    databaseHelper.deleteFlashcard(
                                            flashcardId
                                    );

                            if (deleted) {

                                Toast.makeText(
                                        this,
                                        "Flashcard deleted",
                                        Toast.LENGTH_SHORT
                                ).show();

                                loadCategories();
                                loadFlashcards();

                            } else {

                                Toast.makeText(
                                        this,
                                        "Delete failed",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null) {

            loadCategories();
            loadFlashcards();
        }
    }
}