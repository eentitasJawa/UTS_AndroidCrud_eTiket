package com.example.etiketkereta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class BookingFragment extends Fragment {
    EditText etNama, etTanggal, etJumlah, etBayar;
    Spinner spTujuan;
    Button btnPesan;
    TextView tvHasil;
    DBHelper dbHelper;

    String[] tujuanList = {"Jakarta - Jogja/Rp 450.000/tiket", "Jakarta - Surabaya/Rp 600.000/tiket", "Jakarta - Bandung/Rp 300.000/tiket"};
    int[] hargaList = {450000, 600000, 00000};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        etNama = view.findViewById(R.id.etNama);
        spTujuan = view.findViewById(R.id.spTujuan);
        etTanggal = view.findViewById(R.id.etTanggal);
        etJumlah = view.findViewById(R.id.etJumlah);
        etBayar = view.findViewById(R.id.etBayar);
        btnPesan = view.findViewById(R.id.btnPesan);
        tvHasil = view.findViewById(R.id.tvHasil);

        dbHelper = new DBHelper(getContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, tujuanList);
        spTujuan.setAdapter(adapter);

        btnPesan.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String tanggal = etTanggal.getText().toString();
            String jumlahStr = etJumlah.getText().toString();
            String bayarStr = etBayar.getText().toString();
            int pos = spTujuan.getSelectedItemPosition();

            if (nama.isEmpty() || tanggal.isEmpty() || jumlahStr.isEmpty() || bayarStr.isEmpty()) {
                tvHasil.setText("Lengkapi semua data!");
            } else {
                int jumlah = Integer.parseInt(jumlahStr);
                int harga = hargaList[pos] * jumlah;
                int bayar = Integer.parseInt(bayarStr);
                String tujuan = tujuanList[pos];

                dbHelper.addBooking(nama, tujuan, tanggal, jumlah, harga, bayar);

                if (bayar >= harga) {
                    int kembalian = bayar - harga;
                    tvHasil.setText("Pesanan tersimpan!\n"
                            + "Nama: " + nama
                            + "\nTujuan: " + tujuan
                            + "\nTanggal: " + tanggal
                            + "\nJumlah: " + jumlah
                            + "\nTotal Harga: Rp" + harga
                            + "\nDibayar: Rp" + bayar
                            + "\nKembalian: Rp" + kembalian);
                } else {
                    int kurang = harga - bayar;
                    tvHasil.setText("Uang kurang Rp" + kurang + "!\nTotal: Rp" + harga + "\nDibayar: Rp" + bayar);
                }
            }
        });

        return view;
    }
}
