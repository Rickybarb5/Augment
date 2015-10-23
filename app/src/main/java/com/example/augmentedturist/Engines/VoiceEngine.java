package com.example.augmentedturist.Engines;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Ricardo Barbosa on 28/09/2015.
 */
public class VoiceEngine {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static Activity c;

    public VoiceEngine(Activity c) {
        this.c = c;

    }

    public static void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "fala");
        try {
            c.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(c.getApplicationContext(),
                    "Not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
