package com.example.helloworld;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TranslationFragment extends Fragment {

    private Spinner spinnerSourceLanguage;
    private Spinner spinnerTargetLanguage;
    private EditText editTextLetters;
    private EditText editTxtTranslated; // Added this line
    private Button btnTranslate;

    public TranslationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translation, container, false);

        spinnerSourceLanguage = view.findViewById(R.id.spinnerSourceLanguage);
        spinnerTargetLanguage = view.findViewById(R.id.spinnerTargetLanguage);
        editTextLetters = view.findViewById(R.id.editTxtletters);
        editTxtTranslated = view.findViewById(R.id.editTxtTranslated); // Added this line
        btnTranslate = view.findViewById(R.id.btnTranslate);

        // Populate Spinners with supported languages
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSourceLanguage.setAdapter(adapter);
        spinnerTargetLanguage.setAdapter(adapter);

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedSourceLanguage = spinnerSourceLanguage.getSelectedItem().toString();
                String selectedTargetLanguage = spinnerTargetLanguage.getSelectedItem().toString();

                // Extract language codes from selected items
                String sourceLanguageCode = extractLanguageCode(selectedSourceLanguage);
                String targetLanguageCode = extractLanguageCode(selectedTargetLanguage);

                // Use sourceLanguageCode and targetLanguageCode in TranslatorOptions.Builder
                TranslatorOptions options = new TranslatorOptions.Builder()
                        .setTargetLanguage(targetLanguageCode)
                        .setSourceLanguage(sourceLanguageCode)
                        .build();
                Translator translator = Translation.getClient(options);

                String sourceText = editTextLetters.getText().toString();
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Downloading the translation model...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });

                Task<String> result = translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String translatedText) {
                        // Set the translated text to the new EditText
                        editTxtTranslated.setText(translatedText);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    // Helper method to extract language code from the selected item
    private String extractLanguageCode(String selectedItem) {
        // Assuming the language code is present after the last space
        String[] parts = selectedItem.split(" ");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return ""; // Handle the case where no code is found
    }
}
