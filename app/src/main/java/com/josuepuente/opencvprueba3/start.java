package com.josuepuente.opencvprueba3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.josuepuente.opencvprueba3.loginDAO.Usuario;
import com.josuepuente.opencvprueba3.masterdatail.ListaObjetosActivity;
import com.josuepuente.opencvprueba3.vistacontrolador.MainActivity;
import com.josuepuente.opencvprueba3.vistacontrolador.MainActivity2;

public class start extends AppCompatActivity{
    Button button;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //datos dao
        SharedPreferences prefs = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int id = prefs.getInt("id", -1);
        String nombre = prefs.getString("nombre", "");
        String correo = prefs.getString("correo", "");
        String contrasena = prefs.getString("contrasena", "");
        usuario = new Usuario(id, nombre, correo, contrasena);


       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        TextView myTextView = findViewById(R.id.myTextView);
        myTextView.setText(getString(R.string.texto));
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                SharedPreferences prefs = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.clear();
                editor.apply();

                Toast.makeText(start.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(start.this, Login.class));

                    // Código adicional para realizar cualquier otra acción necesaria al cerrar sesión
                finish();
            }
        });

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent;
                    switch (item.getItemId()) {
                        case R.id.menu_item_2:
                            // Acción para el botón "Inicio"
                            intent = new Intent(start.this, MainActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.menu_item_1:
                            // Acción para el botón "Objetos"
                            intent = new Intent(start.this, ListaObjetosActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.menu_item_3:
                            // Acción para el botón "Conteo de granos"
                            intent = new Intent(start.this, MainActivity2.class);
                            startActivity(intent);
                            return true;
                    }
                    return false;
                }
            };

}