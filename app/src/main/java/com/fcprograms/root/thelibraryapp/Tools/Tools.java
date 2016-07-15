package com.fcprograms.root.thelibraryapp.Tools;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;

import com.fcprograms.root.thelibraryapp.R;

import java.util.regex.Pattern;

/**
 * Created by Root on 26-May-16.
 */
public class Tools{
    private ProgressDialog progressDialog;

    public void progressDialog(Context c,String mensaje){
        progressDialog = new ProgressDialog(c);
        progressDialog.setMessage(mensaje);
        progressDialog.show();
    }

    public void cerrarProgress(){
        progressDialog.dismiss();
    }

    public boolean existe(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }

    public static void setActionBarContent(AppCompatActivity appCompatActivity){
        Toolbar mToolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);

        try {
            appCompatActivity.setSupportActionBar(mToolbar);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }catch (Exception e){ e.printStackTrace(); }

    }

    public String getEmail(Context context){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return "";
    }
}
