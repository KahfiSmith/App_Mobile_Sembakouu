package com.example.sembakomobile.Model.Retrofit;

import com.google.gson.annotations.SerializedName;

public class DataBarang {
    private String kdBarang;
    private String NamaBarang;
    private String jenis_barang;
    private String deskripsi;
    private String gambar;
    private String HargaBeli;
    private String HargaJual;
    private String Stok;
    private String Satuan;
    private String kdSupplier;
    private String tanggal;
    private String sub_total;
    @SerializedName("Jumlah")
    private String jumlah;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKdSupplier() {
        return kdSupplier;
    }

    public void setKdSupplier(String kdSupplier) {
        this.kdSupplier = kdSupplier;
    }

    public String getKdBarang() {
        return kdBarang;
    }

    public void setKdBarang(String kdBarang) {
        this.kdBarang = kdBarang;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() { return gambar; }

    public void setGambar(String gambar) { this.gambar = gambar;}

    public String getHargaBeli() {
        return HargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        HargaBeli = hargaBeli;
    }

    public String getHargaJual() {
        return HargaJual;
    }

    public void setHargaJual(String hargaJual) {
        HargaJual = hargaJual;
    }

    public String getStok() {
        return Stok;
    }

    public void setStok(String stok) {
        Stok = stok;
    }

    public String getSatuan() {
        return Satuan;
    }

    public void setSatuan(String satuan) {
        Satuan = satuan;
    }
}
