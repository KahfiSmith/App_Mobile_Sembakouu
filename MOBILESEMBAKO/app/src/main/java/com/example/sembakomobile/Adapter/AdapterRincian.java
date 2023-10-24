package com.example.sembakomobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterRincian extends RecyclerView.Adapter<AdapterRincian.CheckoutHolderData>{

    int total = 0;
    private Context ctx;
    private List<DataBarang> dataCheckout;

    public AdapterRincian(Context ctx, List<DataBarang> dataCheckout) {
        this.ctx = ctx;
        this.dataCheckout = dataCheckout;
    }

    @NonNull
    @Override
    public CheckoutHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.cv_rincian, parent, false);
        return new CheckoutHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutHolderData holder, int position) {
        DataBarang dm = dataCheckout.get(position);
        Picasso.get()
                .load(dataCheckout.get(position).getGambar()).resize(300,300).centerCrop()
                .into(holder.img_button);
        String hrg = dm.getHargaJual();
        int rp = Integer.parseInt(hrg);
        String torp = toRupiah(rp);
        System.out.println("Gson = " + new Gson().toJson(dm));
        holder.harga.setText(torp);
        holder.nama.setText(dm.getNamaBarang());
        holder.total.setText(toRupiah(Integer.parseInt(dm.getSub_total())));
        holder.jumlah.setText(dm.getJumlah());
    }


    @Override
    public int getItemCount() {
        return dataCheckout.size();
    }

    public class CheckoutHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_button;
        TextView nama, harga, jumlah, total;

        public CheckoutHolderData(@NonNull View itemView){
            super(itemView);
            img_button = itemView.findViewById(R.id.gambar_rincian);
            nama = itemView.findViewById(R.id.nama_rincian);
            harga = itemView.findViewById(R.id.harga_rincian);
            jumlah = itemView.findViewById(R.id.jumlah_rincian);
            total = itemView.findViewById(R.id.subtotal_rincian);
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
