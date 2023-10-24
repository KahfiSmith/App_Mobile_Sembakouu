package com.example.sembakomobile.Model.Retrofit;

import java.util.Date;

public class DataTransaksi {
    private String idTransaksi;
    private String idPembeli;
    private String total;
    private String tanggal;
    private String status;

    public DataTransaksi(String idTransaksi, String idPembeli, String total, String tanggal) {
        this.idTransaksi = idTransaksi;
        this.idPembeli = idPembeli;
        this.total = total;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
