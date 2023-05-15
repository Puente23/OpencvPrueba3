package com.josuepuente.opencvprueba3.masterdatail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.josuepuente.opencvprueba3.R;
import com.josuepuente.opencvprueba3.masterdatail.objeto;

import java.util.List;

public class objetosnombre extends ArrayAdapter<objeto> {

    private List<objeto> objetos;
    private Context context;

    public objetosnombre(Context context, List<objeto> objetos) {
        super(context, 0, objetos);
        this.context = context;
        this.objetos = objetos;
    }

    public objetosnombre(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.objeto_item, parent, false);
        }

        objeto objeto = objetos.get(position);

        TextView nombreTextView = listItem.findViewById(R.id.textview_objeto_nombre);
        nombreTextView.setText(objeto.getNombre());

        return listItem;
    }
}

