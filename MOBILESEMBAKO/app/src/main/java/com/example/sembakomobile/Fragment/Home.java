package com.example.sembakomobile.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Activity.Cart;
import com.example.sembakomobile.Activity.SaveAccount;
import com.example.sembakomobile.Activity.cari_barang;
import com.example.sembakomobile.Activity.detail_barang;
import com.example.sembakomobile.Adapter.AdapterBarang;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.ResponseModelBarang;
import com.example.sembakomobile.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private List<DataBarang> dataBarangList = new ArrayList<>();
    ImageButton semua, makanan, snack, minuman, bumbu;
    ImageButton sembako, pencil, mandi, cuci, alat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView nama1;
        Button btn_src;
        ImageButton btn_chat, btn_chart;
        SwipeRefreshLayout putar;

        putar = view.findViewById(R.id.swap);
        putar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                putar.setRefreshing(true);
                retrieveData();
                putar.setRefreshing(false);
            }
        });

        btn_chat = view.findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(ch -> {
                    String wpurl = "https://wa.me/+6287704632355";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(wpurl));
                    startActivity(intent);
                });
        Fragment fr = this;
        btn_chart = view.findViewById(R.id.btn_chart);
        btn_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView)).commit();

                startActivity(new Intent(getActivity(), Cart.class));
            }
        });

        nama1 = view.findViewById(R.id.nama);
        nama1.setText("Selamat Datang "+SaveAccount.readDataPembeli(getActivity()).getNamaPembeli());

        btn_src = view.findViewById(R.id.btn_src);
        btn_src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), cari_barang.class));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        retrieveData();
        AllBarang();
        AllMakanan();
        AllMakananRingan();
        AllMinuman();
        AllBumbu();
        AllSembako();
        AllTulis();
        AllMandi();
        AllCuci();
        AllRumah();

        rvData = view.findViewById(R.id.rv_data);
        rvData.setHasFixedSize(true);
        rvData.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    public void AllBarang() {
        semua = getView().findViewById(R.id.img_semua);
        semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData();
            }
        });
    }

    public void AllMakanan() {
        makanan = getView().findViewById(R.id.img_makanan);
        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataMakanan();
            }
        });
    }

    public void AllMakananRingan() {
        snack = getView().findViewById(R.id.img_snack);
        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataSnack();
            }
        });
    }

    public void AllMinuman() {
        minuman = getView().findViewById(R.id.img_minuman);
        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataMinuman();
            }
        });
    }

    public void AllBumbu() {
        bumbu = getView().findViewById(R.id.img_bumbu);
        bumbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataBumbu();
            }
        });
    }

    public void AllSembako() {
        sembako = getView().findViewById(R.id.img_sembako);
        sembako.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataSembako();
            }
        });
    }

    public void AllTulis() {
        pencil = getView().findViewById(R.id.img_pencil);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataAlatTulis();
            }
        });
    }

    public void AllMandi() {
        mandi = getView().findViewById(R.id.img_mandi);
        mandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataMandi();
            }
        });
    }

    public void AllCuci() {
        cuci = getView().findViewById(R.id.img_cuci);
        cuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataMencuci();
            }
        });
    }

    public void AllRumah() {
        alat = getView().findViewById(R.id.img_alat);
        alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveDataRumah();
            }
        });
    }

    public void detail(int position){
        DataBarang dm = dataBarangList.get(position);
    }

    public void retrieveData() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilBarang();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        System.out.println("asdfg "+serializeObject);
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
                    }
                };
                adData = new AdapterBarang(getContext(),
                dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
            }
        });
    }
    public void retrieveDataMakanan() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilMakanan();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataMinuman() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilMinuman();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataSnack() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilSnack();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataBumbu() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilBumbu();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataSembako() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilSembako();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataAlatTulis() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilTulis();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataMandi() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilMandi();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataMencuci() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilMencuci();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataRumah() {
        ServiceLogin service1 = ApiClient.getClient().create(ServiceLogin.class);
        Call<ResponseModelBarang> tampilData = service1.tampilRumah();
        tampilData.enqueue(new Callback<ResponseModelBarang>() {
            @Override
            public void onResponse(Call<ResponseModelBarang> call, Response<ResponseModelBarang> response) {
                byte kode = response.body().getKode();
                String message = response.body().getMessage();

                dataBarangList = response.body().getData();
                Gson gson = new Gson();
                System.out.println(gson.toJson(dataBarangList) + " JSON DATA");
                AdapterBarang.adapterBarangListener adapterBarangListener = new AdapterBarang.adapterBarangListener() {
                    @Override
                    public void selectedItemListener(int positionOfItemClicked) {
                        Intent intent  = new Intent(getActivity().getApplicationContext(), detail_barang.class);
                        Gson gso1 = new Gson();
                        String serializeObject = gso1.toJson(dataBarangList.get(positionOfItemClicked));
                        intent.putExtra("KEY_DATA",serializeObject);
                        startActivity(intent);
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Click item = " + dataBarangList.get(positionOfItemClicked).getGambar(), Toast.LENGTH_SHORT).show();
                    }
                };
                adData = new AdapterBarang(getContext(), dataBarangList, adapterBarangListener);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<ResponseModelBarang> call, Throwable t) {
//                Toast.makeText(getActivity(), "Server Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}