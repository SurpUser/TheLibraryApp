package com.fcprograms.root.thelibraryapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Bibliotecas;
import com.fcprograms.root.thelibraryapp.Model.Usuarios;
import com.fcprograms.root.thelibraryapp.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Biblioteca_Activity extends AppCompatActivity {
    private EditText nombre;
    private EditText telefono;
    private EditText direccion;
    private EditText correo;
    private Bibliotecas bibliotecas;
    private Usuarios usuarios;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilioteca);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buscarBiblioteca();
    }

    public void init(){
        toolbar =  (Toolbar)findViewById(R.id.biblioteca_toolbar);
        bibliotecas = new Bibliotecas();
        nombre = (EditText)findViewById(R.id.NombreBibliotecaeditText);
        direccion = (EditText)findViewById(R.id.DireccionBibliotecaeditText);
        telefono = (EditText)findViewById(R.id.TelefonoBibliotecaeditText);
        correo = (EditText)findViewById(R.id.CorreoBibliotecaeditText);
        usuarios = new Usuarios();
    }

    private void llenarDatos(){
        if (usuarios.buscar(this,mostrarArchivo())){
            bibliotecas.setIdUsuario(usuarios.getIdUsuario());
        }
        bibliotecas.setNombre(nombre.getText().toString());
        bibliotecas.setTelefono(telefono.getText().toString());
        bibliotecas.setCorreo(correo.getText().toString());
        bibliotecas.setDireccion(direccion.getText().toString());
    }

    public void guardarBiblioteca(){
        llenarDatos();
        if (bibliotecas.save(this)) {
            Toast.makeText(this, "Guardo Correctamente" , Toast.LENGTH_SHORT).show();
        }else{
           Toast.makeText(this, "Error al Guardar", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarBiblioteca(){
        llenarDatos();
        if (bibliotecas.editar(this,usuarios.getIdUsuario()+"")){
            Toast.makeText(this, "Actualizado Correctamente" , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error al Actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarBiblioteca(){
        if (usuarios.buscar(this,mostrarArchivo())) {
            if (bibliotecas.buscar(this,usuarios.getIdUsuario()+"" )) {
                nombre.setText(bibliotecas.getNombre());
                telefono.setText(bibliotecas.getTelefono());
                correo.setText(bibliotecas.getCorreo());
                direccion.setText(bibliotecas.getDireccion());
            } else {
                Toast.makeText(this, "Registre una", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String mostrarArchivo(){
        String texto = null;
        try {
            InputStreamReader archivo = new InputStreamReader( openFileInput("the-library-App-data-user.txt"));
            BufferedReader br = new BufferedReader(archivo);

            texto = br.readLine();
            br.close();
            return texto;
        }catch (Exception e){
            //return texto +" catch = "+e.getMessage();
        }
        return texto;
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
            if (usuarios.buscar(this,mostrarArchivo())) {
                if (bibliotecas.buscar(this,usuarios.getIdUsuario()+"")) {
                    actualizarBiblioteca();
                    startActivity(new Intent(Biblioteca_Activity.this, MainActivity.class));
                    finish();
                } else {
                    guardarBiblioteca();
                    startActivity(new Intent(Biblioteca_Activity.this, MainActivity.class));
                    finish();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
