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
public class Usuarios {
    private int idUsuario;
    private String imagen;
    private String nombres;
    private String apellidos;
    private String nombreUsuario;
    private String contrasena;
    private String correo;
    private int tipoUsuario;
    private String telefono;
    private String direccion;
    private static AdminSQLite db;
    private SQLiteDatabase bdsql;
    private ContentValues values;

    public Usuarios() {
        setIdUsuario(0);
        setNombres("");
        setApellidos("");
        setNombreUsuario("");
        setContrasena("");
        setCorreo("");
        setTipoUsuario(0);
        setTelefono("");
        setDireccion("");
        setImagen("");
    }

    public Boolean save(Context context){
        try {
            db = new AdminSQLite(context);
            values = new ContentValues();
            values.put("nombres", getNombres());
            values.put("apellidos",getApellidos());
            values.put("nombreUsuario", getNombreUsuario());
            values.put("contrasena", getContrasena());
            values.put("correo",getCorreo());
            values.put("tipoUsuario", getTipoUsuario());
            values.put("telefono", getTelefono());
            values.put("direccion", getDireccion());
            values.put("imagen",getImagen());
            (db.getWritableDatabase()).insert("usuarios", null, values);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public Boolean login(Context c){
        Cursor cursor;
        try {
            db = new AdminSQLite(c);
            SQLiteDatabase sqldb = db.getWritableDatabase();
            cursor = sqldb.rawQuery("select idUsuario from usuarios where correo = '"+getCorreo()+"' and contrasena = '"+getContrasena()+"';", null);
            if(cursor.moveToFirst()){
                sqldb.close();
                db.close();
                return true;
            }else{
                sqldb.close();
                db.close();
                return false;
            }

        }catch (SQLiteException e){return false;}
    }

    public Boolean buscar(Context c, String correo){
        Cursor cursor;
        try {
            db = new AdminSQLite(c);
            SQLiteDatabase sqldb = db.getWritableDatabase();
            cursor = sqldb.rawQuery("select nombres,apellidos,correo,nombreUsuario,tipoUsuario,telefono,direccion,imagen,idUsuario from usuarios where correo = '"+correo+"';", null);
            if(cursor.moveToFirst()){
                setNombres(cursor.getString(0));
                setApellidos(cursor.getString(1));
                setCorreo(cursor.getString(2));
                setNombreUsuario(cursor.getString(3));
                setTipoUsuario(cursor.getInt(4));
                setTelefono(cursor.getString(5));
                setDireccion(cursor.getString(6));
                setImagen(cursor.getString(7));
                setIdUsuario(cursor.getInt(8));
                sqldb.close();
                db.close();
                return true;
            }else{
                sqldb.close();
                db.close();
                return false;
            }

        }catch (SQLiteException e){return false;}
    }

    public Boolean editar(Context context, String correo){

        try {
            db = new AdminSQLite(context);
            Cursor cursor;
            bdsql = db.getReadableDatabase();
            cursor = bdsql.rawQuery("update usuarios set imagen = '"+getImagen()+"', nombres = '"+getNombres()+"',apellidos = '"+getApellidos()+
                    "',nombreUsuario = '"+getNombreUsuario()+"',telefono = '"+getTelefono()+"',direccion = '"+getDireccion()
                    +"' where correo = '"+correo+"';", null);
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getImagen(){return this.imagen;}

    public void setImagen(String imagen){this.imagen = imagen;}

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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
}
