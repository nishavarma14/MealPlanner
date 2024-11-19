package com.example.myapplication;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class appointmentsFragment extends Fragment {

    ImageView ivmic;
    TextView tvtext;


private static final int REQUEST_CODE_SPEECH_INPUT = 1;

TextToSpeech textToSpeech;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_appointments, container, false);

        ivmic = view.findViewById(R.id.ivmic);
        tvtext = view.findViewById(R.id.tvtext);

        ivmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }catch (Exception e)
                {
                    Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT)
        {
            if (resultCode == RESULT_OK && data!=null)
            {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);

                tvtext.setText(result.get(0));

                textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        textToSpeech.setLanguage(Locale.ENGLISH);
                        textToSpeech.speak(result.get(0),
                                TextToSpeech.QUEUE_FLUSH,null,null);

                    textToSpeech.setSpeechRate(20);
                    }
                });
            }
        }
    }
}