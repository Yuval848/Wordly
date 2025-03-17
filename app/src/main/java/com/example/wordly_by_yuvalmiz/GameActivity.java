package com.example.wordly_by_yuvalmiz;


import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends AppCompatActivity {
    private BoardGame boardGame;
    TextToSpeech textToSpeech;
    SoundPool soundPool;
    int sound1,sound2;

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
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        sound1 = soundPool.load(this,R.raw.wrongsound,1);
        sound2 = soundPool.load(this,R.raw.correctsound,1);


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
                soundPool.play(sound2,1,1,0,3,1);
                textToSpeech.speak("Congratulation you have won", TextToSpeech.QUEUE_FLUSH, null);


                createDialog();
            }
            Greenflag =0;
        }
        if(attempts == 6)
        {
            soundPool.play(sound1,1,1,0,0,1);
            createDialog();
        }

    }

    private boolean isWordValid(String word) {
        String API_KEY = "8e8sodqspul7qzwvfocimwsu3g9tua7qg6ktlgx13zptyo57h";  // Replace with your Wordnik API key
        String url = "https://api.wordnik.com/v4/word.json/" + word + "/definitions?api_key=" + API_KEY;

        DownloadJson downloadJson = new DownloadJson();

        try {
            // Perform the network operation to get the JSON response
            String result = downloadJson.execute(url).get();

            // Check if the result is empty or null
            if (result == null || result.isEmpty()) {
                return false; // If the response is empty, the word is not valid
            }

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(result);

            // If there are any definitions (jsonArray length > 0), proceed to check further
            if (jsonArray.length() > 0) {
                // Loop through all the definitions in the JSON response
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject definition = jsonArray.getJSONObject(i);

                    // Check if the definition has a valid "text" field with a non-empty value
                    if (definition.has("text") && !definition.getString("text").isEmpty()) {
                        String definitionText = definition.getString("text").trim();

                        // Check if the definition text is not just empty or whitespace
                        if (!definitionText.isEmpty() && !definitionText.equalsIgnoreCase("no definition found")) {
                            return true; // The word has at least one valid definition with content
                        }
                    }
                }
            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace(); // Handle exceptions (network issues, JSON parsing issues)
        }

        // If no valid definition was found, return false
        return false;
    }


    private void createDialog() {
        CustomDialog customDialog = new CustomDialog(this);
        textToSpeech.speak("Would you like to play again?", TextToSpeech.QUEUE_FLUSH, null);
        customDialog.show();


    }
    private boolean YellowSquare(char a) {
        for (int j = 0; j <5; j++) {
            if (a ==targetWord.charAt(j))
                return true;
        }
        return false;
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