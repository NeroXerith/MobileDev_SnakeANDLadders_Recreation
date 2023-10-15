package com.bryle_sanico.diceactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;
    Button playBtn;
    Button resetBtn;
    int currentRow;
    int currentCol;
    int constantValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableTest);
        playBtn = findViewById(R.id.playBtn);
        resetBtn = findViewById(R.id.resetBtn);
        int startValue = 60;
        int rowCount = 12;  // Number of rows
        int colCount = 5;   // Number of columns

        currentRow = rowCount - 1;  // Start from the bottom row
        currentCol = 0;  // Start from the first column

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Generate a random number between 1 and 6
                Random random = new Random();
                int randomNumber = random.nextInt(6) + 1;

                // Calculate the new constant value by adding the random number
                int newConstantValue = constantValue + randomNumber;

                // Check if the new constant value exceeds 60
                if (newConstantValue > 60) {
                    int remainder = newConstantValue % 60;
                    newConstantValue = 60 - remainder;
                }

                // Find the TextView with the previous constant value and change its background color to white
                TextView previousTextView = findViewById(constantValue);
                if (previousTextView != null) {
                    previousTextView.setBackgroundColor(getResources().getColor(android.R.color.white));
                }

                // Update the constant value to the new value
                constantValue = newConstantValue;

                // Find the TextView with the updated constant value and change its background color to black
                TextView updatedTextView = findViewById(constantValue);

                if (updatedTextView != null) {
                    updatedTextView.setBackgroundColor(getResources().getColor(android.R.color.black));
                } else {
                    showToast("TextView with ID " + constantValue + " not found.");
                }

                // Check if the player has won
                if (constantValue == 60) {
                    showToast("You win!");
                    playBtn.setEnabled(false); // Disable the play button
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reset the constant value to 0
                constantValue = 0;

                // Enable the play button
                playBtn.setEnabled(true);

                // Reset the background color of all TextViews
                clearBackgroundColors();
            }
        });


        for (int i = 1; i <= rowCount; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            row.setLayoutParams(layoutParams);

            for (int j = 1; j <= colCount; j++) {
                int value;
                TextView textView = new TextView(this);
                TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1
                );
                textView.setLayoutParams(cellParams);

                if (i % 2 == 1) {
                    // Descending sequence
                    value = startValue - ((i - 1) * colCount + j - 1);
                } else {
                    // Ascending sequence
                    value = startValue - (i * colCount - j);
                }

                // Generate a unique ID based on the value
                int textViewId = value;

                textView.setId(textViewId);  // Set the ID of the TextView
                textView.setTag(value);  // Set the tag as the value
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int cellValue = (int) view.getTag();  // Get the value from the tag
                        // Handle clicks on TextView with value = cellValue
                    }
                });

                if (value == 1) {
                    textView.setText("START");
                    textView.setTextSize(19);
                    textView.setPadding(0, 35, 0, 10);
                } else if (value == 60) {
                    textView.setText("END");
                    textView.setTextSize(30);
                } else {
                    textView.setText(String.valueOf(value));
                    textView.setTextSize(30);
                }

                textView.setBackgroundColor(getResources().getColor(android.R.color.white));

                textView.setGravity(android.view.Gravity.CENTER);
                row.addView(textView);
            }

            tableLayout.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));
        }
    }


    // Clear background colors for all TextViews
    private void clearBackgroundColors() {
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);

            for (int j = 0; j < tableRow.getChildCount(); j++) {
                TextView textView = (TextView) tableRow.getChildAt(j);
                textView.setBackgroundColor(getResources().getColor(android.R.color.white));
            }
        }
    }

    // Function to display a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
