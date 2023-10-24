package com.example.sembakomobile.Model.Retrofit;

public class InsertDataKeranjang {
    private String idkeranjang;
    private String kdBarang;
    private String idPembeli;
    private String Jumlah;

    public String getIdKeranjang() {
        return idkeranjang;
    }

    public void setIdKeranjang(String idKeranjang) {
        this.idkeranjang = idKeranjang;
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
}
