package com.example.sembakomobile.Model.Retrofit;

import com.google.gson.annotations.SerializedName;

public class DataKeranjang {

    @SerializedName("idkeranjang")
    private String idKeranjang;
    private String kdBarang;
    private String idPembeli;
    @SerializedName("Jumlah")
    private String Jumlah;
    private String NamaBarang;
    private String gambar;
    private String HargaJual;
    private String Stok;
    private String Satuan;
    private String sub_total;
    private boolean isSelectedCheck;

    public DataKeranjang(String idKeranjang, String kdBarang, String idPembeli,
                         String jumlah, String namaBarang, String gambar, String hargaJual,
                         String stok, String satuan, boolean isSelectedCheck) {
        this.idKeranjang = idKeranjang;
        this.kdBarang = kdBarang;
        this.idPembeli = idPembeli;
        Jumlah = jumlah;
        NamaBarang = namaBarang;
        this.gambar = gambar;
        HargaJual = hargaJual;
        Stok = stok;
        Satuan = satuan;
        this.sub_total = sub_total;
        this.isSelectedCheck = isSelectedCheck;

    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getIdKeranjang() {
        return idKeranjang;
    }

    public void setIdKeranjang(String idKeranjang) {
        this.idKeranjang = idKeranjang;
    }

    public String getKdBarang() {
        return kdBarang;
    }

    public void setKdBarang(String kdBarang) {
        this.kdBarang = kdBarang;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getJumlah() {
        return Jumlah;
    }

    public void setJumlah(String jumlah) {
        Jumlah = jumlah;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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

    public boolean isSelectedCheck() {
        return isSelectedCheck;
    }

    public void setSelectedCheck(boolean selectedCheck) {
        isSelectedCheck = selectedCheck;
    }

}
