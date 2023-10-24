package com.example.sembakomobile.Rincian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sembakomobile.Activity.SaveAccount;
import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.R;

import java.util.ArrayList;
import java.util.List;

public class RincianDikirim extends AppCompatActivity {
    private RecyclerView.Adapter rincian;
    List<DataKeranjang> listDataKeranjangSengDipilihUser;
    private List<DataDetailTransaksi> dikirim = new ArrayList<>();
    private RecyclerView rv_rincian;
    int position;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_dikirim);
        TextView alamat, nama, nopesanan, tanggal, total;
        Button batalkan;
        ImageButton kembali;

        rv_rincian = findViewById(R.id.rv_rincian);

        kembali = findViewById(R.id.kembali_rincian);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nopesanan = findViewById(R.id.notrx_detail);

        tanggal = findViewById(R.id.tanggal_detail);

        nama = findViewById(R.id.text_nama_pembeli);
        nama.setText(SaveAccount.readDataPembeli(RincianDikirim.this).getNamaPembeli());

        alamat = findViewById(R.id.text_alamat);
        alamat.setText(SaveAccount.readDataPembeli(RincianDikirim.this).getAlamat());
    }
}