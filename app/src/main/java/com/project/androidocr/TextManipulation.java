package com.project.androidocr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class TextManipulation extends AppCompatActivity {
    private float defFontsize = 14f;
    private String textData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        textData = i.getStringExtra("testString");
        textData = "textString";
        setContentView(R.layout.activity_text_manipulation);
        TextView textShow = (TextView) findViewById (R.id.textShow);
        textShow.setText(textData);

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
}