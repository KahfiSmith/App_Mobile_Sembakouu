package com.example.sembakomobile.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class reset_password extends AppCompatActivity {
    TextView username_pass2, password_pass2, reset_password, pass;
    ImageButton bt_back;
    EditText password_lama, password_new;
//    String passwordLama, passwordNew;
    Button btn_edit_pass, btn_konfir_pass, btn_batal_pass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        username_pass2 =  findViewById(R.id.txt_username2);
        username_pass2.setText(SaveAccount.readDataPembeli(reset_password.this).getUsername());

        password_pass2 = findViewById(R.id.txt_password2);
        password_pass2.setText(SaveAccount.readDataPembeli(reset_password.this).getPassword());

        bt_back = findViewById(R.id.button_Back1);
        bt_back.setOnClickListener(view -> onBackPressed());

        btn_edit_pass = findViewById(R.id.btn_edit_pass);
        btn_edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog(); }
        });
    }
    @SuppressLint("MissingInflatedId")
    private void alertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(reset_password.this);
        View view = getLayoutInflater().inflate(R.layout.activity_reset_password_alert, null);
        alert.setView(view);
        AlertDialog alert2 = alert.create();
        alert2.show();

        reset_password = view.findViewById(R.id.reset_password);
        password_new = view.findViewById(R.id.password_new);
        password_lama = view.findViewById(R.id.password_lama);

        pass = view.findViewById(R.id.pass);
        pass.setText(SaveAccount.readDataPembeli(reset_password.this).getPassword());

        btn_konfir_pass = view.findViewById(R.id.btn_konfir_pass);
        btn_konfir_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passlama = password_lama.getText().toString();
                String passbaru = password_new.getText().toString();
                String pws = pass.getText().toString();

                if (passlama.equals("") || passbaru.equals("")) {
                    view = getLayoutInflater().inflate(R.layout.toast_pwkosong, null);
                    view.findViewById(R.id.toast_pwkosong);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                } else if (!passlama.equals(pws)) {
                    view = getLayoutInflater().inflate(R.layout.toast_pwsalah, null);
                    view.findViewById(R.id.toast_pwsalah);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM| Gravity.CENTER,0,200);
                } else if (passlama.equals(pws)) {
                    inputpw();
                    finish();
                }
            }

        });
        btn_batal_pass = view.findViewById(R.id.btn_batal_pass);
        btn_batal_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert2.cancel();
            }
        });
    }

    private void inputpw() {
        ServiceLogin service = ApiClient.getClient().create(ServiceLogin.class);
        DataItem mpa = SaveAccount.readDataPembeli(reset_password.this);
        Call<ResponseModel> simpan = service.updatePw(mpa.getIdPembeli(), password_new.getText().toString());
        simpan.enqueue(new Callback<ResponseModel>() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                View view;
                if (response.body().getKode() == 1) {
                    view = getLayoutInflater().inflate(R.layout.toast_pwberhasil, null);
                    view.findViewById(R.id.toast_pwberhasil);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);

                    DataItem mpaskmn = SaveAccount.readDataPembeli(reset_password.this);
                    mpaskmn.setPassword(password_new.getText().toString());
                    SaveAccount.writeDataPembeli(reset_password.this, mpaskmn);
                } else {
                    view = getLayoutInflater().inflate(R.layout.toast_pwgagal, null);
                    view.findViewById(R.id.toast_pwgagal);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view);
                    toast.show();
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(reset_password.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        onBackPressed();
    }
}