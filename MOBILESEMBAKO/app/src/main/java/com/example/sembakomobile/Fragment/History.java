package com.example.sembakomobile.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Activity.SaveAccount;
import com.example.sembakomobile.Activity.detail_barang;
import com.example.sembakomobile.Activity.transaksi;
import com.example.sembakomobile.Adapter.AdapterBarang;
import com.example.sembakomobile.Adapter.AdapterRiwayat;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.Model.Retrofit.DataTransaksi;
import com.example.sembakomobile.Model.Retrofit.ResponseDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.ResponseModelBarang;
import com.example.sembakomobile.Model.Retrofit.ResponseTransaksi;
import com.example.sembakomobile.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends Fragment {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private List<DataDetailTransaksi> dataDetailTransaksi = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        rvData = view.findViewById(R.id.rv_cart);
        retrieveData();

    }
    public void retrieveData() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.tampilTrx(cri.getIdPembeli(), "selesai");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();

                System.out.println("datatrx "+dataDetailTransaksi);
                dataDetailTransaksi = response.body().getData();
                if(dataDetailTransaksi == null) {
                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
                    view.findViewById(R.id.toast_datanull);
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
                adData = new AdapterRiwayat(getContext(), dataDetailTransaksi);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }


}