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
public class Libros {
    private int idLibro;
    private String autor;
    private int idImagen;
    private String titulo;
    private String edicion;
    private String anoEdicion;
    private String iSBN;
    private String area;
    private String editora;
    private AdminSQLite db;
    private SQLiteDatabase bdsql;
    private ContentValues values;

    public Libros(String titulo, String edicion,String anoEdicion, String iSBN, String area, String editora,String autor) {
        this.titulo = titulo;
        this.edicion = edicion;
        this.anoEdicion = anoEdicion;
        this.iSBN = iSBN;
        this.area = area;
        this.editora = editora;
        this.autor = autor;
    }

    public Libros(int idLibro,int idImagen,String Titulo, String autor){
        setIdImagen(idImagen);
        setTitulo(Titulo);
        setIdLibro(idLibro);
        setAutor(autor);

    }
    public Libros() {
        setIdImagen(0);
        setTitulo("");
        setIdLibro(0);
        setEdicion("");
        setAnoEdicion("");
        setiSBN("");
        setArea("");
        setAutor("");
        setEditora("");
    }




    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdImagen() { return idImagen; }

    public void setIdImagen(int idImagen) { this.idImagen = idImagen; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getAnoEdicion() {
        return anoEdicion;
    }

    public void setAnoEdicion(String anoEdicion) {
        this.anoEdicion= anoEdicion;
    }

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAutor() {return autor;}

    public void setAutor(String autor) {this.autor = autor;}

    public String getEditora() {return editora;}

    public void setEditora(String editora) {this.editora = editora;}

    public Boolean save(Context context){
        try {
            db = new AdminSQLite(context);
            values = new ContentValues();
            values.put("titulo", getTitulo());
            values.put("edicion", getEdicion());
            values.put("iSBN",getiSBN());
            values.put("autor",getAutor());
            values.put("editora",getEditora());
            values.put("anoEdicion",getAnoEdicion());
            values.put("area", getArea());
            (db.getWritableDatabase()).insert("libros", null, values);
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
            cursor = bdsql.rawQuery("update libros set titulo = '"+getTitulo()+"',edicion = '"+getEdicion()+"',"+
                    "iSBN = '"+getiSBN()+"',autor = '"+getAutor()+"',editora = '"+getEditora()+"',"+
                    "anoEdicion = '"+getAnoEdicion()+"', area = '"+getArea()+"' where idLibro = '"+id+"';", null);
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
            cursor = bdsql.rawQuery("delete from libros where idLibro = '"+id+"';", null);
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

    public Boolean buscar(Context context,String id) {
        try {
            db = new AdminSQLite(context);
            Cursor cursor;
            bdsql = db.getReadableDatabase();
            cursor = bdsql.rawQuery("select titulo,editora,autor,edicion,iSBN,area,anoEdicion from libros where idLibro = '" + id + "';", null);
            if (cursor.moveToFirst()) {
                setTitulo(cursor.getString(0));
                setEditora(cursor.getString(1));
                setAutor(cursor.getString(2));
                setEdicion(cursor.getString(3));
                setiSBN(cursor.getString(4));
                setArea(cursor.getString(5));
                setAnoEdicion(cursor.getString(6));
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
            return false;
        } finally {
            bdsql.close();
            db.close();
        }
    }

}
