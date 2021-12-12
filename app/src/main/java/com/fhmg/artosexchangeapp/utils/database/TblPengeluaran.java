package com.fhmg.artosexchangeapp.utils.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


@Entity
public class TblPengeluaran {

    @Id(autoincrement = true)
    private Long idTblPengeluaran;

    private String pengeluaran;
    private int nominal;
    private String tanggal;
    @Generated(hash = 1328748510)
    public TblPengeluaran(Long idTblPengeluaran, String pengeluaran, int nominal,
            String tanggal) {
        this.idTblPengeluaran = idTblPengeluaran;
        this.pengeluaran = pengeluaran;
        this.nominal = nominal;
        this.tanggal = tanggal;
    }
    @Generated(hash = 177408923)
    public TblPengeluaran() {
    }
    public Long getIdTblPengeluaran() {
        return this.idTblPengeluaran;
    }
    public void setIdTblPengeluaran(Long idTblPengeluaran) {
        this.idTblPengeluaran = idTblPengeluaran;
    }
    public String getPengeluaran() {
        return this.pengeluaran;
    }
    public void setPengeluaran(String pengeluaran) {
        this.pengeluaran = pengeluaran;
    }
    public int getNominal() {
        return this.nominal;
    }
    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
    public String getTanggal() {
        return this.tanggal;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }



}