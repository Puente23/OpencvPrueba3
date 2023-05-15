package com.josuepuente.opencvprueba3.loginDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
    private final SQLiteDatabase db;

    public UsuarioDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void registrarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nombre", usuario.getNombre());
        values.put("correo", usuario.getCorreo());
        values.put("contrasena", usuario.getContrasena());
        db.insert("usuarios", null, values);
    }

    public Usuario obtenerUsuario(String correo, String contrasena) {
        String[] columns = {"id", "nombre", "correo", "contrasena"};
        String selection = "correo = ? AND contrasena = ?";
        String[] selectionArgs = {correo, contrasena};
        Cursor cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);

        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            usuario = new Usuario(id, nombre, correo, contrasena);
        }

        cursor.close();
        return usuario;
    }
}

