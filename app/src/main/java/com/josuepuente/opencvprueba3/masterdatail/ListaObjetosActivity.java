package com.josuepuente.opencvprueba3.masterdatail;
import com.josuepuente.opencvprueba3.R;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaObjetosActivity extends AppCompatActivity {

    private ListView listView;
    private List<objeto> objetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_objetos);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        listView = findViewById(R.id.listViewObjetos);

        // Crear algunos objetos para mostrar en la lista
        objetos = new ArrayList<>();
        objetos.add(new objeto("Maíz dentado", "El grano de este tipo de maíz presenta una muesca en la parte superior, lo que le da un aspecto dentado."));
        objetos.add(new objeto("Maíz harinoso", "Este tipo de maíz tiene un alto contenido de almidón, lo que lo hace ideal para la elaboración de harina."));
        objetos.add(new objeto("Maíz de cristal", "Este tipo de maíz se caracteriza por tener granos transparentes, lo que lo hace ideal para su uso en decoración."));
        objetos.add(new objeto("Maíz dulce", "Este tipo de maíz se consume principalmente en su etapa de madurez, cuando sus granos son tiernos y dulces."));
        objetos.add(new objeto("Maíz reventón", "Este tipo de maíz se utiliza para la elaboración de palomitas, ya que al calentarse los granos estallan y se convierten en palomitas."));


        // Crear un adaptador para la lista de objetos
        ArrayAdapter<objeto> adapter = new ArrayAdapter<objeto>(this, R.layout.objeto_item, objetos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Obtener el objeto actual de la lista
                objeto obj = getItem(position);

                // Verificar si la vista actual está siendo reutilizada, de lo contrario, inflar la vista
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.objeto_item, parent, false);
                }

                // Obtener las vistas del diseño personalizado
                TextView nombreTextView = convertView.findViewById(R.id.nombre_textview);
                TextView descripcionTextView = convertView.findViewById(R.id.descripcion_textview);

                // Mostrar el nombre y la descripción del objeto en las vistas correspondientes
                nombreTextView.setText(obj.getNombre());
                descripcionTextView.setText(obj.getDescripcion());

                // Devolver la vista inflada y actualizada
                return convertView;
            }
        };

// Establecer el adaptador en la lista
        listView.setAdapter(adapter);


        // Agregar un listener para cuando el usuario seleccione un objeto de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objeto objetoSeleccionado = objetos.get(position);
                Intent intent = new Intent(ListaObjetosActivity.this, DetallesObjetoActivity.class);
                intent.putExtra("nombre", objetoSeleccionado.getNombre());
                intent.putExtra("descripcion", objetoSeleccionado.getDescripcion());
                startActivity(intent);
            }
        });
    }

}