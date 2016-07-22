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
public class Prestamos {
    private int idPrestamo;
    private int idBiblioteca;
    private int idUsuario;
    private int idLibro;
    private String fechaInicio;
    private String fechaFin;
    private int activo;

    private static AdminSQLite db;
    private SQLiteDatabase bdsql;
    private ContentValues values;

    public Prestamos(int idPrestamo, int idLibro, String fechaInicio, String fechaFin) {
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Prestamos(){
        setIdPrestamo(0);
        setIdUsuario(0);
        setIdBiblioteca(0);
        setIdLibro(0);
        setFechaFin("");
        setFechaInicio("");
        setActivo(0);
    }
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setActivo(int activo){this.activo = activo;}

    public int getActivo(){return this.activo;}

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean save(Context context){
        try {
            db = new AdminSQLite(context);
            values = new ContentValues();
            values.put("idBiblioteca",getIdBiblioteca());
            values.put("idUsuario", getIdUsuario());
            values.put("idLibro",getIdLibro());
            values.put("fechaInicio", getFechaInicio());
            values.put("fechaFin",getFechaFin());
            values.put("activo",getActivo());
            (db.getWritableDatabase()).insert("prestamo", null, values);
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public Boolean update(Context context, int id){
        try {
            db = new AdminSQLite(context);
            Cursor cursor;
            bdsql = db.getReadableDatabase();
            cursor = bdsql.rawQuery("update prestamo set activo = '1' where idPrestamo = '"+id+"';", null);
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

    public Boolean buscar(Context c, int idPrestamo){
        Cursor cursor;
        try {
            db = new AdminSQLite(c);
            SQLiteDatabase sqldb = db.getWritableDatabase();
            cursor = sqldb.rawQuery("select idPrestamo,idBiblioteca,idUsuario,ididLibro,fechaInicio,fechaFin,activo from prestamo where idPrestamo = '"+idPrestamo+"';", null);
            if(cursor.moveToFirst()){
                setIdPrestamo(cursor.getInt(0));
                setIdBiblioteca(cursor.getInt(1));
                setIdUsuario(cursor.getInt(2));
                setIdLibro(cursor.getInt(3));
                setFechaInicio(cursor.getString(4));
                setFechaFin(cursor.getString(5));
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
}
