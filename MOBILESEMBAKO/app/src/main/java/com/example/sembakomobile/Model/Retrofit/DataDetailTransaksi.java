package com.example.sembakomobile.Model.Retrofit;

import java.util.List;

public class DataDetailTransaksi {
    String idTransaksi, idkeranjang, kdBarang, idPembeli, Jumlah, sub_total, tanggal, NamaBarang, gambar, HargaJual;
    List<DataBarang> list_barang;

    public DataDetailTransaksi(String idTransaksi, String idkeranjang, String kdBarang, String idPembeli, String jumlah, String sub_total, String tanggal, String namaBarang, String gambar, String hargaJual, List<DataBarang> list_barang) {
        this.idTransaksi = idTransaksi;
        this.idkeranjang = idkeranjang;
        this.kdBarang = kdBarang;
        this.idPembeli = idPembeli;
        Jumlah = jumlah;
        this.sub_total = sub_total;
        this.tanggal = tanggal;
        NamaBarang = namaBarang;
        this.gambar = gambar;
        HargaJual = hargaJual;
        this.list_barang = list_barang;
    }

    public List<DataBarang> getList_barang() {
        return list_barang;
    }

    public void setList_barang(List<DataBarang> list_barang) {
        this.list_barang = list_barang;
    }

    public DataDetailTransaksi(String idTransaksi, String idkeranjang, String kdBarang, String idPembeli,
                               String Jumlah, String sub_total, String tanggal, String namaBarang, String gambar, String hargaJual) {
        this.idTransaksi = idTransaksi;
        this.idkeranjang = idkeranjang;
        this.kdBarang = kdBarang;
        this.idPembeli = idPembeli;
        Jumlah = Jumlah;
        this.sub_total = sub_total;
        this.tanggal = tanggal;
        NamaBarang = namaBarang;
        this.gambar = gambar;
        HargaJual = hargaJual;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdkeranjang() {
        return idkeranjang;
    }

    public void setIdkeranjang(String idkeranjang) {
        this.idkeranjang = idkeranjang;
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

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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
}
