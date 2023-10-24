package com.example.sembakomobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.Model.Retrofit.DataTransaksi;
import com.example.sembakomobile.R;

import java.util.List;

public class AdapterSampai extends RecyclerView.Adapter<AdapterSampai.StatusHolderData>{

    private Context ctx;
    private List<DataDetailTransaksi> sampai;

    public AdapterSampai(Context crx, List<DataDetailTransaksi> dataTransaksi) {
        this.ctx = crx;
        this.sampai = dataTransaksi;
    }

    @NonNull
    @Override
    public StatusHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cv_diterima, parent, false);
        return new StatusHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusHolderData holder, int position) {
        DataDetailTransaksi ddk = sampai.get(position);

        holder.idtrx.setText(ddk.getIdTransaksi());
        holder.tanggal.setText(ddk.getTanggal());

    }

    @Override
    public int getItemCount() {
        return  sampai == null ? 0 : sampai.size();
    }

    public class StatusHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idtrx, tanggal;

        public StatusHolderData(@NonNull View itemView){
            super(itemView);
            idtrx = itemView.findViewById(R.id.idtrx_pending);
            tanggal = itemView.findViewById(R.id.tgl);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
