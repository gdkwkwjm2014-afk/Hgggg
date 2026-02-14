package com.darkness.loader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;

public class MainActivity extends Activity {

    private TextView statusText, keyExpiry;
    private Button btnStart, btnStop;
    private Spinner versionSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Привязываем элементы дизайна
        statusText = findViewById(R.id.status);
        keyExpiry = findViewById(R.id.keyExpiry);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        versionSelector = findViewById(R.id.versionSelector);

        // Настройка выбора версий PUBG
        String[] versions = {"PUBG Global", "PUBG Korea", "PUBG Vietnam", "PUBG Taiwan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, versions);
        versionSelector.setAdapter(adapter);

        // Анимация пульсации для кнопок (Красное свечение)
        final Animation pulse = new AlphaAnimation(1.0f, 0.5f);
        pulse.setDuration(800);
        pulse.setRepeatMode(Animation.REVERSE);
        pulse.setRepeatCount(Animation.INFINITE);

        // Кнопка START
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = versionSelector.getSelectedItem().toString();
                String pkg = getPackageNameByVersion(selected);

                // Имитация входа в игру
                statusText.setText("STATUS: CHEAT ACTIVE");
                statusText.setTextColor(Color.RED);
                statusText.startAnimation(pulse); // Начинает мигать

                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pkg);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                    Toast.makeText(MainActivity.this, "Darkness VIP Started!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Install " + selected + " first!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Кнопка STOP
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusText.clearAnimation();
                statusText.setText("STATUS: CLEANED");
                statusText.setTextColor(Color.GRAY);
                Toast.makeText(MainActivity.this, "Memory Cleaned. Safe to exit.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Метод для определения пакета игры
    private String getPackageNameByVersion(String version) {
        switch (version) {
            case "PUBG Korea": return "com.pubg.krmobile";
            case "PUBG Vietnam": return "com.vng.pubgmobile";
            case "PUBG Taiwan": return "com.rekoo.pubgm";
            default: return "com.tencent.ig";
        }
    }
}

