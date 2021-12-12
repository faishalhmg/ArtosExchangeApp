package com.fhmg.artosexchangeapp.utils.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
    public class TblPendapatan {

        @Id(autoincrement = true)
        private Long idTblPendapatan;
        
        private String pendapatan;
        private int nominal;
    private String tanggal;
    @Generated(hash = 1867387723)
    public TblPendapatan(Long idTblPendapatan, String pendapatan, int nominal,
            String tanggal) {
        this.idTblPendapatan = idTblPendapatan;
        this.pendapatan = pendapatan;
        this.nominal = nominal;
        this.tanggal = tanggal;
    }
    @Generated(hash = 2114593808)
    public TblPendapatan() {
    }
    public Long getIdTblPendapatan() {
        return this.idTblPendapatan;
    }
    public void setIdTblPendapatan(Long idTblPendapatan) {
        this.idTblPendapatan = idTblPendapatan;
    }
    public String getPendapatan() {
        return this.pendapatan;
    }
    public void setPendapatan(String pendapatan) {
        this.pendapatan = pendapatan;
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

