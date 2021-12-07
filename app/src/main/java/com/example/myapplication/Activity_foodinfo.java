package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_foodinfo extends AppCompatActivity {

    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodinfo);

        for(int i = 0; i<20; i++){
            insertDataTable(i);
        }

        scrollView = (ScrollView) findViewById(R.id.scrollView);


    }

    public void insertDataTable(int i)
    {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/welcome_regular.ttf"); // font 폴더내에 있는 jua.ttf 파일을 typeface로 설정


        TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        for(int j =0; j < 2; j++){
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i));
            textView.setTypeface(typeface,Typeface.NORMAL); // messsage는 TextView 변수
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(32);

            textView.setBackgroundResource(R.drawable.roundedtextview);
            textView.setTextColor(Color.parseColor("#000000"));
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);
    }




}