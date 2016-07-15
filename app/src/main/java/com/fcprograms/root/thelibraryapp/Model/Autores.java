package com.fcprograms.root.thelibraryapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.fcprograms.root.thelibraryapp.DAL.AdminSQLite;

/**
 * Created by Root on 01-Jun-16.
 */
public class Autores {
    private int idAutor;
    private String nombres;
    private String apellidos;
    private static AdminSQLite db;
    private ContentValues values;

    public Autores(int idAutor, String nombres, String apellidos) {
        this.idAutor = idAutor;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public Boolean save(Context context){
        try {
            db = new AdminSQLite(context);
            values = new ContentValues();
            values.put("nombres", getNombres());
            values.put("apellidos",getApellidos());
            (db.getWritableDatabase()).insert("autores", null, values);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }
}
