package com.example.sembakomobile.Model.Retrofit;

import java.util.List;

public class ResponseTransaksi {
    private byte kode;
    private String message;
    private String idTransaksi;
    private List<DataTransaksi> data;

    public ResponseTransaksi(byte kode, String message, List<DataTransaksi> data) {
        this.kode = kode;
        this.message = message;
        this.data = data;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public byte getKode() {
        return kode;
    }

    public void setKode(byte kode) {
        this.kode = kode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataTransaksi> getData() {
        return data;
    }

    public void setData(List<DataTransaksi> data) {
        this.data = data;
    }
}
