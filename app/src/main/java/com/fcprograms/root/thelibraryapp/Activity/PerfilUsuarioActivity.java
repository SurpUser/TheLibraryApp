package com.fcprograms.root.thelibraryapp.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Usuarios;
import com.fcprograms.root.thelibraryapp.R;
import com.fcprograms.root.thelibraryapp.Tools.Tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PerfilUsuarioActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText nombres;
    private EditText apellidos;
    private EditText correo;
    private EditText nombreUsuario;
    private EditText direccion;
    private EditText telefono;
    private Usuarios usuarios;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.botonPerfilFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();
        cargarImagen();
        obtenerDatos();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void cargarImagen(){
        Drawable originalDrawable = getResources().getDrawable(R.drawable.avatar_x);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) findViewById(R.id.ImagenPerfil);
        imageView.setImageDrawable(roundedDrawable);
    }

    private void init(){
        usuarios = new Usuarios();
        toolbar =  (Toolbar)findViewById(R.id.appbar);
        nombres = (EditText)findViewById(R.id.NombrePerfilEditText);
        apellidos = (EditText)findViewById(R.id.ApellidoPerfilEditText);
        correo = (EditText)findViewById(R.id.EmailPerfilEditText);
        nombreUsuario = (EditText)findViewById(R.id.NombreUsuarioPerfilEditText);
        direccion = (EditText)findViewById(R.id.DireccionPerfilEditText);
        telefono = (EditText)findViewById(R.id.TelefonoPerfilEditText);
        tools = new Tools();
    }

    private void obtenerDatos(){
        if (usuarios.buscar(this,mostrarArchivo())){
            nombres.setText(usuarios.getNombres());
            apellidos.setText(usuarios.getApellidos());
            nombreUsuario.setText(usuarios.getNombreUsuario());
            correo.setText(usuarios.getCorreo());
            direccion.setText(usuarios.getDireccion());
            telefono.setText(usuarios.getTelefono());
        }
    }

    private void llenarDatos(){
        usuarios.setNombres(nombres.getText().toString());
        usuarios.setApellidos(apellidos.getText().toString());
        usuarios.setNombreUsuario(nombreUsuario.getText().toString());
        usuarios.setDireccion(direccion.getText().toString());
        usuarios.setTelefono(telefono.getText().toString());
    }
    private void editarDatos(){
        llenarDatos();
        if (usuarios.editar(this,mostrarArchivo())){
            Toast.makeText(PerfilUsuarioActivity.this, "Actualizado Correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(PerfilUsuarioActivity.this, "Error al Actualizar", Toast.LENGTH_SHORT).show();
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
            editarDatos();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        return texto +" fuera del try ";// "francisjcv@hotmail.com";
    }

}
