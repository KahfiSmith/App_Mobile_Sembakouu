package com.example.sembakomobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sembakomobile.Model.Retrofit.DataDetailTransaksi;
import com.example.sembakomobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPending extends RecyclerView.Adapter<AdapterPending.StatusHolderData>{
    private Context ctx;
    private List<DataDetailTransaksi> dataPending;
    static AdapterPending.AdapterPendingInterface adapterBarangListener;

    public AdapterPending(Context crx, List<DataDetailTransaksi> dataTransaksi, AdapterPending.AdapterPendingInterface adapterBarangListener) {
        this.ctx = crx;
        this.dataPending = dataTransaksi;
        this.adapterBarangListener = adapterBarangListener;

    }

    public interface AdapterPendingInterface{
        void clikAdapterPending(int position);
    }
    @NonNull
    @Override
    public StatusHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cv_pending, parent, false);
        return new StatusHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusHolderData holder, int position) {
        DataDetailTransaksi ddk = dataPending.get(position);

        holder.idtrx.setText(ddk.getIdTransaksi());
        holder.tanggal.setText(ddk.getTanggal());
    }

    @Override
    public int getItemCount() {
        return  dataPending == null ? 0 : dataPending.size();
    }

    public class StatusHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idtrx, tanggal, namabarang, jumlah, harga, sub_total;
        ImageView gambar;

        public StatusHolderData(@NonNull View itemView){
            super(itemView);
            idtrx = itemView.findViewById(R.id.idtrx_pending);
            tanggal = itemView.findViewById(R.id.tgl);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            adapterBarangListener.clikAdapterPending(getAdapterPosition());
        }
    }

}
