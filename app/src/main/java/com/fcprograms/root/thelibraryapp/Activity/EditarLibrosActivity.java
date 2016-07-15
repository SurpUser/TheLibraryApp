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

public class EditarLibrosActivity extends AppCompatActivity {
    private Libros libros;
    private Bundle bundle;
    private EditText titulo;
    private EditText area;
    private EditText isbn;
    private EditText anoEdicion;
    private EditText edicion;
    private EditText editora;
    private EditText autor;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libros);

        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void editarLibro(){
        try {
            libros.setTitulo(titulo.getText().toString());
            libros.setArea(area.getText().toString());
            libros.setiSBN(isbn.getText().toString());
            libros.setAnoEdicion(anoEdicion.getText().toString());
            libros.setEditora(editora.getText().toString());
            libros.setEdicion(edicion.getText().toString());
            libros.setAutor(autor.getText().toString());

            if (libros.editar(this,bundle.getString("ID"))){
                Toast.makeText(EditarLibrosActivity.this, "Editado Corectamente", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(EditarLibrosActivity.this, "Error al Editar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){}
    }

    public void init(){
        bundle = getIntent().getExtras();
        libros = new Libros();
        toolbar =  (Toolbar)findViewById(R.id.librosEditar_toolbar);
        titulo = ((EditText)findViewById(R.id.TituloRegistroeditText));
        area = ((EditText)findViewById(R.id.AreaRegistroeditText));
        isbn = ((EditText)findViewById(R.id.ISBNRegistroeditText));
        anoEdicion = ((EditText)findViewById(R.id.AnoEdicioneditText));
        editora = ((EditText)findViewById(R.id.EditoraeditText));
        edicion = ((EditText)findViewById(R.id.EdicioneditText));
        autor = ((EditText)findViewById(R.id.AutoreditText));
        try {
            if (!bundle.getString("ID").isEmpty()){
                llenarActivity();
            }
        }catch (Exception e){
            Toast.makeText(EditarLibrosActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarActivity(){
        if(libros.buscar(this,bundle.getString("ID"))){
            titulo.setText(libros.getTitulo());
            area.setText(libros.getArea());
            isbn.setText(libros.getiSBN());
            anoEdicion.setText(libros.getAnoEdicion());
            edicion.setText(libros.getEdicion());
            editora.setText(libros.getEditora());
            autor.setText(libros.getAutor());
        }else{
            Toast.makeText(EditarLibrosActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
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
            editarLibro();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
