package com.josuepuente.opencvprueba3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.josuepuente.opencvprueba3.loginDAO.Usuario;
import com.josuepuente.opencvprueba3.loginDAO.UsuarioDAO;

public class registro extends AppCompatActivity {
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etContrasena;
    private UsuarioDAO usuarioDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        etNombre = findViewById(R.id.et_nombre);
        etCorreo = findViewById(R.id.et_correo);
        etContrasena = findViewById(R.id.et_contrasena);
        usuarioDAO = new UsuarioDAO(this);

        Button btnRegistrar = findViewById(R.id.btn_registrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String correo = etCorreo.getText().toString();
                String contrasena = etContrasena.getText().toString();

                Usuario usuario = new Usuario(0, nombre, correo, contrasena);
                usuarioDAO.registrarUsuario(usuario);

                Toast.makeText(registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(registro.this, Login.class));
                finish();
            }
        });
    }
}