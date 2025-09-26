package com.example.etiketkereta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {
    private Context context;
    private ArrayList<JadwalModel> jadwalList;

    public JadwalAdapter(Context context, ArrayList<JadwalModel> jadwalList) {
        this.context = context;
        this.jadwalList = jadwalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jadwal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JadwalModel jadwal = jadwalList.get(position);
        holder.tvInfo.setText("Asal: " + jadwal.getAsal()
                + "\nTujuan: " + jadwal.getTujuan()
                + "\nJam: " + jadwal.getJam()
                + "\nHarga: Rp" + jadwal.getHarga());
    }

    @Override
    public int getItemCount() {
        return jadwalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tvInfo);
        }
    }
}
