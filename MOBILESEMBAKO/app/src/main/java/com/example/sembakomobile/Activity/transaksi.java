package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Adapter.AdapterBarang;
import com.example.sembakomobile.Adapter.AdapterCheckout;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.Model.Retrofit.DataTransaksi;
import com.example.sembakomobile.Model.Retrofit.ResponseKeranjang;
import com.example.sembakomobile.Model.Retrofit.ResponseModelBarang;
import com.example.sembakomobile.Model.Retrofit.ResponseTransaksi;
import com.example.sembakomobile.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class transaksi extends AppCompatActivity {
    private RecyclerView rv_checkout;
    private RecyclerView.Adapter Checkout;
    private List<DataKeranjang> Checkoutlist = new ArrayList<>();
    private List<DataTransaksi> Transaksi = new ArrayList<>();
    int position;
    TextView alamat, notrx;
    int total;
    int jumlahBarang;
    List<DataKeranjang> listDataKeranjangSengDipilihUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ImageButton kembali;
        Button buatPesanan;
        TextView total_harga;
        Intent intent = getIntent();
        String serializeNameGson = intent.getStringExtra("LIST_BARANG");
        String idTrx = intent.getStringExtra("ID_TRX");

        System.out.println("idtrx "+idTrx);
        Gson gson = new Gson();
        Type typeAccount = new TypeToken<List<DataKeranjang>>(){}.getType();
        listDataKeranjangSengDipilihUser = gson.fromJson(serializeNameGson,typeAccount);
        rv_checkout = findViewById(R.id.rv_cart);
        total_harga = findViewById(R.id.checkout_total_t);
        System.out.println("listbarang"+serializeNameGson);
        for(DataKeranjang dataKeranjang : listDataKeranjangSengDipilihUser){
            total += Integer.parseInt(dataKeranjang.getHargaJual()) * Integer.parseInt(dataKeranjang.getJumlah());
            jumlahBarang += Integer.parseInt(dataKeranjang.getJumlah());
        }
        total_harga.setText(toRupiah(total));
        alamat = findViewById(R.id.text_alamat);
        alamat.setText(SaveAccount.readDataPembeli(transaksi.this).getAlamat());

        notrx = findViewById(R.id.notrx);
        notrx.setText(idTrx);
        System.out.println("nopesan"+notrx);

        kembali = findViewById(R.id.kembalitrx);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
                hapusTRX(idTrx);

            }
        });
        buatPesanan = findViewById(R.id.checkout_btpesan);
        buatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDetailTRX();

            }
        });

        retrieveData();
    }

    private void insertDetailTRX(){
        for (int i = 0; i < listDataKeranjangSengDipilihUser.size(); i++) {
            listDataKeranjangSengDipilihUser.get(i).getNamaBarang();
            System.out.println("namabarang "+listDataKeranjangSengDipilihUser.get(i).getNamaBarang());
            listDataKeranjangSengDipilihUser.get(i).getJumlah();
            System.out.println("jumlah "+listDataKeranjangSengDipilihUser.get(i).getJumlah());
            listDataKeranjangSengDipilihUser.get(i).getSub_total();
            System.out.println("total "+listDataKeranjangSengDipilihUser.get(i).getSub_total());
            ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
            DataItem cr = SaveAccount.readDataPembeli(transaksi.this);
            Intent intent = getIntent();
            String idTrx = intent.getStringExtra("ID_TRX");
            for(int x = 0; x < Integer.parseInt(listDataKeranjangSengDipilihUser.get(i).getJumlah()); x++){
                Call<ResponseTransaksi> insert = service1.insertDetail(idTrx,
                        listDataKeranjangSengDipilihUser.get(i).getIdKeranjang(), listDataKeranjangSengDipilihUser.get(i).getKdBarang(),
                        cr.getIdPembeli(), String.valueOf(1),
                        String.valueOf(listDataKeranjangSengDipilihUser.get(i).getHargaJual()));
                insert.enqueue(new Callback<ResponseTransaksi>() {
                    @Override
                    public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                        System.out.println("Asd = " + new Gson().toJson(response.body()));
                        if(response.body().getKode() == 1) {

                        } else {
                            Toast.makeText(transaksi.this, "Gagal Membuat Pesanan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                        System.out.println("kon"+t.getMessage());
                    }
                });
            }

            Call<ResponseTransaksi> hapusTransaksi = service1.hapusKeranjang(listDataKeranjangSengDipilihUser.get(i).getIdKeranjang(),listDataKeranjangSengDipilihUser.get(i).getIdPembeli());
            hapusTransaksi.enqueue(new Callback<ResponseTransaksi>() {
                @Override
                public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {

                }

                @Override
                public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

                }
            });
        }
        Intent i = new Intent(transaksi.this,NotifDonePesanan.class);
        startActivity(i);
    }

    private void hapusTRX(String idTrx) {
        ServiceLogin service2 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cr = SaveAccount.readDataPembeli(transaksi.this);
        Call<ResponseTransaksi> tampil = service2.deleteTransaksi(idTrx, cr.getIdPembeli());

        System.out.println("idpemb "+cr.getIdPembeli());
        tampil.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                int kode = response.body().getKode();
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });

    }

    public void detail(int position) {
        DataKeranjang db = Checkoutlist.get(position);
    }
    public void retrieveData() {
        Checkout = new AdapterCheckout(transaksi.this, listDataKeranjangSengDipilihUser);
        rv_checkout.setAdapter(Checkout);
        Checkout.notifyDataSetChanged();
    }

    public static String toRupiah(int rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator('.');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(rupiah);
    }
}