package com.bryle_sanico.diceactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;
    Button playBtn;
    Button resetBtn;
    int currentCell = 0;
    MediaPlayer climbingSound;
    MediaPlayer winSound;

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

        // Initialize MediaPlayer objects for audio
        climbingSound = MediaPlayer.create(this, R.raw.climbing);
        winSound = MediaPlayer.create(this, R.raw.win);

        setupPlayButton();
        setupResetButton();
        createTextViews(rowCount, colCount, startValue);
    }

    private void setupPlayButton() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomNumber = rollDice();
                updateDiceImage(randomNumber);

                int newCurrentCellValue = updateCurrentCellValue(randomNumber);

                updatePreviousTextViewColor(currentCell);
                currentCell = newCurrentCellValue;
                updateCurrentTextViewColor(currentCell);

                if (currentCell == 60) {
                    showToast("You win!");
                    playBtn.setEnabled(false);
                    playSound(winSound);
                } else {
                    playSound(climbingSound);
                }
            }
        });
    }

    private int rollDice() {
        return new Random().nextInt(6) + 1;
    }

    private void updateDiceImage(int randomNumber) {
        ImageView diceImg = findViewById(R.id.diceImg);
        int drawableResource = getResources().getIdentifier("dice" + randomNumber, "drawable", getPackageName());
        diceImg.setImageResource(drawableResource);
    }

    private int updateCurrentCellValue(int randomNumber) {
        int newCurrentCellValue = currentCell + randomNumber;
        if (newCurrentCellValue > 60) {
            int remainder = newCurrentCellValue % 60;
            newCurrentCellValue = 60 - remainder;
        }
        return newCurrentCellValue;
    }

    private void updatePreviousTextViewColor(int cellValue) {
        TextView previousTextView = findViewById(cellValue);
        if (previousTextView != null) {
            previousTextView.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void updateCurrentTextViewColor(int cellValue) {
        TextView updatedTextView = findViewById(cellValue);
        if (updatedTextView != null) {
            updatedTextView.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            showToast("TextView with ID " + currentCell + " not found.");
        }
    }

    private void playSound(MediaPlayer sound) {
        sound.start();
    }

    private void setupResetButton() {
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCell = 0;
                playBtn.setEnabled(true);
                clearBackgroundColors();
            }
        });
    }

    private void clearBackgroundColors() {
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);

            for (int j = 0; j < tableRow.getChildCount(); j++) {
                TextView textView = (TextView) tableRow.getChildAt(j);
                textView.setBackgroundColor(getResources().getColor(android.R.color.white));
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createTextViews(int rowCount, int colCount, int startValue) {
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
                cellParams.setMargins(5, 5, 5, 5);
                textView.setLayoutParams(cellParams);

                if (i % 2 == 1) {
                    value = startValue - ((i - 1) * colCount + j - 1);
                } else {
                    value = startValue - (i * colCount - j);
                }

                textView.setId(value);
                textView.setTag(value);
                textView.setShadowLayer(1.6f, 1.5f, 1.3f, getResources().getColor(android.R.color.black));

                if (value == 1) {
                    textView.setText("START");
                    textView.setTextSize(19);
                    textView.setPadding(0, 35, 0, 10);
                } else if (value == 60) {
                    textView.setText("FINISH");
                    textView.setTextSize(19);
                    textView.setPadding(0, 35, 0, 10);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (climbingSound != null) {
            climbingSound.release();
        }
        if (winSound != null) {
            winSound.release();
        }
    }
}
