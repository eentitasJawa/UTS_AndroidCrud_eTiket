package com.example.etiketkereta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText etUser, etPass;
    Button btnRegister;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnRegister = findViewById(R.id.btnRegister);
        userDAO = new UserDAO(this);

        btnRegister.setOnClickListener(v -> {
            String user = etUser.getText().toString();
            String pass = etPass.getText().toString();

            if (userDAO.register(user, pass)) {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registrasi gagal, username sudah ada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
