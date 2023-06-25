package com.example.txtscn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    EditText edittext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button backButton = (Button) this.findViewById(R.id.button4);
        edittext1 = findViewById(R.id.editText1);

        String uriString = getIntent().getStringExtra("uri");
        Uri uri = Uri.parse(uriString);
        _extractTextFromUri(getApplicationContext(), uri);

        backButton.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    getApplicationContext(), MainActivity.class
            );
            startActivity(mainActivityIntent);
        });

    }

    public void _extractTextFromUri(Context context, Uri _uri) {

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        try {
            InputImage image = InputImage.fromFilePath(context, _uri);
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text visionText) {
                                    // Task completed successfully
                                    // ...
                                    edittext1.setText(visionText.getText());
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Task failed with an exception
                                            // ...
                                        }
                                    });
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}