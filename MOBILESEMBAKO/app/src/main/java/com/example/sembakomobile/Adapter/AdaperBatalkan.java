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

public class AdaperBatalkan extends  RecyclerView.Adapter<AdaperBatalkan.StatusHolderData>{

    private Context ctx;
    private List<DataDetailTransaksi> dibatalkan;

    public AdaperBatalkan(Context crx, List<DataDetailTransaksi> dataTransaksi) {
        this.ctx = crx;
        this.dibatalkan = dataTransaksi;
    }

    @NonNull
    @Override
    public StatusHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cv_dibatalkan, parent, false);
        return new StatusHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusHolderData holder, int position) {
        DataDetailTransaksi ddk = dibatalkan.get(position);

        holder.idtrx.setText(ddk.getIdTransaksi());
        holder.tanggal.setText(ddk.getTanggal());
    }

    @Override
    public int getItemCount() {
        return  dibatalkan == null ? 0 : dibatalkan.size();
    }

    public class StatusHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idtrx, tanggal;

        public StatusHolderData(@NonNull View itemView){
            super(itemView);
            idtrx = itemView.findViewById(R.id.id_batal);
            tanggal = itemView.findViewById(R.id.tgl_batal);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
