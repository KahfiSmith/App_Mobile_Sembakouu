package com.example.sembakomobile.API;

import com.example.sembakomobile.Model.Retrofit.ResponseDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.ResponseKeranjang;
import com.example.sembakomobile.Model.Retrofit.ResponseModel;
import com.example.sembakomobile.Model.Retrofit.ResponseModelBarang;
import com.example.sembakomobile.Model.Retrofit.ResponseTransaksi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceLogin {
    @FormUrlEncoded
    @POST("retrieve.php")
    Call<ResponseModel> loginResponse(
            @Field("Username") String Username,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("cari_barang.php")
    Call<ResponseModelBarang> cariBarangSpesifik(
            @Field("NamaBarang") String NamaBarang
    );

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> registerResponse(
            @Field("NamaPembeli") String NamaPembeli,
            @Field("Alamat") String Alamat,
            @Field("no_hp") String no_hp,
            @Field("Username") String Username,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("update_akun.php")
    Call<ResponseModel> updateResponse(
            @Field("idPembeli") String idPembeli,
            @Field("NamaPembeli") String NamaPembeli,
            @Field("Alamat") String Alamat,
            @Field("no_hp") String no_hp,
            @Field("Username") String Username
    );

    @GET("barang.php")
    Call<ResponseModelBarang> tampilBarang(
    );

    @GET("keranjang.php")
    Call<ResponseKeranjang> tampilKeranjang(
            @Query("idPembeli") String idPembeli
    );

    @FormUrlEncoded
    @POST("insert_keranjang.php")
    Call<ResponseKeranjang> insertKeranjang(
            @Field("idPembeli") String idPembeli,
            @Field("kdBarang") String kdBarang,
            @Field("Jumlah") String Jumlah,
            @Field("sub_total") String sub_total
    );

    @FormUrlEncoded
    @POST("insert_transaksi.php")
    Call<ResponseTransaksi> insertTransaksi(
            @Field("total") String total,
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("update_batalkan.php")
    Call<ResponseModel> update_batalkan(
            @Field("idTransaksi") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("validasi_pw.php")
    Call<ResponseModel> updatePw(
            @Query("idPembeli") String idPembeli,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("tampil_transaksi.php")
    Call<ResponseDetailTransaksi> tampilTrx(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("pending.php")
    Call<ResponseDetailTransaksi> pending(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("dikirim.php")
    Call<ResponseDetailTransaksi> dikirim(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("dikemas.php")
    Call<ResponseDetailTransaksi> dikemas(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("sampai.php")
    Call<ResponseDetailTransaksi> sampai(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("selesai.php")
    Call<ResponseDetailTransaksi> selesai(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("dibatalkan.php")
    Call<ResponseDetailTransaksi> dibatalkan(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("delete_barang.php")
    Call<ResponseKeranjang> deleteBarang(
            @Field("kdBarang") String kdBarang
    );

    @FormUrlEncoded
    @POST("delete_transaksi.php")
    Call<ResponseTransaksi> deleteTransaksi(
            @Field("idTransaksi") String idTransaksi,
            @Field("idPembeli") String idPembeli
    );

    @FormUrlEncoded
        @POST("insert_detailtransaksi.php")
    Call<ResponseTransaksi> insertDetail(
            @Field("idTransaksi") String idTransaksi,
            @Field("idkeranjang") String idkeranjang,
            @Field("kdBarang") String kdBarang,
            @Field("idPembeli") String idPembeli,
            @Field("Jumlah") String Jumlah,
            @Field("sub_total") String sub_total

    );
    @FormUrlEncoded
    @POST("hapus_keranjang.php")
    Call<ResponseTransaksi> hapusKeranjang(
            @Field("idkeranjang") String idkeranjang,
            @Field("idPembeli") String idPembeli
    );

    @FormUrlEncoded
    @POST("update_batalkan.php")
    Call<ResponseDetailTransaksi> update_dibatalkan(
            @Field("idPembeli") String idPembeli,
            @Field("idTransaksi") String idTrasaksi,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("update_selesai.php")
    Call<ResponseDetailTransaksi> update_selesai(
            @Field("idPembeli") String idPembeli,
            @Field("status") String status
    );

    @GET("makanan.php")
    Call<ResponseModelBarang> tampilMakanan();

    @GET("snack.php")
    Call<ResponseModelBarang> tampilSnack();

    @GET("minuman.php")
    Call<ResponseModelBarang> tampilMinuman();

    @GET("bumbu.php")
    Call<ResponseModelBarang> tampilBumbu();

    @GET("sembako.php")
    Call<ResponseModelBarang> tampilSembako();

    @GET("alat_tulis.php")
    Call<ResponseModelBarang> tampilTulis();

    @GET("mandi.php")
    Call<ResponseModelBarang> tampilMandi();

    @GET("mencuci.php")
    Call<ResponseModelBarang> tampilMencuci();

    @GET("rumah.php")
    Call<ResponseModelBarang>tampilRumah();

}
