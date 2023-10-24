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

public class AdapterSelesai extends RecyclerView.Adapter<AdapterSelesai.StatusHolderData>{

    private Context ctx;
    private List<DataDetailTransaksi> selesai;

    public AdapterSelesai(Context crx, List<DataDetailTransaksi> dataTransaksi) {
        this.ctx = crx;
        this.selesai = dataTransaksi;
    }

    @NonNull
    @Override
    public AdapterSelesai.StatusHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cv_selesai, parent, false);
        return new StatusHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelesai.StatusHolderData holder, int position) {
        DataDetailTransaksi ddk = selesai.get(position);

        holder.idtrx.setText(ddk.getIdTransaksi());
        holder.tanggal.setText(ddk.getTanggal());
    }

    @Override
    public int getItemCount() {
        return selesai == null ? 0 : selesai.size();
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
