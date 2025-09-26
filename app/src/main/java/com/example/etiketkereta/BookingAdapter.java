package com.example.etiketkereta;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TiketModel> tiketList;
    private DBHelper dbHelper;

    public BookingAdapter(Context context, ArrayList<TiketModel> tiketList, DBHelper dbHelper) {
        this.context = context;
        this.tiketList = tiketList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tiket, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TiketModel tiket = tiketList.get(position);

        String status;
        if (tiket.getBayar() >= tiket.getHarga()) {
            status = "Lunas (Kembalian Rp" + (tiket.getBayar() - tiket.getHarga()) + ")";
        } else {
            status = "Kurang Rp" + (tiket.getHarga() - tiket.getBayar());
        }

        holder.tvInfo.setText("Nama: " + tiket.getNama()
                + "\nTujuan: " + tiket.getTujuan()
                + "\nTanggal: " + tiket.getTanggal()
                + "\nJumlah: " + tiket.getJumlah()
                + "\nTotal: Rp" + tiket.getHarga()
                + "\nDibayar: Rp" + tiket.getBayar()
                + "\nStatus: " + status);

        holder.btnEdit.setOnClickListener(v -> showEditDialog(tiket, position));
        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteBooking(tiket.getId());
            tiketList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tiketList.size());
            Toast.makeText(context, "Pesanan dihapus", Toast.LENGTH_SHORT).show();
        });
    }

    private void showEditDialog(TiketModel tiket, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Tiket");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
        EditText etNama = dialogView.findViewById(R.id.etEditNama);
        EditText etTujuan = dialogView.findViewById(R.id.etEditTujuan);
        EditText etTanggal = dialogView.findViewById(R.id.etEditTanggal);
        EditText etJumlah = dialogView.findViewById(R.id.etEditJumlah);
        EditText etBayar = dialogView.findViewById(R.id.etEditBayar);

        etNama.setText(tiket.getNama());
        etTujuan.setText(tiket.getTujuan());
        etTanggal.setText(tiket.getTanggal());
        etJumlah.setText(String.valueOf(tiket.getJumlah()));
        etBayar.setText(String.valueOf(tiket.getBayar()));

        builder.setView(dialogView);
        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String nama = etNama.getText().toString();
            String tujuan = etTujuan.getText().toString();
            String tanggal = etTanggal.getText().toString();
            int jumlah = Integer.parseInt(etJumlah.getText().toString());
            int bayar = Integer.parseInt(etBayar.getText().toString());

            int harga = tiket.getHarga() / tiket.getJumlah() * jumlah;

            dbHelper.updateBooking(tiket.getId(), nama, tujuan, tanggal, jumlah, harga, bayar);

            tiket.setNama(nama);
            tiket.setTujuan(tujuan);
            tiket.setTanggal(tanggal);
            tiket.setJumlah(jumlah);
            tiket.setHarga(harga);
            tiket.setBayar(bayar);

            notifyItemChanged(position);
            Toast.makeText(context, "Pesanan diupdate", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return tiketList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
