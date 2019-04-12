package io.github.rahmatsyam.mitramasnote.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.github.rahmatsyam.mitramasnote.R;
import io.github.rahmatsyam.mitramasnote.data.model.Note;
import io.github.rahmatsyam.mitramasnote.data.provider.DatabaseHelper;

public class TambahActivity extends AppCompatActivity {

    DatabaseHelper db = null;
    EditText etTambahAktivitas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);


        etTambahAktivitas = findViewById(R.id.et_aktivitas);


        findViewById(R.id.btn_tambah_aktivitas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahAktivitas();

            }
        });

    }

    private void tambahAktivitas() {

        String aktivitas = etTambahAktivitas.getText().toString();
        String date = new SimpleDateFormat("d/M/yyyy - HH:mm", Locale.getDefault()).format(new Date());

        if (aktivitas.equals("")) {
            Toast.makeText(getApplicationContext(), "Field tak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            db = new DatabaseHelper(this);
            db.addAktivitas(new Note(0,aktivitas, date));

            if (db != null) {
                Toast.makeText(getApplicationContext(), "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                etTambahAktivitas.getText().clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1200);
            } else {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
