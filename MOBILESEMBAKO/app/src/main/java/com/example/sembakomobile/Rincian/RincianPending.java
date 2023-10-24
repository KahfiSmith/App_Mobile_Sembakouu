package com.example.sembakomobile.Rincian;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Activity.Cart;
import com.example.sembakomobile.Activity.Dashboard;
import com.example.sembakomobile.Activity.Lottebatal;
import com.example.sembakomobile.Activity.SaveAccount;
import com.example.sembakomobile.Activity.transaksi;
import com.example.sembakomobile.Adapter.AdapterCheckout;
import com.example.sembakomobile.Adapter.AdapterPending;
import com.example.sembakomobile.Adapter.AdapterRincian;
import com.example.sembakomobile.Adapter.AdapterRiwayat;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.Model.Retrofit.ResponseDetailTransaksi;
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

public class RincianPending extends AppCompatActivity {
    private RecyclerView.Adapter rincian;
    List<DataKeranjang> listDataKeranjangSengDipilihUser;
    private List<DataDetailTransaksi> dataPending = new ArrayList<>();
    private RecyclerView rv_rincian;
    int position;
    public static String toRupiah(int rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator('.');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(rupiah);
    }
    int totalHarga = 0;
    DataDetailTransaksi dataDetailTransaksi;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_pending);
        TextView alamat, nama, nopesanan, tanggal, total;
        Button batalkan;
        ImageButton kembali;
        Intent intent = getIntent();
        String serializeName = intent.getStringExtra("KEY_PENDING");
        Type typeAccount = new TypeToken<DataDetailTransaksi>(){}.getType();
        total = findViewById(R.id.checkout_total_t);
         dataDetailTransaksi = new Gson().fromJson(serializeName,typeAccount);
        for(int i = 0; i < dataDetailTransaksi.getList_barang().size(); i++){
            totalHarga += Integer.parseInt(dataDetailTransaksi.getList_barang().get(i).getSub_total());
        }
        System.out.println(dataDetailTransaksi.getIdTransaksi() + " id transaksi");
        System.out.println(serializeName + " COK ");
        rv_rincian = findViewById(R.id.rv_rincian);
        AdapterRincian adapterRincian = new AdapterRincian(getApplicationContext(),dataDetailTransaksi.getList_barang());
        rv_rincian.setAdapter(adapterRincian);
        adapterRincian.notifyDataSetChanged();
        kembali = findViewById(R.id.kembali_rincian);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        total.setText(toRupiah(totalHarga));
        nopesanan = findViewById(R.id.notrx_detail);
        nopesanan.setText(dataDetailTransaksi.getIdTransaksi());
        tanggal = findViewById(R.id.tanggal_detail);
        tanggal.setText(dataDetailTransaksi.getList_barang().get(0).getTanggal());
        batalkan = findViewById(R.id.rincian_btpesan);
        batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBatalkan();
            }
        });

        nama = findViewById(R.id.text_nama_pembeli);
        nama.setText(SaveAccount.readDataPembeli(RincianPending.this).getNamaPembeli());

        alamat = findViewById(R.id.text_alamat);
        alamat.setText(SaveAccount.readDataPembeli(RincianPending.this).getAlamat());

    }

    public void btnBatalkan() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getApplicationContext().getApplicationContext());
            Call<ResponseDetailTransaksi> tampilData = service1.update_dibatalkan(cri.getIdPembeli(), dataDetailTransaksi.getIdTransaksi(), "dibatalkan");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();
                System.out.println("datatrx "+listDataKeranjangSengDipilihUser);
                dataPending = response.body().getData();
                Intent intent = new Intent(RincianPending.this, Lottebatal.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }


}