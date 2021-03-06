package com.fhmg.artosexchangeapp.pendapatan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.fhmg.artosexchangeapp.create.PendapatanCreateActivity;
import com.fhmg.artosexchangeapp.edit.EditDialogFragment2;
import com.fhmg.artosexchangeapp.utils.FunctionHelper;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;



import com.fhmg.artosexchangeapp.utils.database.TblPendapatan;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PendapatanActivity extends AppCompatActivity
        implements PendapatanAdapter.PendapatanAdapterCallback,
        EditDialogFragment2.EditDialogListener {


    @BindView(R.id.rvNote2)
    RecyclerView rvNote2;
    @BindView(R.id.fabAdd2)
    FloatingActionButton fabAdd2;
    @BindView(R.id.tvTotal2)
    TextView tvTotal2;


    private PendapatanAdapter pendapatanAdapter;
    private DaoSession daoSession;
    private List<TblPendapatan> tblPendapatanList;

    SharedPref sharedpref;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendapatan_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);
        prefs = this.getSharedPreferences(
                "pendapatan_sp", Context.MODE_PRIVATE);


        tblPendapatanList = daoSession.getTblPendapatanDao().queryBuilder().list();
        pendapatanAdapter = new PendapatanAdapter(tblPendapatanList, this);
        rvNote2.setLayoutManager(new LinearLayoutManager(this));
        rvNote2.setItemAnimator(new DefaultItemAnimator());
        rvNote2.setAdapter(pendapatanAdapter);
        pendapatanAdapter.notifyDataSetChanged();
        tvTotal2.setText(FunctionHelper.convertRupiah(getTotal()));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("pendapatan", Integer.valueOf(getTotal()));
        editor.commit();

        fabAdd2.setOnClickListener(view -> startActivity(new Intent(PendapatanActivity.this, PendapatanCreateActivity.class)));
    }


    @Override
    public void onLongClick(int position) {
        long id = tblPendapatanList.get(position).getIdTblPendapatan();
        String pemasukan = tblPendapatanList.get(position).getPendapatan();
        int nominal = tblPendapatanList.get(position).getNominal();

        FragmentManager fm = getSupportFragmentManager();
        EditDialogFragment2 editDialogFragment2 = EditDialogFragment2.newInstance(id, pemasukan, nominal);
        editDialogFragment2.show(fm, "dialog_edit");
    }


    @Override
    public void onDelete(int position) {
        String name = tblPendapatanList.get(position).getPendapatan();
        showDialogDelete(position, name);
    }


    private int getTotal(){
        int total = 0;
        for (int i = 0; i < tblPendapatanList.size(); i++){
            int nominal = tblPendapatanList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }


    private void showDialogDelete(final int position, String name){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PendapatanActivity.this);
        builder1.setMessage("Yakin untuk menghapus item "+ name + " ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*
                        Fungsi delete suatu data bedasarkan idnya.
                         */
                        long idTbl = tblPendapatanList.get(position).getIdTblPendapatan();
                        daoSession.getTblPendapatanDao().deleteByKey(idTbl);

                        tblPendapatanList.remove(position);
                        pendapatanAdapter.notifyItemRemoved(position);
                        pendapatanAdapter.notifyItemRangeChanged(position, tblPendapatanList.size());

                        tvTotal2.setText(FunctionHelper.convertRupiah(getTotal()));

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
    public void requestUpdate(long id, String pemasukan, int nominal) {
        TblPendapatan TblPendapatan = daoSession.getTblPendapatanDao().load(id);
        TblPendapatan.setPendapatan(pemasukan);
        TblPendapatan.setNominal(nominal);
        daoSession.getTblPendapatanDao().update(TblPendapatan);

        pendapatanAdapter.notifyDataSetChanged();
        tvTotal2.setText(FunctionHelper.convertRupiah(getTotal()));
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
