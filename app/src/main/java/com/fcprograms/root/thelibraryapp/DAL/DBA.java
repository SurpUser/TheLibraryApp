package com.fcprograms.root.thelibraryapp.DAL;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

/**
 * Created by Root on 26-May-16.
 */
public interface DBA {
    static final String SERVER = "http://gearsdeveloperscom.ipage.com/android_app/";
    static final String INSERT_USER = SERVER + "add_user.php";
    static final String LOGIN = SERVER + "login.php";

    public abstract void insert(AppCompatActivity activity, ProgressDialog progressDialog);
    public abstract int update(JSONObject jsonObject);
    public abstract JSONObject search(String string, ProgressDialog progressDialog);
    public abstract boolean verifyUser(AppCompatActivity activity, ProgressDialog progressDialog);
    public abstract int delete(int integer);
}
