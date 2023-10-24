package com.example.sembakomobile.Adapter;

import static com.example.sembakomobile.Adapter.AdapterCariBarang.toRupiah;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sembakomobile.Model.Retrofit.DataBarang;
import com.example.sembakomobile.Model.Retrofit.DataKeranjang;
import com.example.sembakomobile.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder>{
    private final AdapterCartClick itemClick;
    Context context;
    List<DataKeranjang> cartList;
    int total = 0;

    public AdapterCart(Context context, List<DataKeranjang> cartList, AdapterCartClick itemClick) {
        this.context = context;
        this.cartList = cartList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataKeranjang dm = cartList.get(position);
        Picasso.get()
                .load(cartList.get(position).getGambar()).resize(300,300).centerCrop()
                .into(holder.img_button);

        String hrg = dm.getHargaJual();
        int rp = Integer.parseInt(hrg);
        String torp = toRupiah(rp);
        holder.harga.setText(torp);
        holder.nama.setText(dm.getNamaBarang());
        holder.total.setText(dm.getHargaJual());
        holder.jumlah.setText(dm.getJumlah());
        total = Integer.parseInt(holder.jumlah.getText().toString()) * Integer.parseInt(dm.getHargaJual());
        System.out.println("totl"+total);
        String hasilConvert = toRupiah(total);
        holder.total.setText(hasilConvert);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    itemClick.onCheckBox(position,true);
                } else {
                    itemClick.onCheckBox(position,false);
                }
            }
        });
        holder.button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageButton img_button, button_del;
        TextView nama, harga, jumlah, total;
        CheckBox checkBox;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_button = itemView.findViewById(R.id.img_btn_cart);
            nama = itemView.findViewById(R.id.nama_produk_cart);
            harga = itemView.findViewById(R.id.harga_produk_cart);
            jumlah = itemView.findViewById(R.id.jumlah);
            total = itemView.findViewById(R.id.total_produk_cart);
            checkBox = itemView.findViewById(R.id.checkbox_cart);
            button_del = itemView.findViewById(R.id.cvcart_btdelete);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface AdapterCartClick{
        public void onDeleteClick(int position);
        public void onCheckBox(int position, boolean stattus);
        public void onCheckBoxNotSelected(int position);
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
