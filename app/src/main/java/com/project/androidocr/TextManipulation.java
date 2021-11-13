package com.project.androidocr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import android.speech.tts.TextToSpeech;

public class TextManipulation extends AppCompatActivity {
    private float defFontsize = 14f;
    private String textData = "";
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        textData = i.getStringExtra("textString");
        setContentView(R.layout.activity_text_manipulation);
        TextView textShow = (TextView) findViewById (R.id.textShow);
        textShow.setText(textData);

//      For text2Speech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale(Locale.getDefault().getLanguage()));
                }
            }
        });
    }

    public void ZoomOut (View view) {
        defFontsize -= 4f;
        if(defFontsize <= 4f)
            defFontsize = 4f;
        TextView textShow = (TextView) findViewById (R.id.textShow);
        textShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, defFontsize);
        // Do something in response to button click
    }

    public void ZoomIn (View view) {
        defFontsize += 4f;
        TextView textShow = (TextView) findViewById (R.id.textShow);
        textShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, defFontsize);
        // Do something in response to button click
    }


    public void Share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textData);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void Text2Speech(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textData);
        sendIntent.setType("text/plain");

        TextView textBox = (TextView) findViewById (R.id.textShow);
        textToSpeech.speak(textBox.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

    }



}