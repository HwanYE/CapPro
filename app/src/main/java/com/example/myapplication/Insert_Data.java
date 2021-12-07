package com.example.myapplication;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Insert_Data {
    public void insertDataTable(TableLayout tableLayout, TableRow tableRow, TextView textView)
    {
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        for(int i =0; i < 2; i++){

            textView.setText(String.valueOf(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(34);

            textView.setBackgroundResource(R.drawable.roundedtextview);
            textView.setTextColor(Color.parseColor("#000000"));
            tableRow.addView(textView);

        }

        tableLayout.addView(tableRow);
    }
}
