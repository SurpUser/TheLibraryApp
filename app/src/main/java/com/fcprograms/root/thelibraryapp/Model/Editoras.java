package com.fcprograms.root.thelibraryapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.fcprograms.root.thelibraryapp.DAL.AdminSQLite;

/**
 * Created by Root on 01-Jun-16.
 */
public class Editoras {
    private int idEditora;
    private String nombre;
    private int ano;
    private static AdminSQLite db;
    private ContentValues values;

    public Editoras(int idEditora, String nombre, int ano) {
        this.idEditora = idEditora;
        this.nombre = nombre;
        this.ano = ano;

    }

    public int getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(int idEditora) {
        this.idEditora = idEditora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Boolean save(Context context){
        try {
            db = new AdminSQLite(context);
            values = new ContentValues();
            values.put("nombre", getNombre());
            values.put("ano",getAno());
            (db.getWritableDatabase()).insert("editoras", null, values);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

}
