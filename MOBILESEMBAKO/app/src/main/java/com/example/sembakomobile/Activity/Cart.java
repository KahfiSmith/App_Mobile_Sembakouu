package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Adapter.AdapterCart;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.Model.Retrofit.DataTransaksi;
import com.example.sembakomobile.Model.Retrofit.ResponseKeranjang;
import com.example.sembakomobile.Model.Retrofit.ResponseTransaksi;
import com.example.sembakomobile.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    private RecyclerView rv_cart;
    private RecyclerView.Adapter cartData;
    private List<DataTransaksi> insertTransaksi = new ArrayList<>();
    private List<DataKeranjang> cartList = new ArrayList<>();
    private List<DataKeranjang> listKeranjangDipilih = new ArrayList<>();
    TextView total_harga;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Button btn_keranjang;
        ImageButton kembali;

        keranjang();
        rv_cart = findViewById(R.id.rv_cart);
        total_harga = findViewById(R.id.total_harga);
        total_harga.setText(toRupiah(0));

        kembali = findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Dashboard.class);
                startActivity(intent);

            }
        });
        btn_keranjang = findViewById(R.id.btn_keranjang);
        btn_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (listKeranjangDipilih == null){
                        Toast.makeText(Cart.this, "Mohon pilih barang anda", Toast.LENGTH_SHORT).show();
                    }else{
                        if (listKeranjangDipilih.isEmpty()){
                            view = getLayoutInflater().inflate(R.layout.toast_nopilihbarang, null);
                            view.findViewById(R.id.toast_nopilihbarang);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(view);
                            toast.show();
                            toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                        }else{
                            keranjang();
                            insertTRX();
                            finish();
                        }
                    }
            }
        });
    }

    public void detail(int position) {
        DataTransaksi db = insertTransaksi.get(position);
    }

    String idTransaksi = "";
    public void insertTRX() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cr = SaveAccount.readDataPembeli(Cart.this);
        Call<ResponseTransaksi> insert = service1.insertTransaksi(String.valueOf(totalHarga), cr.getIdPembeli(), "pending");
        System.out.println("idpembeli "+cr.getIdPembeli());
        System.out.println("totalharga "+String.valueOf(totalHarga));
        insert.enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                if(response.body().getKode() == 1) {
                System.out.println(response.body().getIdTransaksi() + " TRX");
                    idTransaksi = response.body().getIdTransaksi();
                            Intent intent = new Intent(Cart.this,transaksi.class);
                            Gson gson = new Gson();
                            intent.putExtra("LIST_BARANG",gson.toJson(listKeranjangDipilih));
                            intent.putExtra("ID_TRX",idTransaksi);
                            startActivity(intent);

                } else {
                    Toast.makeText(Cart.this, "Gagal Membuat Pesanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {

            }
        });
    }

    int totalHarga = 0;
    public void keranjang() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem dk = SaveAccount.readDataPembeli(Cart.this);
        Call<ResponseKeranjang> tampilData = service1.tampilKeranjang(dk.getIdPembeli());
        tampilData.enqueue(new Callback<ResponseKeranjang>() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onResponse(Call<ResponseKeranjang> call, Response<ResponseKeranjang> response) {
                byte kode = response.body().getKode();
                if (kode == 1) {
                    cartList = response.body().getData();
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(cartList) + " JSON DATA");

                    AdapterCart.AdapterCartClick adapterCartClick = new AdapterCart.AdapterCartClick() {
                        @Override
                        public void onDeleteClick(int position) {
                            ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
                            Call<ResponseKeranjang> tampilData = service1.deleteBarang(cartList.get(position).getKdBarang());
                            tampilData.enqueue(new Callback<ResponseKeranjang>() {
                             @SuppressLint("MissingInflatedId")
                             @Override
                             public void onResponse(Call<ResponseKeranjang> call, Response<ResponseKeranjang> response) {
                                 cartList.remove(position);
                                 cartData.notifyDataSetChanged();;
                                 View view = getLayoutInflater().inflate(R.layout.toast_carthapus, null);
                                 view.findViewById(R.id.toast_carthapus);
                                 Toast toast = new Toast(getApplicationContext());
                                 toast.setDuration(Toast.LENGTH_SHORT);
                                 toast.setView(view);
                                 toast.show();
                                 toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                             }

                             @Override
                             public void onFailure(Call<ResponseKeranjang> call, Throwable t) {
                                 Toast.makeText(Cart.this, "", Toast.LENGTH_SHORT).show();
                             }
                            });
                        }
                        @Override
                        public void onCheckBox(int position, boolean status) {
                            cartList.get(position).setSelectedCheck(status);
                            if(cartList.get(position).isSelectedCheck()){
                                totalHarga += Integer.parseInt(cartList.get(position).getHargaJual()) * Integer.parseInt(cartList.get(position).getJumlah());
;                               listKeranjangDipilih.add(cartList.get(position));
                            } else {
                                totalHarga -= Integer.parseInt(cartList.get(position).getHargaJual()) * Integer.parseInt(cartList.get(position).getJumlah());;
                                listKeranjangDipilih.remove(cartList.get(position));
                            }
                            total_harga.setText(toRupiah(totalHarga));
                            System.out.println(totalHarga + " TOTAL HARGA");
                        }
                        @Override
                        public void onCheckBoxNotSelected(int position) {
                            cartList.get(position).setSelectedCheck(false);
                            totalHarga -=  Integer.parseInt(cartList.get(position).getHargaJual()) * Integer.parseInt(cartList.get(position).getJumlah());;
                            System.out.println("total Harga = "+ totalHarga);cartData.notifyDataSetChanged();
                        }

                    };
                    cartData = new AdapterCart(Cart.this, cartList, adapterCartClick);
                    rv_cart.setAdapter(cartData);
                    cartData.notifyDataSetChanged();
                } else {
                    View view = getLayoutInflater().inflate(R.layout.toast_cartkosong, null);
                    view.findViewById(R.id.toast_cartkosong);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
            }

            @Override
            public void onFailure(Call<ResponseKeranjang> call, Throwable t) {
                Toast.makeText(Cart.this, "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
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