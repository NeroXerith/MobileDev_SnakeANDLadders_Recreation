package com.bryle_sanico.diceactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableTest);

        int rowCount = 12;  // Number of rows (you can adjust this value)
        int colCount = 5;   // Number of columns (you can adjust this value)
        int startValue = 60;
        int endValue = 1;

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

                if(value==60){
                    textView.setText("END");
                    textView.setTextSize(30);
                } else if (value==1){
                    textView.setTextSize(19);
                    textView.setText("START");
                    textView.setPadding(0, 35, 0, 10);

                } else {
                    textView.setText(String.valueOf(value));
                    textView.setTextSize(30);
                }


                // Create a custom background with a border
                textView.setBackgroundResource(R.drawable.textview_border);

                textView.setGravity(android.view.Gravity.CENTER);
                row.addView(textView);
            }

            tableLayout.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));
        }
    }
}
