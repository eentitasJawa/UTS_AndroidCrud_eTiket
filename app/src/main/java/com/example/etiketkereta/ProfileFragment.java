package com.example.etiketkereta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvUser = view.findViewById(R.id.tvUsername);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        SharedPreferences sp = getActivity().getSharedPreferences("session", getContext().MODE_PRIVATE);
        String username = sp.getString("username", "User");
        tvUser.setText("Username: " + username);

        btnLogout.setOnClickListener(v -> {
            sp.edit().clear().apply();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finishAffinity();
        });

        return view;
    }
}
