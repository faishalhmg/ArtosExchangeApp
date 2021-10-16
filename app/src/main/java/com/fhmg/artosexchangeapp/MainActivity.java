package com.fhmg.artosexchangeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import com.fhmg.artosexchangeapp.pendapatan.PendapatanActivity;
import com.fhmg.artosexchangeapp.pengeluaran.PengeluaranActivity;
import com.fhmg.artosexchangeapp.utils.FunctionHelper;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;

import com.fhmg.artosexchangeapp.utils.database.TblPendapatan;
import com.fhmg.artosexchangeapp.utils.database.TblPengeluaran;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tvTotal3)
    TextView tvTotal3;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageFilterButton4)
    ImageFilterButton imagebutton1;
    @BindView(R.id.imageFilterButton5)
    ImageFilterButton imagebutton2;
    @BindView(R.id.imageFilterButton)
    ImageFilterButton imagebutton;
private DaoSession daoSession;


    SharedPreferences prefs;
    private List<TblPendapatan> tblPendapatanList;
    private List<TblPengeluaran> tblPengeluaranList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);
        tblPendapatanList = daoSession.getTblPendapatanDao().queryBuilder().list();
        tblPengeluaranList = daoSession.getTblPengeluaranDao().queryBuilder().list();
        imageView.setImageResource(R.drawable.dompet);
        prefs = this.getSharedPreferences(
                "pengeluaan_sp",Context.MODE_PRIVATE);
        imagebutton1.setImageResource(R.drawable.pendapatan);
        imagebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,PendapatanActivity.class);
                startActivity(nextActivity);
            }
        });
        imagebutton2.setImageResource(R.drawable.moneyspend);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,PengeluaranActivity.class);
                startActivity(nextActivity);
            }
        });
        imagebutton.setImageResource(R.drawable.moneycurrency);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,MoneyCurrencyConverter.class);
                startActivity(nextActivity);
            }
        });

        tvTotal3.setText(FunctionHelper.convertRupiah(getTotal()));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_pendapatan:
                Intent nextActivity = new Intent(this, PendapatanActivity.class);
                startActivity(nextActivity);
                break;
            case R.id.action_pengeluaran:
                Intent nextActivity2 = new Intent(this, PengeluaranActivity.class);
                startActivity(nextActivity2);
                break;

            case R.id.money_converter:
                Intent nextActivity3 = new Intent(this, MoneyCurrencyConverter.class);
                startActivity(nextActivity3);
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    private int getTotal1(){
        int total = 0;
        for (int i = 0; i < tblPendapatanList.size(); i++){
            int nominal = tblPendapatanList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }
    private int getTotal2(){
        int total = 0;
        for (int i = 0; i < tblPengeluaranList.size(); i++){
            int nominal = tblPengeluaranList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }
    private int getTotal(){
        int total = 0;

        total = getTotal1()-getTotal2();

        return total;
    }
}
