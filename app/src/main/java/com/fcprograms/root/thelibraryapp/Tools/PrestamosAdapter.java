package com.fcprograms.root.thelibraryapp.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fcprograms.root.thelibraryapp.Model.Libros;
import com.fcprograms.root.thelibraryapp.Model.Prestamos;
import com.fcprograms.root.thelibraryapp.R;

import java.util.ArrayList;

/**
 * Created by Root on 03-Jul-16.
 */
public class PrestamosAdapter extends ArrayAdapter {
    private Libros libros;
    private Context context;
    private ArrayList datos;

    public PrestamosAdapter(Context context,  ArrayList datos) {
        super(context, R.layout.row_prestamo, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.row_prestamo, null);

        TextView titulo = (TextView)item.findViewById(R.id.TituloLibroPrestamotextView);
        titulo.setText(getTituloLibro(position));

        TextView fechaInicio = (TextView) item.findViewById(R.id.FechaIniicoPresmotextView);
        fechaInicio.setText(((Prestamos)datos.get(position)).getFechaInicio());

        TextView fechaFin = (TextView) item.findViewById(R.id.FechaFinPrestamotextView);
        fechaFin.setText(((Prestamos)datos.get(position)).getFechaFin());

        return item;
    }

    private String getTituloLibro(int position){
        libros = new Libros();
        String id = ""+((Prestamos)datos.get(position)).getIdLibro();
        if (libros.buscar(context,id)){
            return libros.getTitulo();
        }else{return null;}
    }
}
