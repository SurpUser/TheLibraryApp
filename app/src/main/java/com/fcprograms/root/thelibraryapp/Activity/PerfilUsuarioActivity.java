package com.fcprograms.root.thelibraryapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
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

import java.io.BufferedReader;
import java.io.File;
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
    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;
    private ImageView imageView;
    private String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.botonPerfilFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] options = {"Tomar Foto", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilUsuarioActivity.this);
                builder.setTitle("Elige una Opcion");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int seleccion) {
                        if(options[seleccion] == "Tomar Foto"){
                            openCamera();
                        }else if(options[seleccion] == "Cancelar"){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        init();
        cargarImagen(imageView);
        obtenerDatos();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator
                + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                    String dir =  Environment.getExternalStorageDirectory() + File.separator
                            + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;

            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    Uri path = data.getData();
                    imageView.setImageURI(path);
                }
                break;
        }

    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        rutaImagen = dir;
        imageView.setImageBitmap(bitmap);
    }
    
    private void cargarImagen(ImageView imageView){
        Drawable originalDrawable = getResources().getDrawable(R.drawable.avatar_x);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
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
        imageView = (ImageView) findViewById(R.id.ImagenPerfil);
    }

    private void obtenerDatos(){
        if (usuarios.buscar(this,mostrarArchivo())){
            nombres.setText(usuarios.getNombres());
            apellidos.setText(usuarios.getApellidos());
            nombreUsuario.setText(usuarios.getNombreUsuario());
            correo.setText(usuarios.getCorreo());
            direccion.setText(usuarios.getDireccion());
            telefono.setText(usuarios.getTelefono());
            try {
                if (!usuarios.getImagen().isEmpty()) {
                    Bitmap bitmap;
                    bitmap = BitmapFactory.decodeFile(usuarios.getImagen());
                    imageView.setImageBitmap(bitmap);
                }
            }catch (OutOfMemoryError e){  }
        }
    }

    private void llenarDatos(){
        usuarios.setImagen(rutaImagen);
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
        }catch (Exception e){ }
        return texto;
    }

}
