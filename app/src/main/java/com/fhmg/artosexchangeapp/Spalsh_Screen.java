
/**
 * Bank Papua
 * https://www.instagram.com/adityatprtma/
 * Created on 03-maret-2021.
 * Created by : Adityatprtma
 */
package com.fhmg.artosexchangeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Spalsh_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh__screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Thread thread = new Thread() {
            public void run () {
                try {
                    sleep(6000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(Spalsh_Screen.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}