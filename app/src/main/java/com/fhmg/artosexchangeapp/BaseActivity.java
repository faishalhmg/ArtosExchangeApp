package com.fhmg.artosexchangeapp;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity {
    SharedPref sharedpref;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_pendapatan:
                Toast.makeText(this, "Clicked: Menu Pendapatan", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_pengeluaran:
                Toast.makeText(this, "Clicked: Menu Pengeluaran", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.money_converter:
                Toast.makeText(this, "Clicked: Menu Money Converter", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
