package com.fcprograms.root.thelibraryapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.DAL.AdminSQLite;
import com.fcprograms.root.thelibraryapp.Model.Libros;
import com.fcprograms.root.thelibraryapp.R;
import com.fcprograms.root.thelibraryapp.Tools.LibrosAdapter;

import java.util.ArrayList;

public class cLibrosActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    private Cursor cursor;
    private Libros libros;
    private ListView lista;
    private static AdminSQLite db;
    private ArrayList arrayLibros;
    private LibrosAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_libros);

        init();
        rellenarArrayList();
        lista.setOnItemClickListener(this);
        registerForContextMenu(lista);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void rellenarArrayList() {
        db = new AdminSQLite(this);
        SQLiteDatabase bd = db.getWritableDatabase();
        cursor = bd.rawQuery("select idLibro,titulo,autor from libros;", null);
        if (cursor.moveToFirst()) {
            do{
                arrayLibros.add(new Libros(cursor.getInt(0),R.drawable.default_book,cursor.getString(1),cursor.getString(2)));
            } while(cursor.moveToNext());
        } else
            Toast.makeText(this, "No hay Libros Registrados", Toast.LENGTH_SHORT).show();
        adapter = new LibrosAdapter(arrayLibros,this);
        lista.setAdapter(adapter);
        bd.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.LibroslistView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Elija una opción");
            inflater.inflate(R.menu.menu_ctx_lista, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.OpcionEditar:
                Intent i = new Intent(this, EditarLibrosActivity.class);
                i.putExtra("ID", ""+((Libros)arrayLibros.get(info.position)).getIdLibro());
                startActivity(i);
                return true;
            case R.id.OpcionEliminar:
                createSimpleDialog(info.position).show();
                return true;
            case R.id.OpcionPrestamo:
                Intent intent = new Intent(this, PrestamosActivity.class);
                intent.putExtra("ID", ""+((Libros)arrayLibros.get(info.position)).getIdLibro());
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public AlertDialog createSimpleDialog(final int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Eliminar")
                .setMessage("Quieres Eliminar el Libro: "+((Libros)arrayLibros.get(posicion)).getTitulo() +"?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(libros.eliminar(cLibrosActivity.this,((Libros)arrayLibros.get(posicion)).getIdLibro()))
                                    Toast.makeText(cLibrosActivity.this, "Eliminado Correctamente", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(cLibrosActivity.this, "Error al Eliminar", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        return builder.create();
    }

    private void init(){
        arrayLibros = new ArrayList();
        libros = new Libros();
        lista = (ListView)findViewById(R.id.LibroslistView);
        toolbar = (Toolbar)findViewById(R.id.clibros_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                adapter.filter(searchQuery.toString().trim());
                lista.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
