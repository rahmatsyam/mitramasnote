package io.github.rahmatsyam.mitramasnote.ui.activity;

import android.content.Intent;
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


import io.github.rahmatsyam.mitramasnote.R;


public class MasukActivity extends AppCompatActivity {

    DatabaseReference dbUser;
    FirebaseAuth mAuth;
    EditText etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);


        mAuth = FirebaseAuth.getInstance();

        dbUser = FirebaseDatabase.getInstance().getReference().child("user");


        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);


        findViewById(R.id.btn_masuk).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                cekValidasi();

            }
        });

        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
            }
        });


    }

    private void cekValidasi() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                           // FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MasukActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }


}

