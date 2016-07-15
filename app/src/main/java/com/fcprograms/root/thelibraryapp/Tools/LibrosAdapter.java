package com.fcprograms.root.thelibraryapp.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcprograms.root.thelibraryapp.Model.Libros;
import com.fcprograms.root.thelibraryapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Root on 05-Jun-16.
 */
public class LibrosAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Libros> datos;
    private List<Libros> libro;

    public LibrosAdapter(Context context, ArrayList<Libros> datos) {
        super(context, R.layout.row, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.row, null);

        ImageView imagen = (ImageView) item.findViewById(R.id.imageView_Libro);
        imagen.setImageResource(((Libros)datos.get(position)).getIdImagen());

        TextView nombre = (TextView) item.findViewById(R.id.textView_superior);
        nombre.setText(((Libros)datos.get(position)).getTitulo());

        TextView autor = (TextView) item.findViewById(R.id.textView_inferior);
        autor.setText(((Libros)datos.get(position)).getAutor());//String.valueOf(position)

        return item;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        libro.clear();
        if (charText.length() == 0) {
            libro.addAll(datos);

        } else {
            for (Libros l : datos) {
                if (charText.length() != 0 && l.getTitulo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    libro.add(l);
                } else if (charText.length() != 0 && l.getTitulo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    libro.add(l);
                }
            }
        }
        notifyDataSetChanged();
    }




}

