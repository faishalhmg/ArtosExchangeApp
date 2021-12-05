package com.fhmg.artosexchangeapp.pengeluaran;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fhmg.artosexchangeapp.MainActivity;
import com.fhmg.artosexchangeapp.R;
import com.fhmg.artosexchangeapp.SharedPref;
import com.fhmg.artosexchangeapp.create.CreateActivity;
import com.fhmg.artosexchangeapp.edit.EditDialogFragment;
import com.fhmg.artosexchangeapp.utils.FunctionHelper;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;
import com.fhmg.artosexchangeapp.utils.database.TblPengeluaran;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PengeluaranActivity extends AppCompatActivity
        implements PengeluaranAdapter.PengeluaranAdapterCallback,
        EditDialogFragment.EditDialogListener {
    SharedPref sharedpref;

    @BindView(R.id.rvNote) RecyclerView rvNote;
    @BindView(R.id.fabAdd) FloatingActionButton fabAdd;
    @BindView(R.id.tvTotal) TextView tvTotal;


    private PengeluaranAdapter pengeluaranAdapter;
    private DaoSession daoSession;
    private List<TblPengeluaran> tblPengeluaranList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengeluaran_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);



        tblPengeluaranList = daoSession.getTblPengeluaranDao().queryBuilder().list();
        pengeluaranAdapter = new PengeluaranAdapter(tblPengeluaranList, this);
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setItemAnimator(new DefaultItemAnimator());
        rvNote.setAdapter(pengeluaranAdapter);
        pengeluaranAdapter.notifyDataSetChanged();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvTotal.setText(FunctionHelper.convertRupiah(getTotal()));

        fabAdd.setOnClickListener(view -> startActivity(new Intent(PengeluaranActivity.this, CreateActivity.class)));
    }


    @Override
    public void onLongClick(int position) {
        long id = tblPengeluaranList.get(position).getIdTblPengeluaran();
        String pembelian = tblPengeluaranList.get(position).getPengeluaran();
        int nominal = tblPengeluaranList.get(position).getNominal();

        FragmentManager fm = getSupportFragmentManager();
        EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(id, pembelian, nominal);
        editDialogFragment.show(fm, "dialog_edit");
    }


    @Override
    public void onDelete(int position) {
        String name = tblPengeluaranList.get(position).getPengeluaran();
        showDialogDelete(position, name);
    }


    private int getTotal(){
        int total = 0;
        for (int i = 0; i < tblPengeluaranList.size(); i++){
            int nominal = tblPengeluaranList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }


    private void showDialogDelete(final int position, String name){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PengeluaranActivity.this);
        builder1.setMessage("Yakin untuk menghapus item "+ name + " ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*
                        Fungsi delete suatu data bedasarkan idnya.
                         */
                        long idTbl = tblPengeluaranList.get(position).getIdTblPengeluaran();
                        daoSession.getTblPengeluaranDao().deleteByKey(idTbl);

                        tblPengeluaranList.remove(position);
                        pengeluaranAdapter.notifyItemRemoved(position);
                        pengeluaranAdapter.notifyItemRangeChanged(position, tblPengeluaranList.size());

                        tvTotal.setText(FunctionHelper.convertRupiah(getTotal()));

                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    public void requestUpdate(long id, String pembelian, int nominal) {
        TblPengeluaran tblPengeluaran = daoSession.getTblPengeluaranDao().load(id);
        tblPengeluaran.setPengeluaran(pembelian);
        tblPengeluaran.setNominal(nominal);
        daoSession.getTblPengeluaranDao().update(tblPengeluaran);

        pengeluaranAdapter.notifyDataSetChanged();
        tvTotal.setText(FunctionHelper.convertRupiah(getTotal()));
    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        restartApp();

        return true;
    }
}
