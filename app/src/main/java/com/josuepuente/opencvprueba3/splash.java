package com.josuepuente.opencvprueba3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Establecer el layout del Splash Screen
        setContentView(R.layout.activity_splash);

        // Establecer el tiempo de duración del Splash Screen
        int splashScreenDuration = 2000; // 2 segundos
        new Handler().postDelayed(() -> {
            // Redirigir al usuario a la actividad de inicio de sesión o al dashboard
            if (userAlreadyLoggedIn()) {
                Intent intent = new Intent(splash.this, start.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(splash.this, Login.class);
                startActivity(intent);
            }
            // Cerrar la actividad del Splash Screen
            finish();
        }, splashScreenDuration);
    }

    private boolean userAlreadyLoggedIn() {
            SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            return isLoggedIn;
        }
}
