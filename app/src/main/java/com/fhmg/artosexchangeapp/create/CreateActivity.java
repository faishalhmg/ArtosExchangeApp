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
import com.fhmg.artosexchangeapp.pengeluaran.PengeluaranActivity;
import com.fhmg.artosexchangeapp.R;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;
import com.fhmg.artosexchangeapp.utils.database.TblPengeluaran;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class CreateActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener {
    private final static String DATE_PICKER_TAG = "DatePicker";
    @BindView(R.id.etPembelian)
    EditText etPembelian;
    @BindView(R.id.etNominal)
    EditText etNominal;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btn_once_date2)
    ImageButton btn_once_date2;
    @BindView(R.id.tvDate2)
    TextView tvDate2;
    private Unbinder unbinder;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Buat pengeluaran");

        unbinder = ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);
        btn_once_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pembelian = etPembelian.getText().toString();
                String nominal = etNominal.getText().toString();
                String date =  tvDate2.getText().toString();
                if (pembelian.isEmpty() || nominal.isEmpty()){
                    Toast.makeText(CreateActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {

                    TblPengeluaran tblPengeluaran = new TblPengeluaran();
                    tblPengeluaran.setPengeluaran(pembelian);
                    tblPengeluaran.setNominal(Integer.parseInt(nominal));
                    tblPengeluaran.setTanggal(date);
                    daoSession.getTblPengeluaranDao().insert(tblPengeluaran);

                    Toast.makeText(CreateActivity.this, "Berhasil menginput data",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateActivity.this, PengeluaranActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }
        });
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
        tvDate2.setText(dateFormat.format(calendar.getTime()));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
