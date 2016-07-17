package com.fcprograms.root.thelibraryapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Usuarios;
import com.fcprograms.root.thelibraryapp.R;
import com.fcprograms.root.thelibraryapp.Tools.Tools;

public class UserRegister extends AppCompatActivity {
    private Usuarios usuarios;
    private Toolbar toolbar;
    private Tools tools;
    private EditText correo;
    private EditText nombres;
    private EditText apellidos;
    private EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void guardarUsuario(){
        usuarios = new Usuarios();
        usuarios.setNombres(nombres.getText().toString());
        usuarios.setApellidos(apellidos.getText().toString());
        usuarios.setCorreo(correo.getText().toString());
        usuarios.setContrasena(contrasena.getText().toString());
        usuarios.setNombreUsuario("");
        usuarios.setTipoUsuario(1);
        usuarios.setImagen("");
        if (usuarios.save(this)) {
            Toast.makeText(this, "Usuario Guardado", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error al Guardado", Toast.LENGTH_LONG).show();
        }

    }

    public void init(){
        toolbar = (Toolbar)findViewById(R.id.toolbar_usuario);
        tools = new Tools();
        correo = ((EditText)findViewById(R.id.Email_UserRegisterEditText));
        nombres = ((EditText)findViewById(R.id.FistNameEditText));
        apellidos = ((EditText)findViewById(R.id.LastNameEditText));
        contrasena = ((EditText)findViewById(R.id.Password_UserRegisterEditText));
        correo.setText(tools.getEmail(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_libros, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            guardarUsuario();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
