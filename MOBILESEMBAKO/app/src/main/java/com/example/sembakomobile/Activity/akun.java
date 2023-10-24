package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sembakomobile.API.ApiClient;
import com.example.sembakomobile.API.ServiceLogin;
import com.example.sembakomobile.Model.Retrofit.DataItem;
import com.example.sembakomobile.Model.Retrofit.ResponseModel;
import com.example.sembakomobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class akun extends AppCompatActivity {
    ImageButton bt_back;
    TextView nama1, nama2, nama3, nama4, tt_akun, alert_username1, alert_nama1, alert_alamat1, alert_nohp1;
    EditText alert_username2, alert_nama2, alert_alamat2, alert_nohp2;
    Button btn1, btn_konfir, btn_batal;
    EditText username, nama, alamat, nohp;
    SharedPreferences shared;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        nama1 = findViewById(R.id.username_akun);
        nama1.setText(SaveAccount.readDataPembeli(akun.this).getUsername());

        nama2 = findViewById(R.id.namaLengkap_akun);
        nama2.setText(SaveAccount.readDataPembeli(akun.this).getNamaPembeli());

        nama3 = findViewById(R.id.alamat_akun);
        nama3.setText(SaveAccount.readDataPembeli(akun.this).getAlamat());

        nama4 = findViewById(R.id.nohp_akun);
        nama4.setText(SaveAccount.readDataPembeli(akun.this).getNo_hp());

        bt_back = findViewById(R.id.button_Back);
        bt_back.setOnClickListener(view -> onBackPressed());

        btn1 = findViewById(R.id.btn_editakun);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog();}
        });
    }
    @SuppressLint("MissingInflatedId")
    private void alertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(akun.this);
        View view = getLayoutInflater().inflate(R.layout.activity_akun_alert, null);
        alert.setView(view);
        AlertDialog alert1 = alert.create();
        alert1.show();

        username = view.findViewById(R.id.alert_username2);
        username.setText(SaveAccount.readDataPembeli(akun.this).getUsername());

        nama = view.findViewById(R.id.alert_nama2);
        nama.setText(SaveAccount.readDataPembeli(akun.this).getNamaPembeli());

        alamat = view.findViewById(R.id.alert_alamat2);
        alamat.setText(SaveAccount.readDataPembeli(akun.this).getAlamat());

        nohp = view.findViewById(R.id.alert_nohp2);
        nohp.setText(SaveAccount.readDataPembeli(akun.this).getNo_hp());

        tt_akun = view.findViewById(R.id.tt_akun);
        alert_username1 = view.findViewById(R.id.alert_username1);
        alert_username2 = view.findViewById(R.id.alert_username2);

        alert_nama1 = view.findViewById(R.id.alert_nama1);
        alert_nama2 = view.findViewById(R.id.alert_nama2);

        alert_alamat1 = view.findViewById(R.id.alert_alamat1);
        alert_alamat2 = view.findViewById(R.id.alert_alamat2);

        alert_nohp1 = view.findViewById(R.id.alert_nohp1);
        alert_nohp2 = view.findViewById(R.id.alert_nohp2);

        btn_konfir = view.findViewById(R.id.alert_btn_konfirmasi);
        btn_konfir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceLogin service = ApiClient.getClient().create(ServiceLogin.class);
                DataItem mpa = SaveAccount.readDataPembeli(akun.this);
                Call<ResponseModel> simpan = service.updateResponse(mpa.getIdPembeli(), alert_nama2.getText().toString(),
                alert_alamat2.getText().toString(), alert_nohp2.getText().toString(), alert_username2.getText().toString());
                simpan.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.body().getKode() == 1){
                            shared = getSharedPreferences("myapp-data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putBoolean("homeId", true);
                            editor.commit();
                            View view = getLayoutInflater().inflate(R.layout.toast_akunberhasil, null);
                            view.findViewById(R.id.toast_akunberhasil);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(view);
                            toast.show();
                            toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                            DataItem mpasal = SaveAccount.readDataPembeli(akun.this);
                            mpasal.setUsername(alert_username2.getText().toString());
                            mpasal.setNamaPembeli(alert_nama2.getText().toString());
                            mpasal.setAlamat(alert_alamat2.getText().toString());
                            mpasal.setNo_hp(alert_nohp2.getText().toString());
                            SaveAccount.writeDataPembeli(akun.this, mpasal);
                            onBackPressed();

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(akun.this, "Server Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_batal = view.findViewById(R.id.alert_btn_cancel);
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert1.cancel();
            }
        });
    }
}

