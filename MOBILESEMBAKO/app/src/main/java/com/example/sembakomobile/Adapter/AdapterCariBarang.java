package com.example.sembakomobile.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sembakomobile.Activity.detail_barang;
import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterCariBarang extends RecyclerView.Adapter<AdapterCariBarang.ViewHolder> {
    private Context ctx;
    private List<DataBarang> cariBarangList;
    static  AdapterCariBarang.adapterBarangListener mListenerAdapter;

    public interface adapterBarangListener{
        void selectedItemListener(int positionOfItemClicked);

    }

    public AdapterCariBarang(Context ctx, List<DataBarang> dataBarangList, AdapterCariBarang.adapterBarangListener listener) {
        this.ctx = ctx;
        this.cariBarangList = dataBarangList;
        this.mListenerAdapter = listener;
    }

    @Override
    @NonNull
    public AdapterCariBarang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem2,parent,false);
        return new AdapterCariBarang.ViewHolder(v);
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

    @Override
    public void onBindViewHolder(@NonNull AdapterCariBarang.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataBarang dm = cariBarangList.get(position);
        Picasso.get()
                .load(cariBarangList.get(position).getGambar()).resize(300,300).centerCrop()
                .into(holder.img_button1);

        String hrg = dm.getHargaJual();
        int rp = Integer.parseInt(hrg);
        String torp = toRupiah(rp);
        holder.harga_carditem1.setText(torp);

        holder.nama_card1.setText(dm.getNamaBarang());
        holder.stok.setText(dm.getStok());
    }

    @Override
    public int getItemCount() {
        return cariBarangList == null ? 0 : cariBarangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {
        ImageButton img_button1;
        TextView nama_card1, harga_carditem1, stok;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_button1 = itemView.findViewById(R.id.img_btn_cart);
            nama_card1 = itemView.findViewById(R.id.nama_produk_cart);
            harga_carditem1 = itemView.findViewById(R.id.harga_produk_cart);
            stok = itemView.findViewById(R.id.stok_produk_cart);
            constraintLayout = itemView.findViewById(R.id.id_wrap_cari_barang);
            img_button1.setOnClickListener(this);
            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListenerAdapter.selectedItemListener(getAdapterPosition());
        }
    }
}
