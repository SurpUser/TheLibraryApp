package com.fcprograms.root.thelibraryapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fcprograms.root.thelibraryapp.R;
import com.fcprograms.root.thelibraryapp.Tools.Tools;

public class SplashActivity extends AppCompatActivity {
    private String[] archivos;
    private Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tools = new Tools();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    verificarArchivo();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void verificarArchivo(){
        archivos = fileList();
        if (tools.existe(archivos, "the-library-App-data-user.txt")) {
            try {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            } catch (Exception e) {
            }
        }else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }
}
