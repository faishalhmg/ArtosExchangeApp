package com.fhmg.artosexchangeapp.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fhmg.artosexchangeapp.DatePickerFragment;
import com.fhmg.artosexchangeapp.MainActivity;
import com.fhmg.artosexchangeapp.R;
import com.fhmg.artosexchangeapp.pengeluaran.PengeluaranActivity;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;
import com.fhmg.artosexchangeapp.utils.database.TblDompet;
import com.fhmg.artosexchangeapp.utils.database.TblPengeluaran;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DompetActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener{
    private final static String DATE_PICKER_TAG = "DatePicker";
    @BindView(R.id.etPengigat)
    EditText etPengigat;
    @BindView(R.id.etNominal3)
    EditText etNominal3;
    @BindView(R.id.btnSimpan3)
    Button btnSimpan3;
    @BindView(R.id.btn_once_date)
    ImageButton btn_once_date;
    @BindView(R.id.tv_once_date)
    TextView tv_once_date;

    private Unbinder unbinder;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create3);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Buat pengingat");

        unbinder = ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);
        btn_once_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
            }
        });

        btnSimpan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pembelian = etPengigat.getText().toString();
                String nominal = etNominal3.getText().toString();
                String date =  tv_once_date.getText().toString();

                if (pembelian.isEmpty() || nominal.isEmpty()){
                    Toast.makeText(DompetActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {

                    TblDompet tblDompet = new TblDompet();
                    tblDompet.setPengingat(pembelian);
                    tblDompet.setNominal(Integer.parseInt(nominal));
                    tblDompet.setTanggal(date);
                    daoSession.getTblDompetDao().insert(tblDompet);

                    Toast.makeText(DompetActivity.this, "Berhasil menginput data",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DompetActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    restartApp();
                    finish();
                }
            }
        });
    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        // Siapkan date formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // Set text dari textview once
        tv_once_date.setText(dateFormat.format(calendar.getTime()));
    }
}