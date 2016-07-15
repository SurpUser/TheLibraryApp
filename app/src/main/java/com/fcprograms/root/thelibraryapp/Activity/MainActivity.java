package com.fcprograms.root.thelibraryapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Usuarios;
import com.fcprograms.root.thelibraryapp.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView nombreUsuario;
    private TextView email;
    private ImageView imagen;
    private Usuarios usuarios;
    private NavigationView navigationView;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initDataUser();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,Biblioteca_Activity.class));
           // finish();
            //setTheme(R.style.AppThemeRedLight);
        }

        return super.onOptionsItemSelected(item);
    }

    public void logOut(){
        createSimpleDialog().show();
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cerrar Sesion")
                .setMessage("Quieres Cerrar Sesion?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(MainActivity.this.deleteFile("the-library-App-data-user.txt")){
                                    finish();
                                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                }else{
                                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_AgregarLibros) {
            startActivity(new Intent(MainActivity.this, LibrosActivity.class));
        } else if (id == R.id.nav_ConsultarLibros) {
            startActivity(new Intent(MainActivity.this, cLibrosActivity.class));
        } else if (id == R.id.nav_perfilUsuario) {
            startActivity(new Intent(MainActivity.this, PerfilUsuarioActivity.class));
        }else if (id == R.id.nav_cerrarsesion){
            logOut();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Check out LibraryApp on Google Play Store https://play.google.com/store/apps/details?id=com.fcprograms.root.thelibraryapp");
            startActivity(Intent.createChooser(intent, "Escoge una Manera de Compartir"));
        } else if (id == R.id.nav_configuracion) {
            startActivity(new Intent(MainActivity.this,Biblioteca_Activity.class));
        }else if(id == R.id.nav_ConsultarPrestamos){
            startActivity(new Intent(MainActivity.this,cPrestamosLibros.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init(){
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        nombreUsuario =  (TextView)navigationView.getHeaderView(0).findViewById(R.id.NombreUsuarioTextView);
        email = (TextView)navigationView.getHeaderView(0).findViewById(R.id.EmailTextView);
        imagen = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.UsuarioImageView);
        usuarios = new Usuarios();
    }

    private void controlUsuario(){
        item = (MenuItem)navigationView.getMenu().findItem(R.id.nav_share);
        item.setVisible(false);
    }

    public void initDataUser(){
        try {
                if(usuarios.buscar(this,mostrarArchivo())){
                    nombreUsuario.setText(usuarios.getNombres());
                    email.setText(usuarios.getCorreo());
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        }
        return texto;
    }
}
