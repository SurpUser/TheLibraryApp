package com.fcprograms.root.thelibraryapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.fcprograms.root.thelibraryapp.DAL.AdminSQLite;

/**
 * Created by Root on 01-Jun-16.
 */
public class Bibliotecas {
    private int idBiblioteca;
    private int idUsuario;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;
    private static AdminSQLite db;
    private SQLiteDatabase bdsql;
    private ContentValues values;


    public Bibliotecas(int idUsuario, String nombre, String telefono, String direccion, String correo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public Bibliotecas(){
        setIdUsuario(0);
        setNombre("");
        setDireccion("");
        setTelefono("");
        setCorreo("");
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public int getIdUsuario() {return idUsuario;}

    public void setIdUsuario(int idUsuario) {this.idUsuario = idUsuario;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {return correo;}

    public void setCorreo(String correo){this.correo = correo;}

    public Boolean save(Context context){
        try {
            db = new AdminSQLite(context);
            values = new ContentValues();
            values.put("idUsuario", getIdUsuario());
            values.put("nombre", getNombre());
            values.put("telefono",getTelefono());
            values.put("direccion", getDireccion());
            values.put("correo",getCorreo());
            (db.getWritableDatabase()).insert("bibliotecas", null, values);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public Boolean editar(Context context, String id){
        try {
            db = new AdminSQLite(context);
            Cursor cursor;
            bdsql = db.getReadableDatabase();
            cursor = bdsql.rawQuery("update bibliotecas set nombre = '"+getNombre()+"',direccion = '"+getDireccion()+"',"+
                    "telefono = '"+getTelefono()+"',correo = '"+getCorreo()+"' where idUsuario = '"+id+"';", null);
            if (cursor.moveToFirst()) {
                return false;
            } else{
                return true;
            }
        }catch (SQLiteException e){
            return false;
        }finally {
            bdsql.close();
            db.close();
        }
    }

    public Boolean eliminar(Context context, int id){
        try {
            db = new AdminSQLite(context);
            Cursor cursor;
            bdsql = db.getReadableDatabase();
            cursor = bdsql.rawQuery("delete from bibliotecas where idBiblioteca = '"+id+"';", null);
            if (cursor.moveToFirst()) {
                return false;
            } else{
                return true;
            }
        }catch (SQLiteException e){
            return false;
        }finally {
            bdsql.close();
            db.close();
        }
    }

    public Boolean buscar(Context context,String id){
        try {
            db = new AdminSQLite(context);
            Cursor cursor;
            bdsql = db.getReadableDatabase();
            cursor = bdsql.rawQuery("select nombre,direccion,telefono,correo from bibliotecas where idUsuario = '"+id+"';", null);
            if (cursor.moveToFirst()) {
                setNombre(cursor.getString(0));
                setDireccion(cursor.getString(1));
                setTelefono(cursor.getString(2));
                setCorreo(cursor.getString(3));
                return true;
            } else{
                return false;
            }
        }catch (SQLiteException e){
            return false;
        }finally {
            bdsql.close();
            db.close();
        }
    }


}
