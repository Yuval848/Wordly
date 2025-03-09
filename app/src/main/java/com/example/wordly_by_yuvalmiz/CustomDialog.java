package com.example.wordly_by_yuvalmiz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Button btnYes, btnNo;
    private Context context;

    public CustomDialog(@NonNull Context context) {
        super(context);
        //the context reference (print) to the game activity
        setContentView(R.layout.custom_dialog);
        this.context = context;

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (btnYes == view) {
            dismiss();// closing the dialog.
            ((GameActivity) context).resetGame();

        }
        if (btnNo == view) {
            ((GameActivity) context).finish();
        }
    }
}
