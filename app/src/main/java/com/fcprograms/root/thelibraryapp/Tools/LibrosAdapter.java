package com.fcprograms.root.thelibraryapp.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcprograms.root.thelibraryapp.Model.Libros;
import com.fcprograms.root.thelibraryapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Root on 05-Jun-16.
 */
public class LibrosAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Libros> datos;
    private Filtro filtro;

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

    public ArrayList<Libros> getLibro(){return datos;}
    public void setLibro(ArrayList<Libros> dato){this.datos = dato;}

    @Override
    public Filter getFilter() {

        if(filtro == null){
            filtro = new Filtro();
        }
        return filtro;
    }

    private class Filtro extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0){
                results.values = getLibro();
                results.count = getLibro().size();
            }else {
                List<Libros> libroList = new ArrayList<>();

                for(Libros l: getLibro()){
                    if(l.getTitulo().toUpperCase().startsWith(libroList.toString().toUpperCase()))
                        libroList.add(l);
                    results.values = libroList;
                    results.count = libroList.size();
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.count == 0){
                notifyDataSetInvalidated();
            }else {
                setLibro((ArrayList<Libros>)results.values);
                notifyDataSetChanged();

            }
        }
    }
}
