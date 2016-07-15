package com.fcprograms.root.thelibraryapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Libros;
import com.fcprograms.root.thelibraryapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LibrosActivity extends AppCompatActivity  {

    private Libros libros;
    private Bundle bundle;
    @Bind(R.id.TituloRegistroeditText)
    EditText titulo;
    @Bind(R.id.AreaRegistroeditText)
    EditText area;
    @Bind(R.id.ISBNRegistroeditText)
    EditText isbn;
    @Bind(R.id.AnoEdicioneditText)
    EditText anoEdicion;
    @Bind(R.id.EdicioneditText)
    EditText edicion;
    @Bind(R.id.EditoraeditText)
    EditText editora;
    @Bind(R.id.AutoreditText)
    EditText autor;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);
        ButterKnife.bind(this);

        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void obtenerDatos(){
        libros.setTitulo(titulo.getText().toString());
        libros.setArea(area.getText().toString());
        libros.setiSBN(isbn.getText().toString());
        libros.setAnoEdicion(anoEdicion.getText().toString());
        libros.setEditora(editora.getText().toString());
        libros.setEdicion(edicion.getText().toString());
        libros.setAutor(autor.getText().toString());

        if (libros.save(this)) {
            finish();
            Toast.makeText(this, "Libro Guardado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error al Guardado", Toast.LENGTH_LONG).show();
        }
    }

    public void init(){
        bundle = getIntent().getExtras();
        libros = new Libros();
        toolbar =  (Toolbar)findViewById(R.id.libros_toolbar);
        titulo = ((EditText)findViewById(R.id.TituloRegistroeditText));
        area = ((EditText)findViewById(R.id.AreaRegistroeditText));
        isbn = ((EditText)findViewById(R.id.ISBNRegistroeditText));
        anoEdicion = ((EditText)findViewById(R.id.AnoEdicioneditText));
        editora = ((EditText)findViewById(R.id.EditoraeditText));
        edicion = ((EditText)findViewById(R.id.EdicioneditText));
        autor = ((EditText)findViewById(R.id.AutoreditText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_libros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            obtenerDatos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
