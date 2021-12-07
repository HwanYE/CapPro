package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        for(int i = 0; i < 4; i++){
            insertDataTable();
        }
        TextView username = (TextView)findViewById(R.id.txt_display_name);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        int market_1 = intent.getIntExtra("market_1_data",1);
        int market_2 = intent.getIntExtra("market_2_data",1);

        if(market_1 == 1){
            ImageView image = (ImageView)findViewById(R.id.img_marketinfo);

            image.setImageResource(R.drawable.img_possible);
        }
        if(market_2 == 0){
            ImageView image = (ImageView)findViewById(R.id.img_marketinfo2);

            image.setImageResource(R.drawable.img_impossible);
        }
        username.setText("환영합니다!" + id);
    }


    public void button_needInfo(View view){
        Intent needinfo = new Intent(getApplicationContext(), Activity_foodinfo.class);

        startActivity(needinfo);
    }
    public void btn_market_more_Clicked(View view){
        Intent marketinfo = new Intent(getApplicationContext(), ActivityMarketInfo.class);

        startActivity(marketinfo);

    }

    public void insertDataTable()
    {
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        for(int i =0; i < 2; i++){
            TextView textView = new TextView(this);
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