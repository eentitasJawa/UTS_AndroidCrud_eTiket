package com.example.etiketkereta;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    RecyclerView recyclerView;
    BookingAdapter adapter;
    ArrayList<TiketModel> tiketList;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());
        tiketList = new ArrayList<>();

        loadData();

        adapter = new BookingAdapter(getContext(), tiketList, dbHelper);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadData() {
        tiketList.clear();
        Cursor cursor = dbHelper.getAllBookings();
        if (cursor.moveToFirst()) {
            do {
                tiketList.add(new TiketModel(
                        cursor.getInt(0),   // id
                        cursor.getString(1),// nama
                        cursor.getString(2),// tujuan
                        cursor.getString(3),// tanggal
                        cursor.getInt(4),   // jumlah
                        cursor.getInt(5),   // harga
                        cursor.getInt(6)    // bayar (✅ ganti dari getString ke getInt)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
