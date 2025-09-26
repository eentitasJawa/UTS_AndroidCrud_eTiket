package com.example.etiketkereta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {
    RecyclerView rvJadwal;
    EditText etSearch;
    JadwalAdapter adapter;
    ArrayList<JadwalModel> jadwalList, filteredList;

    String[] asalList = {"Jakarta", "Bandung", "Jogja", "Surabaya"};
    String[] tujuanList = {"Jogja", "Surabaya", "Bandung", "Jakarta"};
    String[] jamList = {"08:00", "10:30", "13:00", "15:45", "19:00"};
    int[] hargaList = {300000, 450000, 500000, 600000, 750000};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvJadwal = view.findViewById(R.id.rvJadwal);
        etSearch = view.findViewById(R.id.etSearch);

        rvJadwal.setLayoutManager(new LinearLayoutManager(getContext()));

        jadwalList = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Buat jadwal random
        for (int i = 0; i < 10; i++) {
            String asal = asalList[(int) (Math.random() * asalList.length)];
            String tujuan = tujuanList[(int) (Math.random() * tujuanList.length)];
            String jam = jamList[(int) (Math.random() * jamList.length)];
            int harga = hargaList[(int) (Math.random() * hargaList.length)];

            jadwalList.add(new JadwalModel(asal, tujuan, jam, harga));
        }

        filteredList.addAll(jadwalList);
        adapter = new JadwalAdapter(getContext(), filteredList);
        rvJadwal.setAdapter(adapter);

        // Search filter
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void filter(String text) {
        filteredList.clear();
        for (JadwalModel jadwal : jadwalList) {
            if (jadwal.getAsal().toLowerCase().contains(text.toLowerCase())
                    || jadwal.getTujuan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(jadwal);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
