package com.fcprograms.root.thelibraryapp.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fcprograms.root.thelibraryapp.Model.Libros;
import com.fcprograms.root.thelibraryapp.Model.Prestamos;
import com.fcprograms.root.thelibraryapp.R;

import java.util.Calendar;

import butterknife.ButterKnife;

public class PrestamosActivity extends AppCompatActivity {
    private Prestamos prestamos;
    private Libros libros;
    private Toolbar toolbar;
    private Button botonInicio;
    private Button botonFin;
    public  int ano,mes,dia;
    private EditText titulo;
    private EditText fechaInicio;
    private EditText fechaFin;
    private Bundle bundle;

    private int mhour;
    private int mminute;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    static final int TIME_DIALOG_ID1 = 2;
    static final int DATE_DIALOG_ID1 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);
        init();

        fechaInicio.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               showDialog(DATE_DIALOG_ID);
                                           }
                                       }
        );
       fechaFin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialog(DATE_DIALOG_ID1);
           }
       });

        final Calendar cal = Calendar.getInstance();
        ano = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);
        mhour = cal.get(Calendar.HOUR_OF_DAY);
        mminute = cal.get(Calendar.MINUTE);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void updateDateInicio() {
        fechaInicio.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(dia).append("/")
                        .append(mes + 1).append("/")
                        .append(ano).append(" "));
        showDialog(TIME_DIALOG_ID);

    }


    //-------------------------------------------update time---//
    public void updatetimeInicio() {
        fechaInicio.setText(
                new StringBuilder()
                        .append(dia).append("/")
                        .append(mes + 1).append("/")
                        .append(ano).append(" ")
                        .append(pad(mhour)).append(":")
                        .append(pad(mminute)));
    }

    private void updateDateFin() {
        fechaFin.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(dia).append("/")
                        .append(mes + 1).append("/")
                        .append(ano).append(" "));
        showDialog(TIME_DIALOG_ID1);

    }


    //-------------------------------------------update time---//
    public void updatetimeFin() {
        fechaFin.setText(
                new StringBuilder()
                        .append(dia).append("/")
                        .append(mes + 1).append("/")
                        .append(ano).append(" ")
                        .append(pad(mhour)).append(":")
                        .append(pad(mminute)));
    }

private String pad(int c) {
    if (c >= 10)
        return String.valueOf(c);
    else
        return "0" + String.valueOf(c);
}
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    ano = year;
                    mes = monthOfYear;
                    dia = dayOfMonth;
                    updateDateInicio();

                }
            };

       private  TimePickerDialog.OnTimeSetListener mTimerSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mhour = hourOfDay;
                    mminute = minute;
                    updatetimeInicio();
                }
            };

    private DatePickerDialog.OnDateSetListener mDateSetListener1 =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    ano = year;
                    mes = monthOfYear;
                    dia = dayOfMonth;
                    updateDateFin();

                }
            };

    private  TimePickerDialog.OnTimeSetListener mTimerSetListener1 =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mhour = hourOfDay;
                    mminute = minute;
                    updatetimeFin();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:

                return new DatePickerDialog(this,
                        mDateSetListener,
                        ano, mes, dia);

            case TIME_DIALOG_ID:

                return new TimePickerDialog(this,
                        mTimerSetListener, mhour, mminute, false);

            case DATE_DIALOG_ID1:

                return new DatePickerDialog(this,
                        mDateSetListener1,
                        ano, mes, dia);

            case TIME_DIALOG_ID1:

                return new TimePickerDialog(this,
                        mTimerSetListener1, mhour, mminute, false);

        }
        return null;
    }

    public void init(){
        bundle = getIntent().getExtras();
        fechaInicio = (EditText)findViewById(R.id.FechaInicioPrestamoeditText);
        fechaFin = (EditText)findViewById(R.id.FechaFinPrestamoeditText);
        toolbar = (Toolbar)findViewById(R.id.prestamo_toolbar);
        prestamos = new Prestamos();
        titulo = (EditText)findViewById(R.id.TituloLibroPrestamoeditText);
        libros = new Libros();
        try {
            if (!bundle.getString("ID").isEmpty()){
                llenarActivity();
            }else{
                Toast.makeText(PrestamosActivity.this, "No Encontrado", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(PrestamosActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarActivity(){
        if(libros.buscar(this,bundle.getString("ID"))){
           titulo.setText(libros.getTitulo());
        }else{
            Toast.makeText(PrestamosActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
            guardarDatos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardarDatos(){
        prestamos.setIdBiblioteca(1);
        prestamos.setIdUsuario(1);
        prestamos.setIdLibro(Integer.parseInt(bundle.getString("ID")));
        prestamos.setFechaInicio(fechaInicio.getText().toString());
        prestamos.setFechaFin(fechaFin.getText().toString());
        if (prestamos.save(this)) {
            finish();
            Toast.makeText(this, "Prestamo Guardado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error en el Guardado", Toast.LENGTH_LONG).show();
        }
    }
}
