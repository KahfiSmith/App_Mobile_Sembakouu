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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Activity.SaveAccount;
import com.example.sembakomobile.Activity.cari_barang;
import com.example.sembakomobile.Activity.detail_barang;
import com.example.sembakomobile.Adapter.AdaperBatalkan;
import com.example.sembakomobile.Adapter.AdapterBarang;
import com.example.sembakomobile.Adapter.AdapterDikemas;
import com.example.sembakomobile.Adapter.AdapterDikirim;
import com.example.sembakomobile.Adapter.AdapterPending;
import com.example.sembakomobile.Adapter.AdapterRiwayat;
import com.example.sembakomobile.Adapter.AdapterSampai;
import com.example.sembakomobile.Adapter.AdapterSelesai;
import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.DataTransaksi;
import com.example.sembakomobile.Model.Retrofit.ResponseDetailTransaksi;
import com.example.sembakomobile.R;
import com.example.sembakomobile.Rincian.RincianPending;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaksi extends Fragment {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private List<DataDetailTransaksi> dataPending = new ArrayList<>();
    private List<DataDetailTransaksi> dikemas = new ArrayList<>();
    private List<DataDetailTransaksi> dikirim = new ArrayList<>();
    private List<DataDetailTransaksi> sampai = new ArrayList<>();
    private List<DataDetailTransaksi> selesai = new ArrayList<>();
    private List<DataDetailTransaksi> batalkan = new ArrayList<>();
    TextView txt_pending, txt_dikemas, txt_dikirim, txt_sampai, txt_selesai, txt_batal;
    String id_trx, tanggal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        rvData = view.findViewById(R.id.rv_pending);

        pending();
        klik_pending();
        klik_dikemas();
        klik_dikirim();
//        klik_sampai();
        klik_selesai();
        klik_batalkan();
        AdapterPending.AdapterPendingInterface adapterPendingInterface = new AdapterPending.AdapterPendingInterface() {
            @Override
            public void clikAdapterPending(int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(),RincianPending.class);
                intent.putExtra("KEY_PENDING",new Gson().toJson(dataPending.get(position)));
                startActivity(intent);

            }
        };
        adData = new AdapterPending(getContext(), dataPending,adapterPendingInterface);
        rvData.setAdapter(adData);
        adData.notifyDataSetChanged();
    }

    public void klik_pending() {
        txt_pending = getView().findViewById(R.id.txt_pending);
        txt_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending();
            }
        });
    }

    public void klik_dikemas() {
        txt_dikemas = getView().findViewById(R.id.Dikemas);
        txt_dikemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dikemas();
            }
        });
    }

    public void klik_dikirim() {
        txt_dikirim = getView().findViewById(R.id.Dikirim);
        txt_dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dikirim();
            }
        });
    }

//    public void klik_sampai() {
//        txt_sampai = getView().findViewById(R.id.Diterima);
//        txt_sampai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sampai();
//            }
//        });
//    }

    public void klik_selesai() {
        txt_selesai = getView().findViewById(R.id.Selesai);
        txt_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selesai();
            }
        });
    }

    public void klik_batalkan() {
        txt_batal = getView().findViewById(R.id.Dibatalkan);
        txt_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dibatalkan();
            }
        });
    }

    public void pending() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.pending(cri.getIdPembeli(), "pending");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();
                dataPending = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataPending) + " JSON DATA");
                AdapterPending.AdapterPendingInterface adapterBarangListener = new AdapterPending.AdapterPendingInterface() {
                    @Override
                    public void clikAdapterPending(int position) {
                        Intent intent = new Intent(getActivity().getApplicationContext(),RincianPending.class);
                        intent.putExtra("KEY_PENDING",new Gson().toJson(dataPending.get(position)));
                        startActivity(intent);

                    }
                };
                if(dataPending == null) {
                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
                    view.findViewById(R.id.toast_datanull);
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
                adData = new AdapterPending(getContext(), dataPending, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }

    public void dikemas() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.dikemas(cri.getIdPembeli(), "dikemas");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();

                dikemas = response.body().getData();
                if(dikemas == null){
                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
                    view.findViewById(R.id.toast_datanull);
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
                    adData = new AdapterDikemas(getContext(), dikemas);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }

    public void dikirim() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.dikirim(cri.getIdPembeli(), "dikirim");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();

                dikirim = response.body().getData();
                if(dikirim == null) {
                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
                    view.findViewById(R.id.toast_datanull);
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
                adData = new AdapterDikirim(getContext(), dikirim);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }

//    public void sampai() {
//        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
//        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
//        Call<ResponseDetailTransaksi> tampilData = service1.sampai(cri.getIdPembeli(), "sampai");
//        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
//            @Override
//            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
//                byte kode = response.body().getKode();
//                System.out.println("res "+new Gson().toJson(response.body().getData()));
//                String message = response.body().getMessage();
//
//                sampai = response.body().getData();
//                if(sampai == null) {
//                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
//                    view.findViewById(R.id.toast_datanull);
//                    Toast toast = new Toast(getActivity());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(view);
//                    toast.show();
//                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
//                }
//                adData = new AdapterSampai(getContext(), sampai);
//                rvData.setAdapter(adData);
//                adData.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
//            }
//        });
//    }
    public void selesai() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.selesai(cri.getIdPembeli(), "selesai");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();

                selesai = response.body().getData();
                if(selesai == null) {
                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
                    view.findViewById(R.id.toast_datanull);
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
                adData = new AdapterSelesai(getContext(), selesai);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }

    public void dibatalkan() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        DataItem cri = SaveAccount.readDataPembeli(getActivity().getApplicationContext());
        Call<ResponseDetailTransaksi> tampilData = service1.dibatalkan(cri.getIdPembeli(), "dibatalkan");
        tampilData.enqueue(new Callback<ResponseDetailTransaksi>() {
            @Override
            public void onResponse(Call<ResponseDetailTransaksi> call, Response<ResponseDetailTransaksi> response) {
                byte kode = response.body().getKode();
                System.out.println("res "+new Gson().toJson(response.body().getData()));
                String message = response.body().getMessage();

                batalkan = response.body().getData();
                if(batalkan == null) {
                    View view = getLayoutInflater().inflate(R.layout.toast_datanull, null);
                    view.findViewById(R.id.toast_datanull);
                    Toast toast = new Toast(getActivity());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                }
                adData = new AdaperBatalkan(getContext(), batalkan);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDetailTransaksi> call, Throwable t) {
            }
        });
    }

}