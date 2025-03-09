package com.example.wordly_by_yuvalmiz;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

class KeyboardView extends LinearLayout implements View.OnClickListener {

    String str="";
    Context context;
    TextView tv;

    public  KeyboardView(Context context)
    {
        super(context);
        this.context=context;
        tv=new TextView(context);
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
        tv.setTextDirection(View.TEXT_DIRECTION_RTL);
        tv.setTextSize(30);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setOrientation(VERTICAL);
        this.addView(tv);
        loadLeboard('a',9);
        loadLeboard('j',9);
        loadLeboard('s',8);
        loadSpaceAndDelete();
    }



    public void loadLeboard(char ch, int len)
    {
        LinearLayout l = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(100, 50, 0, 0);
        l.setLayoutParams(layoutParams);

        for(int i=0;i<len;i++)
        {
            Button btn=new Button(context);
            btn.setBackgroundResource(android.R.drawable.editbox_background);
            btn.setText((String.valueOf(ch)).toLowerCase());
            //LayoutParams btnParam=new LayoutParams(120,120);
            LayoutParams btnParam=new LayoutParams(85,85);
            btn.setLayoutParams(btnParam);
            l.addView(btn);
            ch++;
            btn.setOnClickListener(this);
        }

        this.addView(l);
    }

    public void loadSpaceAndDelete()
    {
        Button submit=new Button(context);
        submit.setBackgroundResource(android.R.drawable.editbox_background);
        submit.setBackgroundColor(Color.GRAY);
        LayoutParams btnParam=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParam.setMargins(100, 10, 20, 0);
        submit.setLayoutParams(btnParam);
        submit.setText(String.valueOf("submit"));
        this.addView(submit);

        submit.setOnClickListener(this);
        Button delete=new Button(context);
        delete.setBackgroundResource(android.R.drawable.editbox_background);
        delete.setText(String.valueOf("delete"));
        delete.setLayoutParams(btnParam);
        delete.setBackgroundColor(Color.GRAY);

        delete.setOnClickListener(this);

        this.addView(delete);
    }

    @Override

    public void onClick(View v) {
        Button btn = (Button) v;
        String s= btn.getText().toString();
        if(s.equals("delete"))
        {
            if(this.str.length()>0)
                this.str=this.str.substring(0,this.str.length()-1);
        }
        else if(s.equals("submit"))
        {
            ((GameActivity)context).transferUserGuess(str);
            this.str ="";

        }
        else
        {
            this.str += btn.getText().toString();
        }
        tv.setText(str);
    }
}

