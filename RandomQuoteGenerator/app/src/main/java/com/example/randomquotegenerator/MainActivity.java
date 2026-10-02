package com.example.randomquotegenerator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvQuote, tvAuthor;
    Button btnNewQuote;

    String[] quotes = {
            "Believe you can and you're halfway there.",
            "The future depends on what you do today.",
            "Success is the sum of small efforts.",
            "Dream big and dare to fail.",
            "Do something today that your future self will thank you.",
            "It always seems impossible until it's done.",
            "Stay positive, work hard, make it happen.",
            "The best way to predict the future is to create it."
    };

    String[] authors = {
            "Theodore Roosevelt",
            "Mahatma Gandhi",
            "Robert Collier",
            "Norman Vaughan",
            "Sean Patrick Flanery",
            "Nelson Mandela",
            "Unknown",
            "Peter Drucker"
    };

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuote = findViewById(R.id.tvQuote);
        tvAuthor = findViewById(R.id.tvAuthor);
        btnNewQuote = findViewById(R.id.btnNewQuote);

        showRandomQuote();

        btnNewQuote.setOnClickListener(v -> showRandomQuote());
    }

    private void showRandomQuote() {

        int index = random.nextInt(quotes.length);

        tvQuote.setText(getString(R.string.quote_format, quotes[index]));

        tvAuthor.setText(getString(R.string.author_format, authors[index]));
    }
}