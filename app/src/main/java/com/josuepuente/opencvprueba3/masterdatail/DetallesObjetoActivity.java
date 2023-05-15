package com.josuepuente.opencvprueba3.masterdatail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.josuepuente.opencvprueba3.R;

public class DetallesObjetoActivity extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView descripcionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_objeto);

        nombreTextView = findViewById(R.id.textview_objeto_nombre);
        descripcionTextView = findViewById(R.id.textview_objeto_descripcion);

        // Obtener los datos del objeto seleccionado de los extras del Intent
        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");

        // Mostrar los detalles del objeto seleccionado
        nombreTextView.setText(nombre);
        descripcionTextView.setText(descripcion);

        Button volverButton = findViewById(R.id.button_volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la anterior
            }
        });
    }

}
