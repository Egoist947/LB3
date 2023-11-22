package com.example.musicshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicshop.R;

public class MainActivity extends AppCompatActivity {
    private TextView textView10;
    private TextView textView5;
    private Spinner instrumentSpinner;
    private ImageView imageView5;

    private int plusAmount = 1;
    private int minusAmount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView10 = findViewById(R.id.textView10);
        textView5 = findViewById(R.id.textView5);
        instrumentSpinner = findViewById(R.id.instrumentSpinner);
        imageView5 = findViewById(R.id.imageView5);

        Button plusButton = findViewById(R.id.plusButton);
        Button minusButton = findViewById(R.id.minusButton);
        Button button = findViewById(R.id.button);
        EditText editTextChelikName = findViewById(R.id.chelik_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chelikName = editTextChelikName.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("chelikName", chelikName);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        String[] instruments = getResources().getStringArray(R.array.instruments);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instruments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instrumentSpinner.setAdapter(adapter);

        instrumentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView5.setText("0");
                textView10.setText("0");

                String selectedInstrument = instruments[position];
                switch (selectedInstrument) {
                    case "guitar":
                        plusAmount = 3;
                        minusAmount = 3;
                        imageView5.setImageResource(R.drawable.guitar_image);
                        break;
                    case "drum":
                        plusAmount = 2;
                        minusAmount = 2;
                        imageView5.setImageResource(R.drawable.drum_image);
                        break;
                    case "keyboard":
                        plusAmount = 1;
                        minusAmount = 1;
                        imageView5.setImageResource(R.drawable.keyboard_image);
                        break;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedInstrument", selectedInstrument);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ничего не делаем при отсутствии выбора
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue10 = Integer.parseInt(textView10.getText().toString());
                textView10.setText(String.valueOf(currentValue10 + 1));

                int currentValue5 = Integer.parseInt(textView5.getText().toString());
                textView5.setText(String.valueOf(currentValue5 + plusAmount));

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("textView10Value", currentValue10 + 1);
                editor.putInt("textView5Value", currentValue5 + plusAmount);
                editor.apply();
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue10 = Integer.parseInt(textView10.getText().toString());
                if (currentValue10 > 0) {
                    textView10.setText(String.valueOf(currentValue10 - 1));
                }

                int currentValue5 = Integer.parseInt(textView5.getText().toString());
                if (currentValue5 >= minusAmount) {
                    textView5.setText(String.valueOf(currentValue5 - minusAmount));
                }

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("textView10Value", currentValue10 - 1);
                editor.putInt("textView5Value", currentValue5 - minusAmount);
                editor.apply();
            }
        });
    }
}
