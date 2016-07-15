package com.fcprograms.root.thelibraryapp.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Root on 7/7/2015.
 */
public class AdminSQLite extends SQLiteOpenHelper {

    private static final String NOMBRE = "DataBase";
    private static final int VERSION = 1;

    public AdminSQLite(Context context) {
        super(context, NOMBRE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios(" +
                "idUsuario       INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombres         TEXT(40) NOT NULL," +
                "apellidos       TEXT(40) NOT NULL," +
                "nombreUsuario   TEXT(40)," +
                "contrasena      TEXT(32) NOT NULL," +
                "correo          TEXT(100)NOT NULL," +
                "tipoUsuario     INTEGER," +
                "telefono        TEXT(12) NOT NULL," +
                "direccion       TEXT(24))");

        db.execSQL("CREATE TABLE libros(" +
                "idLibro        INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idBiblioteca   INTEGER," +
                "autor          TEXT(40)  NOT NULL," +
                "titulo         TEXT(40) NOT NULL," +
                "anoEdicion     INTEGER NOT NULL," +
                "edicion        TEXT(32) NOT NULL," +
                "editora        TEXT(40) NOT NULL," +
                "iSBN           TEXT(20)NOT NULL," +
                "area           TEXT(20) NOT NULL)");

        db.execSQL("CREATE TABLE bibliotecas(" +
                "idBiblioteca       INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario          INTEGER," +
                "Nombre             TEXT(40) NOT NULL," +
                "direccion          TEXT(200) NOT NULL," +
                "telefono           TEXT(15) NOT NULL," +
                "correo             TEXT(100) NOT NULL)");

        db.execSQL("CREATE TABLE prestamo(" +
                "idPrestamo         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idBiblioteca       INTEGER," +
                "idUsuario          INTEGER," +
                "idLibro            INTEGER," +
                "fechaInicio        TEXT," +
                "activo             INTEGER," +
                "fechaFin           TEXT)");


        db.execSQL("CREATE TABLE configuracion(" +
                "idConfiguracion        INTEGER PRIMARY KEY AUTOINCREMENT," +
                "limiteCantidadLibro    INTEGER," +
                "fechaLimite            TEXT)");

        db.execSQL("CREATE TABLE estante(" +
                "idEstante              INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idBiblioteca           INTEGER," +
                "idEstanteLibro         INTEGER)");

        db.execSQL("CREATE TABLE estanteLibro(" +
                "idEstanteLibro         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idLibro                INTEGER," +
                "codigoEstante          INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuarios");

        db.execSQL("CREATE TABLE usuarios(" +
                "idUsuario       INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombres         TEXT(40) NOT NULL," +
                "apellidos       TEXT(40) NOT NULL," +
                "nombreUsuario   TEXT(40)," +
                "contrasena      TEXT(32) NOT NULL," +
                "correo          TEXT(100)NOT NULL," +
                "tipoUsuario     INTEGER," +
                "telefono        TEXT(12) NOT NULL," +
                "direccion       TEXT(24))");

        db.execSQL("CREATE TABLE libros(" +
                "idLibro        INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idBiblioteca   INTEGER," +
                "autor          TEXT(40)  NOT NULL," +
                "titulo         TEXT(40) NOT NULL," +
                "anoEdicion     INTEGER NOT NULL," +
                "edicion        TEXT(32) NOT NULL," +
                "editora        TEXT(40) NOT NULL," +
                "iSBN           TEXT(20)NOT NULL," +
                "area           TEXT(20) NOT NULL)");

        db.execSQL("CREATE TABLE bibliotecas(" +
                "idBiblioteca       INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario          INTEGER," +
                "Nombre             TEXT(40) NOT NULL," +
                "direccion          TEXT(200) NOT NULL," +
                "telefono           TEXT(15) NOT NULL," +
                "correo             TEXT(100) NOT NULL)");

        db.execSQL("CREATE TABLE prestamo(" +
                "idPrestamo         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idBiblioteca       INTEGER," +
                "idUsuario          INTEGER," +
                "idPrestamoLibros   INTEGER," +
                "cantidad           INTEGER," +
                "fechaInicio        TEXT," +
                "fechaFin           TEXT)");

        db.execSQL("CREATE TABLE prestamoLibros(" +
                "idPrestamoLibros       INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idLibro                INTEGER)");

        db.execSQL("CREATE TABLE configuracion(" +
                "idConfiguracion        INTEGER PRIMARY KEY AUTOINCREMENT," +
                "limiteCantidadLibro    INTEGER," +
                "fechaLimite            TEXT)");

        db.execSQL("CREATE TABLE estante(" +
                "idEstante              INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idBiblioteca           INTEGER," +
                "idEstanteLibro         INTEGER)");

        db.execSQL("CREATE TABLE estanteLibro(" +
                "idEstanteLibro         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idLibro                INTEGER," +
                "codigoEstante          INTEGER)");
    }
}