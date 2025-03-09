package com.example.wordly_by_yuvalmiz;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GameActivity extends AppCompatActivity {
    private BoardGame boardGame;

    LinearLayout linearLayout,
            linearLayout1,
            linearLayout2,//for all the cells of the guesses
            linearLayout3;
    KeyboardView m;


    private String targetWord;
    private int attempts=0;
    String result = null;

    String random5LetterWord = "https://random-word-api.herokuapp.com/word?length=5";
    String userGuess;
    int Greenflag =0; //count of how many letters are correct
    private DownloadJson downloanJson;

    public void transferUserGuess(String str) {
        userGuess = str;
        startGame();
    }

    public class DownloadJson extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            InputStreamReader inputStreamReader;

            try {
                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1)
                {
                    result += (char)data;
                    data = inputStreamReader.read();
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return result;
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        linearLayout = findViewById(R.id.activitygame);
        linearLayout1 = findViewById(R.id.firstLayout);
        linearLayout2 = findViewById(R.id.secondLayout);
        linearLayout3 = findViewById(R.id.thirdLayout);

        boardGame = new BoardGame(this);
        linearLayout2.addView(boardGame);
        m=new KeyboardView(this);
        linearLayout3.addView(m);

        Intent i = getIntent();
        String color = i.getStringExtra("color");
        setBackgroundColor(color);

        String url = random5LetterWord;

        downloanJson = new DownloadJson();

        try {
            result = downloanJson.execute(url).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        targetWord = result.replaceAll("[\\[\\]\" ]", "");
        Toast.makeText(this, ""+targetWord, Toast.LENGTH_SHORT).show();


    }

    public void resetGame()
    {
        String url = random5LetterWord;

        downloanJson = new DownloadJson();

        try {
            result = downloanJson.execute(url).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        targetWord = result.replaceAll("[\\[\\]\" ]", "");
        Toast.makeText(this, ""+targetWord, Toast.LENGTH_SHORT).show();

        attempts =0;
        boardGame = new BoardGame(this);
        linearLayout2.removeAllViews();
        linearLayout2.addView(boardGame);

    }



    private void startGame() {
        if (attempts < 6) {
            if (userGuess.length() != 5) {
                Toast.makeText(this, "Your word is not 5 letters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isWordValid(userGuess)) {
                Toast.makeText(this, "Your word does not exist in English", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isWordValid(userGuess) && userGuess.length() == 5) {
                for (int i = 0; i < 5; i++) {
                    if (userGuess.charAt(i) == targetWord.charAt(i)) {
                        boardGame.setCellBackgroundColor(attempts, i, Color.GREEN);
                        Greenflag++;
                    }
                    else if (YellowSquare(userGuess.charAt(i)))
                        boardGame.setCellBackgroundColor(attempts, i, Color.YELLOW);
                    else
                        boardGame.setCellBackgroundColor(attempts, i, Color.RED);


                }
                for (int i = 0; i < 5; i++) {
                    boardGame.setNewWord(attempts,userGuess);

                }
                attempts++;
            }

            if(Greenflag == 5)
            {
                Toast.makeText(this, "congratulations you have won", Toast.LENGTH_SHORT).show();
                createDialog();
            }
            Greenflag =0;
        }
        if(attempts == 6)
            createDialog();
    }



    private void createDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.show();
    }
    private boolean YellowSquare(char a) {
        for (int j = 0; j <5; j++) {
            if (a ==targetWord.charAt(j))
                return true;
        }
        return false;
    }


    private boolean isWordValid(String word) {
        /// TODO: 13/01/2025 להפוך את הקו השחור שירשום במקום בקו על הרשת
        /// TODO: 13/01/2025 https://api.datamuse.com/words?sp=YOUR_WORD_HERE, הרעיון הוא שתיקח את הלינק הזה שבו אתה מקבל responses ואיתם אתה עובר על המילים שקיבלת ובודק אם היא שם.

        return(true);
    }




    private void setBackgroundColor(String backgroundColor) {
        switch (backgroundColor)
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