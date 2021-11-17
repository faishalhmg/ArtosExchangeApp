package com.fhmg.artosexchangeapp.utils.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class TblDompet {

    @Id(autoincrement = true)
    private Long idTblDompet;

    private String pengingat;
    private int nominal;
    private String tanggal;
    @Generated(hash = 144239246)
    public TblDompet(Long idTblDompet, String pengingat, int nominal,
            String tanggal) {
        this.idTblDompet = idTblDompet;
        this.pengingat = pengingat;
        this.nominal = nominal;
        this.tanggal = tanggal;
    }
    @Generated(hash = 2030096796)
    public TblDompet() {
    }
    public Long getIdTblDompet() {
        return this.idTblDompet;
    }
    public void setIdTblDompet(Long idTblDompet) {
        this.idTblDompet = idTblDompet;
    }
    public String getPengingat() {
        return this.pengingat;
    }
    public void setPengingat(String pengingat) {
        this.pengingat = pengingat;
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
