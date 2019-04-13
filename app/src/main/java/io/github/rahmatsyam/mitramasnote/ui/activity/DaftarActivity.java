package io.github.rahmatsyam.mitramasnote.ui.activity;


import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.github.rahmatsyam.mitramasnote.R;
import io.github.rahmatsyam.mitramasnote.data.model.User;
import io.github.rahmatsyam.mitramasnote.data.provider.DatabaseHelper;

public class DaftarActivity extends AppCompatActivity {

    DatabaseReference dbUser;
    EditText etNama, etEmail, etPassword;

    FirebaseAuth mAuth;
    DatabaseHelper db = null;

    List<User> mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mAuth = FirebaseAuth.getInstance();
        dbUser = FirebaseDatabase.getInstance().getReference("user");


        etNama = findViewById(R.id.et_nama);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        mUser = new ArrayList<>();

        findViewById(R.id.btn_daftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MasukActivity.class));
                finish();
            }
        });

    }


    private void createUser() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (nama.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Field tak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            db = new DatabaseHelper(this);
            db.addNama(new User(nama));
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), "User terdaftar", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1200);
                            }
                        }
                    });
        }

    }



}
