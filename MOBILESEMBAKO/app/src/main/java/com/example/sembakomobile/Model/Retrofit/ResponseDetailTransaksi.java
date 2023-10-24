package com.example.sembakomobile.Model.Retrofit;

import java.util.List;

public class ResponseDetailTransaksi {
    private byte kode;
    private String message;
    private List<DataDetailTransaksi> data;

    public ResponseDetailTransaksi(byte kode, String message, List<DataDetailTransaksi> data){
        this.kode = kode;
        this.message = message;
        this.data = data;
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

    public List<DataDetailTransaksi> getData() {
        return data;
    }

    public void setData(List<DataDetailTransaksi> data) {
        this.data = data;
    }
}
