package com.example.sembakomobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.HolderData>{

    private Context ctx;
    private List<DataBarang> dataBarangList;
    static AdapterBarang.adapterBarangListener adapterBarangListener;

    public interface adapterBarangListener{
        void selectedItemListener(int positionOfItemClicked);
    }

    public AdapterBarang(Context ctx, List<DataBarang> dataBarangList,
                        adapterBarangListener adapterBarangListener) {
        this.ctx = ctx;
        this.dataBarangList = dataBarangList;
        this.adapterBarangListener = adapterBarangListener;
    }

    @Override
    @NonNull
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem,parent,false);
        return new HolderData(v);
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
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataBarang dm = dataBarangList.get(position);
        Picasso.get()
                .load(dataBarangList.get(position).getGambar()).resize(512,512).centerCrop()
                .into(holder.img_button1);

        holder.nama_card1.setText(dm.getNamaBarang());
        String hrg = dm.getHargaJual();
        int rp = Integer.parseInt(hrg);
        String torp = toRupiah(rp);
        holder.harga_carditem1.setText(torp);

    }

    @Override
    public int getItemCount() {
        return dataBarangList == null ? 0 : dataBarangList.size();
    }

    public static class HolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageButton img_button1, btn_hapus;
        TextView nama_card1, harga_carditem1;
        CheckBox checkBox;
        LinearLayout id_layout_card_item;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            img_button1 = itemView.findViewById(R.id.img_btn1);
            nama_card1 = itemView.findViewById(R.id.nama_carditem1);
            harga_carditem1 = itemView.findViewById(R.id.harga_carditem1);
            btn_hapus = itemView.findViewById(R.id.cvcart_btdelete);
            checkBox = itemView.findViewById(R.id.checkbox_cart);
            id_layout_card_item = itemView.findViewById(R.id.id_layout_card_item);
            id_layout_card_item.setOnClickListener(this);
            img_button1.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            adapterBarangListener.selectedItemListener(getAdapterPosition());
        }
    }
}
