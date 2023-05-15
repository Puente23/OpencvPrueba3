package com.josuepuente.opencvprueba3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.josuepuente.opencvprueba3.loginDAO.Usuario;
import com.josuepuente.opencvprueba3.loginDAO.UsuarioDAO;


public class Login extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etContrasena;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private Button buttonR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.usuario);
        etContrasena = findViewById(R.id.txtPassword);
        usuarioDAO = new UsuarioDAO(this);
        buttonR = findViewById(R.id.button23);
        Button btnIniciarSesion = findViewById(R.id.btnLogin);
        buttonR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, registro.class));
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = etCorreo.getText().toString();
                String contrasena = etContrasena.getText().toString();
                usuario = usuarioDAO.obtenerUsuario(correo, contrasena);

                if (usuario != null) {
                    // Iniciar sesión
                    SharedPreferences prefs = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("id", usuario.getId());
                    editor.putString("nombre", usuario.getNombre());
                    editor.putString("correo", usuario.getCorreo());
                    editor.putString("contrasena", usuario.getContrasena());
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, start.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
