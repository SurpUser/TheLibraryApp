package com.fcprograms.root.thelibraryapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Bibliotecas;
import com.fcprograms.root.thelibraryapp.Model.Usuarios;
import com.fcprograms.root.thelibraryapp.R;
import com.fcprograms.root.thelibraryapp.Tools.Tools;

import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private Usuarios usuario;
    private Bibliotecas biblioteca;
    private Tools tools;
    private TextView correo;
    private TextView contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();

    }


    public void registrarContacto(View view){
        startActivity(new Intent(LoginActivity.this, UserRegister.class));
    }

    public void iniciarSesion(View view){
        try {
            usuario.setCorreo(correo.getText().toString());
            usuario.setContrasena(contrasena.getText().toString());
            tools.progressDialog(LoginActivity.this,"Iniciando Sesion...");
            if (usuario.login(this)){
                grabar();
                if (biblioteca.buscar(this,"1")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(LoginActivity.this,Biblioteca_Activity.class));
                    finish();
                }
            }else{
                Toast.makeText(LoginActivity.this, "Error al Iniciar Sesion", Toast.LENGTH_SHORT).show();
                tools.cerrarProgress();
            }
        }catch (Exception e){
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){
        usuario = new Usuarios();
        biblioteca = new Bibliotecas();
        tools = new Tools();
        correo = (TextView)findViewById(R.id.Email_LoginEditText);
        contrasena = (TextView)findViewById(R.id.PasswordLogineditText);
        correo.setText(tools.getEmail(this));
    }

    public void grabar() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("the-library-App-data-user.txt", Activity.MODE_PRIVATE));
            archivo.write(correo.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
