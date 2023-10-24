package com.example.sembakomobile.Model.Retrofit;

import java.util.List;

public class ResponseModelBarang {
    private byte kode;
    private String message;
    private List<DataBarang> data;

    public ResponseModelBarang(byte kode, String message, List<DataBarang> data) {
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

    public List<DataBarang> getData() {
        return data;
    }

    public void setData(List<DataBarang> data) {
        this.data = data;
    }

}
