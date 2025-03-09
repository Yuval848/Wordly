package com.example.wordly_by_yuvalmiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class  SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private String[] arrcolor = {"","Blue","Pink","Cyan","Purple","Orange"};
    private boolean isFirstime = true;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        Intent i = getIntent();
        String color = i.getStringExtra("color");
        linearLayout = findViewById(R.id.settings_activity);
        setBackgroundColor(color);

        ArrayAdapter aa =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrcolor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(aa);




    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view,int position, long l) {
        //Toast.makeText(this,"onItemSelected", Toast.LENGTH_SHORT).show();
        if(isFirstime == false)
        {
            Intent intent = new Intent();

            intent.putExtra("color",arrcolor[position]);
            setResult(RESULT_OK,intent);

            setBackgroundColor(arrcolor[position]);
            finish();

        }
        isFirstime= false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void setBackgroundColor(String str) {
        switch (str)
        {
            case"Blue":
            {
                linearLayout.setBackgroundColor(Color.BLUE);
                break;
            }
            case"Purple":
            {
                linearLayout.setBackgroundColor(Color.argb(255,167,29,216));
                break;

            }
            case"Orange":
            {
                linearLayout.setBackgroundColor(Color.argb(255,217,105,28));
                break;

            }


            case "Pink":
            {
                linearLayout.setBackgroundColor(Color.argb(255,255,192,203));
                break;
            }
            case "Cyan":
            {
                linearLayout.setBackgroundColor(Color.argb(255, 0,255,255));
            }

            default:
                break;


        }
    }
}