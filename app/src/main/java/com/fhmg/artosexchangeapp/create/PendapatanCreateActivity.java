package com.fhmg.artosexchangeapp.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fhmg.artosexchangeapp.pendapatan.PendapatanActivity;
import com.fhmg.artosexchangeapp.R;
//import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
//import com.fhmg.artosexchangeapp.utils.database.DaoHandler2;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;
import com.fhmg.artosexchangeapp.utils.database.TblPendapatan;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class PendapatanCreateActivity extends AppCompatActivity {
    @BindView(R.id.etPemasukan)
    EditText etPemasukan;
    @BindView(R.id.etNominal2)
    EditText etNominal2;
    @BindView(R.id.btnSimpan2)
    Button btnSimpan2;

    private Unbinder unbinder;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Buat pendapatan");

        unbinder = ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);

        btnSimpan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pemasukan = etPemasukan.getText().toString();
                String nominal2 = etNominal2.getText().toString();

                if (pemasukan.isEmpty() || nominal2.isEmpty()){
                    Toast.makeText(PendapatanCreateActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {

                    TblPendapatan tblPendapatan = new TblPendapatan();
                    tblPendapatan.setPendapatan(pemasukan);
                    tblPendapatan.setNominal(Integer.parseInt(nominal2));
                    daoSession.getTblPendapatanDao().insert(tblPendapatan);

                    Toast.makeText(PendapatanCreateActivity.this, "Berhasil menginput data",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PendapatanCreateActivity.this, PendapatanActivity.class)
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
}
