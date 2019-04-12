package io.github.rahmatsyam.mitramasnote.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

import io.github.rahmatsyam.mitramasnote.R;
import io.github.rahmatsyam.mitramasnote.data.model.Note;
import io.github.rahmatsyam.mitramasnote.data.provider.DatabaseHelper;
import io.github.rahmatsyam.mitramasnote.ui.adapter.ItemAdapter;
import io.github.rahmatsyam.mitramasnote.ui.util.EmptyRecyclerView;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;

    FirebaseAuth mAuth;
    EmptyRecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Note> notes = new ArrayList<>();
    TextView tvNama;

    DatabaseHelper db = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerview);

        SQLiteDatabase database = db.getReadableDatabase();
        cursor = database.rawQuery("SELECT nama FROM user ", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            //  tvNama.setText(cursor.getString(0));
        }

        tvNama = findViewById(R.id.tv_nama);


        notes = db.getAll();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(this, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(findViewById(R.id.empty_view));

        findViewById(R.id.bt_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TambahActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = db.getAll();
        adapter = new ItemAdapter(this, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(findViewById(R.id.empty_view));

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MasukActivity.class));
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MasukActivity.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}
