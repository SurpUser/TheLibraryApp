package com.fcprograms.root.thelibraryapp.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class LibrosAdapter extends BaseAdapter {

    public class ViewHolder {
        ImageView imagen;
        TextView txtTitulo, txtAutor;
    }

    public List<Libros> parkingList;

    public Context context;
    ArrayList<Libros> arraylist;

    public LibrosAdapter(List<Libros> apps, Context context) {
        this.parkingList = apps;
        this.context = context;
        arraylist = new ArrayList<Libros>();
        arraylist.addAll(parkingList);

    }

    @Override
    public int getCount() {
        return parkingList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.row, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.imagen = (ImageView)rowView.findViewById(R.id.imageView_Libro);
            viewHolder.txtTitulo = (TextView)rowView.findViewById(R.id.textView_superior);
            viewHolder.txtAutor = (TextView)rowView.findViewById(R.id.textView_inferior);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imagen.setImageResource(R.drawable.default_book);
        viewHolder.txtTitulo.setText(parkingList.get(position).getTitulo() + "");
        viewHolder.txtAutor.setText(parkingList.get(position).getAutor() + "");
        return rowView;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        parkingList.clear();
        if (charText.length() == 0) {
            parkingList.addAll(arraylist);

        } else {
            for (Libros postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getTitulo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    parkingList.add(postDetail);
                } else if (charText.length() != 0 && postDetail.getAutor().toLowerCase(Locale.getDefault()).contains(charText)) {
                    parkingList.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }
}

