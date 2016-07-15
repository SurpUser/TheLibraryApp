package com.fcprograms.root.thelibraryapp.Activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.DAL.AdminSQLite;
import com.fcprograms.root.thelibraryapp.Model.Prestamos;
import com.fcprograms.root.thelibraryapp.R;
import com.fcprograms.root.thelibraryapp.Tools.PrestamosAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class cPrestamosLibros extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    private Cursor cursor;
    private Prestamos prestamos;
    private ListView lista;
    private static AdminSQLite db;
    private ArrayList arrayPrestamos;
    private PrestamosAdapter adapter;
    private Toolbar toolbar;
    private TextView FechaIniico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_prestamos_libros);

        init();
        rellenarArrayList();
        lista.setOnItemClickListener(this);
        registerForContextMenu(lista);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void finalizarPrestamos(String fecha, int idPrestamo){
           restarFecha(fecha,idPrestamo);
    }

    private void init(){
        arrayPrestamos = new ArrayList();
        prestamos = new Prestamos();
        adapter = new PrestamosAdapter(this, arrayPrestamos);
        lista = (ListView)findViewById(R.id.PrestamoslistView);
        toolbar = (Toolbar)findViewById(R.id.cprestamos_toolbar);
        FechaIniico = (TextView)findViewById(R.id.FechaIniicoPresmotextView);
    }
    public void restarFecha(String fecha,int idPrestamo){
        String fechaInicio = fecha;
        String fechaActual;
        String subFecha;
        String horaInicio;
        String subHora;
        String horaActual;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = df.format(date);
        subFecha = fechaInicio.substring(0,9);
        String[] aFechaIng = subFecha.split("/");
        int diaInicio = Integer.valueOf(aFechaIng[0]);
        int mesInicio = Integer.valueOf(aFechaIng[1]);
        int anioInicio = Integer.valueOf(aFechaIng[2]);

        DateFormat df1 = new SimpleDateFormat("hh:mm:ss");
        horaActual = df1.format(date);
        String[] horaA  = horaActual.split(":");
        int horasActual = Integer.valueOf(horaA[0]);
        int minutosActual = Integer.valueOf(horaA[1]);


        subHora = fechaInicio.substring(10,15);
        String[] horaI  = subHora.split(":");
        int horasInicio = Integer.valueOf(horaI[0]);
        int minutosInicio = Integer.valueOf(horaI[1]);
        int horas = 0;
        int minutos =0;

        String[] aFecha = fechaActual.split("/");
        int diaActual = Integer.valueOf(aFecha[0]);
        int mesActual = Integer.valueOf(aFecha[1]);
        int anioActual = Integer.valueOf(aFecha[2]);

        horas = horasActual - horasInicio;
        if(horas < 0 )
            horas *= -1;

        minutos =  minutosActual - minutosInicio;
        if(minutos < 0 )
            minutos *= -1;

        int b = 0;
        int dias = 0;
        int mes = 0;
        int anios = 0;
        int meses = 0;
        mes = mesInicio -1;

        if (mes == 2) {
            if ((anioActual % 4 == 0) && ((anioActual % 100 != 0) || (anioActual % 400 == 0))) {
                b = 29;
            } else {
                b = 28;
            }
        } else if (mes <= 7) {
            if (mes == 0) {
                b = 31;
            } else if (mes % 2 == 0) {
                b = 30;
            } else {
                b = 31;
            }
        } else if (mes > 7) {
            if (mes % 2 == 0) {
                b = 31;
            } else {
                b = 30;
            }
        }
       /* if ((anioInicio > anioActual) || (anioInicio == anioActual && mesInicio > mesActual)
                || (anioInicio == anioActual && mesInicio == mesActual && diaInicio > diaActual)) {
                  Toast.makeText(cPrestamosLibros.this, "La fecha inicio debe ser anterior a la fecha actual", Toast.LENGTH_SHORT).show();
        } else {*/
            if (mesInicio <= mesActual) {
                anios = anioActual - anioInicio;
                if (diaInicio <= diaActual) {
                    meses = mesActual - mesInicio ;
                    dias = b - (diaInicio - diaActual);
                } else {
                    if (mesActual == mesInicio) {
                        anios = anios - 1;
                    }
                    meses = (mesActual - mesInicio -1  + 12) % 12;
                    dias = b - (diaInicio - diaActual);
                }
            } else {
                anios = anioActual - anioInicio - 1;
                Toast.makeText(cPrestamosLibros.this, anios, Toast.LENGTH_SHORT).show();

                if (diaInicio > diaActual) {
                    meses = mesActual - mesInicio - 1 + 12;
                    dias = b - (diaInicio - diaActual);
                } else {
                    meses = mesActual - mesInicio -1 + 12;
                    dias = diaActual - diaInicio;
                }
            }
        //}
        dias -=30;
        createSimpleDialog(dias+"/"+meses+"/"+anios+ " "+horas+":"+minutos,idPrestamo).show();
}


    public AlertDialog createSimpleDialog(String mensaje, final int idPrestamo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Entregar Libro")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (prestamos.update(cPrestamosLibros.this,idPrestamo)){
                                    Toast.makeText(cPrestamosLibros.this, "Puedes pedir otro libro cuando quieras", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(cPrestamosLibros.this, "Error al Entregar el Libro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        return builder.create();
    }

    private void rellenarArrayList() {
        db = new AdminSQLite(this);
        SQLiteDatabase bd = db.getReadableDatabase();
        cursor = bd.rawQuery("select idPrestamo,idLibro,fechaInicio,fechaFin from prestamo where activo = '0';", null);
        if (cursor.moveToFirst()) {
            do{
                arrayPrestamos.add(new Prestamos(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3)));
            } while(cursor.moveToNext());
        } else
            Toast.makeText(this, "No hay Prestamos Registrados", Toast.LENGTH_SHORT).show();
        lista.setAdapter(adapter);
        bd.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(cPrestamosLibros.this, "The Position is: "+position +" and Id: " + ((Prestamos)arrayPrestamos.get(position)).getIdLibro(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.PrestamoslistView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Elija una opción");//lista.getAdapter().getItem(info.position).toString()
            inflater.inflate(R.menu.menu_prestamos_devoluciones, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.finalizarPrestamo:
                finalizarPrestamos(((Prestamos)arrayPrestamos.get(info.position)).getFechaInicio(),((Prestamos)arrayPrestamos.get(info.position)).getIdPrestamo());
                return true;
            case R.id.cancelar:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
