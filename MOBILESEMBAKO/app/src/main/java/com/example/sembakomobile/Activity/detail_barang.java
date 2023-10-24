package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Fragment.Home;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.Model.Retrofit.InsertDataKeranjang;
import com.example.sembakomobile.Model.Retrofit.ResponseKeranjang;
import com.example.sembakomobile.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_barang extends AppCompatActivity {
    private List<InsertDataKeranjang> insertDataKeranjangs = new ArrayList<>();
    private List<DataKeranjang> dataBarangList = new ArrayList<>();
    ImageButton bt_back;
    ImageView img;
    TextView harga, nama, deskripsi2, jenis2, stok, kurang, tambah, jumlah, total1, satuan;
    Button btn_keranjang, btn_beli;
    int count = 1;
    int total = 0;

    DataBarang dataBarang;
    int hargaTotal = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        Intent intent  = getIntent();
        String serializeNameGson = intent.getStringExtra("KEY_DATA");
        System.out.println("asdf "+ serializeNameGson);
        Gson gson = new Gson();
        Type typeAccount = new TypeToken<DataBarang>(){}.getType();
        dataBarang = gson.fromJson(serializeNameGson,typeAccount);

        img = findViewById(R.id.img_produk);
        harga = findViewById(R.id.txt_harga);
        nama = findViewById(R.id.txt_nama);
        jenis2 = findViewById(R.id.nama_jenis);
        deskripsi2 = findViewById(R.id.nama_deskripsi);
        stok = findViewById(R.id.img_stok);
        jumlah = findViewById(R.id.jumlah);
        total1 = findViewById(R.id.total);
        satuan = findViewById(R.id.img_satuan);

        btn_keranjang = findViewById(R.id.btn_krnjg);
        btn_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertKeranjang();
            }
        });

        btn_beli = findViewById(R.id.btn_beli_detail);
        btn_beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        String hrg = dataBarang.getHargaJual();
        System.out.println("asd"+dataBarang.getHargaJual());
        int rp = Integer.parseInt(hrg);
        String torp = toRupiah(rp);
        harga.setText(torp);
        nama.setText(dataBarang.getNamaBarang());
        jenis2.setText(dataBarang.getJenis_barang());
        deskripsi2.setText(dataBarang.getDeskripsi());
        stok.setText(dataBarang.getStok());
        satuan.setText(dataBarang.getSatuan());

        String hrg1 = dataBarang.getHargaJual();
        int rp1 = Integer.parseInt(hrg);
        String torp1 = toRupiah(rp);
        total1.setText(torp1);

        Picasso.get().load(dataBarang.getGambar()).resize(512,512).centerCrop().into(img);

        bt_back = findViewById(R.id.btn_bck);
        bt_back.setOnClickListener(view -> onBackPressed());

        kurang = findViewById(R.id.kurang);
        kurang.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if (count <= 1) count=1;
                else
                count--;
                jumlah.setText(""+count);
                total = Integer.parseInt(jumlah.getText().toString()) * Integer.parseInt(dataBarang.getHargaJual());
                String hasilConvert = toRupiah(total);
                total1.setText(hasilConvert);
            }
        });
        tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if (count >= Integer.parseInt(stok.getText().toString()));
                else
                count++;
                jumlah.setText(""+count);
                total = Integer.parseInt(jumlah.getText().toString()) * Integer.parseInt(dataBarang.getHargaJual());
                String hasilConvert = toRupiah(total);
                total1.setText(hasilConvert);
            }
        });
    }
    private void insertKeranjang(){
        Intent intent  = getIntent();
        String serializeNameGson = intent.getStringExtra("KEY_DATA");
        Gson gson = new Gson();
        Type typeAccount = new TypeToken<DataBarang>(){}.getType();

        ServiceLogin service = ApiClient.getClient().create(ServiceLogin.class);
        DataItem mpa = SaveAccount.readDataPembeli(detail_barang.this);
        if(Integer.parseInt(jumlah.getText().toString()) == 1){
            total = Integer.parseInt(dataBarang.getHargaJual());
        }

        Call<ResponseKeranjang> call = service.insertKeranjang(mpa.getIdPembeli(), dataBarang.getKdBarang(), jumlah.getText().toString(),
                String.valueOf(total));
        call.enqueue(new Callback<ResponseKeranjang>() {
            @Override
            public void onResponse(Call<ResponseKeranjang> call, Response<ResponseKeranjang> response) {
                if(response.body().getKode() == 1) {
                    View view = getLayoutInflater().inflate(R.layout.toast_insertbarang, null);
                    view.findViewById(R.id.toast_insertbarang);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }else{
                    Toast.makeText(detail_barang.this, "Gagal menambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                }
            }
            @SuppressLint("MissingInflatedId")
            @Override
            public void onFailure(Call<ResponseKeranjang> call, Throwable t) {
                Toast.makeText(detail_barang.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                View view = getLayoutInflater().inflate(R.layout.toast_insertbarang, null);
                view.findViewById(R.id.toast_insertbarang);
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
                toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
            }
        });
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