package com.example.musicshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int textView10Value = sharedPreferences.getInt("textView10Value", 0);
        int textView5Value = sharedPreferences.getInt("textView5Value", 0);
        String selectedInstrument = sharedPreferences.getString("selectedInstrument", "");

        TextView textView8 = findViewById(R.id.textView8);
        textView8.setText(selectedInstrument);

        String chelikName = sharedPreferences.getString("chelikName", "");
        TextView nameOfZakz = findViewById(R.id.name_of_zakz);
        nameOfZakz.setText(chelikName);

        TextView colVo = findViewById(R.id.col_vo);
        colVo.setText(String.valueOf(textView10Value));

        TextView cena = findViewById(R.id.cena);
        cena.setText(String.valueOf(textView5Value));

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sasadevatovskij@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Заказ с MusicShop");

                TextView textView8 = findViewById(R.id.textView8);
                TextView colVo = findViewById(R.id.col_vo);
                TextView cena = findViewById(R.id.cena);
                TextView name = findViewById(R.id.name_of_zakz);

                String messageBody = "Имя заказчика: " + name.getText() + "\n" +
                        "Товар в корзине: " + textView8.getText() + "\n" +
                        "Количество товара: " + colVo.getText() + "\n" +
                        "Цена заказа: " + cena.getText();

                intent.putExtra(Intent.EXTRA_TEXT, messageBody);

                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(Intent.createChooser(intent, "Выберите приложение для отправки"));
                } else {
                    // Обработка случая, когда приложение для отправки отсутствует
                }
            }
        });
    }
}
