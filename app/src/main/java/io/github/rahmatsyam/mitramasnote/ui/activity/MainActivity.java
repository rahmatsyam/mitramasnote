package io.github.rahmatsyam.mitramasnote.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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

    DatabaseHelper db = null;
    Cursor cursor = null;

    FirebaseAuth mAuth;

    EmptyRecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DividerItemDecoration itemDecoration;
    LinearLayoutManager layoutManager;

    List<Note> notes = new ArrayList<>();

    TextView tvNama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelper(this);


        setNama();

        notes = db.getAll();

        setRecyclerView();


        signOut();

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

    @SuppressLint("SetTextI18n")
    private void setNama() {
        tvNama = findViewById(R.id.tv_nama);
        String nama = "";
        SQLiteDatabase database = db.getReadableDatabase();
        cursor = database.rawQuery("SELECT id, nama FROM user", null);
        if (cursor.moveToFirst()) {
            for (; !cursor.isAfterLast();
                 cursor.moveToNext()) {
                nama = cursor.getString(1);
            }
        }
        tvNama.setText("Hi" + " " + nama + " " + "!");
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(this, notes);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(findViewById(R.id.empty_view));
    }

    private void signOut() {
        findViewById(R.id.bt_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want sign out?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), MasukActivity.class));
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}
