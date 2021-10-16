package com.fhmg.artosexchangeapp.utils.database;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

@Entity
public class TblDompet {
    @Id(autoincrement = true)
    private Long idTblDompet;
    private String pengingat;
    private int nominal;
    private Date tanggal;
    @Generated(hash = 1909146519)
    public TblDompet(Long idTblDompet, String pengingat, int nominal, Date tanggal) {
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
    public Date getTanggal() { return this.tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
}
