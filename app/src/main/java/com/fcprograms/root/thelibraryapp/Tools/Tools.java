package com.fcprograms.root.thelibraryapp.Tools;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Patterns;

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
