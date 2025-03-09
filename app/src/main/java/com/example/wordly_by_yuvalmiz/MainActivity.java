package com.example.wordly_by_yuvalmiz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartGame, btnSetting, btnInstruction;
    private String backgroundColor = "Blue";
    private FbModule fbModule;
    private LinearLayout linearLayout;

    private ActivityResultLauncher <Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.main);


        init();
    }

    private void init()
    {

        btnStartGame = findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(this);
        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(this);
        btnInstruction = findViewById(R.id.btnInstruction);
        btnInstruction.setOnClickListener(this);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_OK)
                        {
                            Intent data = result.getData();
                            String str = data.getStringExtra("color");
                            fbModule.changeBackgroundColorInFireBase(str);
                            Toast.makeText(MainActivity.this,""+str,Toast.LENGTH_LONG).show();
                        }
                    }
                });
        fbModule = new FbModule(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnStartGame)
        {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("color", backgroundColor);//הפעולה מעבירה  מידע
            startActivity(intent);
        }
        if(v == btnSetting)
        {
            Intent i = new Intent(this,SettingsActivity.class);
            i.putExtra("color", backgroundColor);//הפעולה מעבירה  מידע
            activityResultLauncher.launch(i);
        }
        if(v == btnInstruction)
        {
            Intent i = new Intent(this,InstructionsActivity.class);
            i.putExtra("color", backgroundColor);
            startActivity(i);
        }
    }

    public void setBackgroundColor(String str) {
        backgroundColor= str;

        /*
        switch (str)
        {
            case"Blue":
            {
                linearLayout.setBackgroundColor(Color.BLUE);
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


        }*/
    }
}