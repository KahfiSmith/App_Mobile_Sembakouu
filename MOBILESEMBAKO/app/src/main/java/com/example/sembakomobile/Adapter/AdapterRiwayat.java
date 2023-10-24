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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.StatusHolderData>{
    private Context ctx;
    private List<DataDetailTransaksi> dataDetailTransaksi;

    public AdapterRiwayat(Context crx, List<DataDetailTransaksi> dataDetailTransaksi) {
        this.ctx = crx;
        this.dataDetailTransaksi = dataDetailTransaksi;
    }

    @NonNull
    @Override
    public AdapterRiwayat.StatusHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cv_riwayat, parent, false);
        return new StatusHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRiwayat.StatusHolderData holder, int position) {
        DataDetailTransaksi ddk = dataDetailTransaksi.get(position);

        Picasso.get()
                .load(dataDetailTransaksi.get(position).getGambar()).resize(300,300).centerCrop()
                .into(holder.gambar);
        holder.idtrx.setText(ddk.getIdTransaksi());
        holder.tanggal.setText(ddk.getTanggal());
        holder.namabarang.setText(ddk.getNamaBarang());
        holder.jumlah.setText(ddk.getJumlah());
        String hrg = ddk.getHargaJual();
        int rp = Integer.parseInt(hrg);
        String torp = toRupiah(rp);
        holder.harga.setText(torp);

        String hrg1 = ddk.getSub_total();
        int rp1 = Integer.parseInt(hrg1);
        String torp1 = toRupiah(rp1);
        holder.sub_total.setText(torp1);
    }

    @Override
    public int getItemCount() {
        return  dataDetailTransaksi == null ? 0 : dataDetailTransaksi.size();
    }
    public class StatusHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idtrx, tanggal, namabarang, jumlah, harga, sub_total;
        ImageView gambar;

        public StatusHolderData(@NonNull View itemView){
            super(itemView);
            idtrx = itemView.findViewById(R.id.idtrx2);
            tanggal = itemView.findViewById(R.id.tanggal);
            namabarang = itemView.findViewById(R.id.cvhistory_nama);
            jumlah = itemView.findViewById(R.id.jumlah_riwayat);
            harga = itemView.findViewById(R.id.cvhistory_harga);
            sub_total = itemView.findViewById(R.id.cvcheckout_subtotal);
            gambar = itemView.findViewById(R.id.cvhistory_gambar);
        }

        @Override
        public void onClick(View view) {

        }
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
