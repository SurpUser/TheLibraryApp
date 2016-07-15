package com.fcprograms.root.thelibraryapp.Tools;

/**
 * Created by Root on 26-May-16.
 */
import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Request extends AsyncTask<Void, Void, String> {

    //direcciones de los servicios php
    public static final String LOGIN = "";

    private final ProgressDialog progressDialog;
    private final String method;
    private String url;
    public static final String GET = "GET";
    public static final String POST = "POST";
    private String data;

    public Request(String url, String method, ProgressDialog progressDialog) {
        this.url = url;
        this.progressDialog = progressDialog;
        this.method = method;
        init();
    }

    public Request(String url, String method, String data, ProgressDialog progressDialog) {
        this.url = url;
        this.progressDialog = progressDialog;
        this.method = method;
        this.data = data;
        init();
    }

    public Request(String url, String method) {
        this.url = url;
        this.progressDialog = null;
        this.method = method;
    }

    public Request(String url, String method, String data) {
        this.url = url;
        this.progressDialog = null;
        this.method = method;
        this.data = data;
    }

    private void init(){
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        if(progressDialog != null){
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            switch (method) {
                case GET:
                    return GET();
                case POST:
                    return POST();
                default:
                    return "500";
            }
        } catch (Exception e){
            return "500";
        }
    }

    protected abstract void onPostExecute(String result);

    private String GET() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();
        return readStream(connection.getInputStream());
    }

     private String POST() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.getOutputStream().write(getData().getBytes("utf-8"));
        return readStream(connection.getInputStream());
    }

    private String readStream(InputStream is) throws IOException {
        int ch;
        StringBuffer sb = new StringBuffer();
        while ((ch = is.read()) != -1) {
            sb.append((char) ch);
        }
        return sb.toString();
    }

    public String getData() {
        return data;
    }
}