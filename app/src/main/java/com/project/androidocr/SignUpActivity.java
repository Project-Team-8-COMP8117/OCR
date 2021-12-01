package com.project.androidocr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    Button signUp_btn;
    ProgressBar progressBar;
    EditText m_password;
    EditText m_password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fAuth = FirebaseAuth.getInstance();
        signUp_btn = findViewById(R.id.shivam_signup);
        progressBar = findViewById(R.id.loading);
        m_password = findViewById(R.id.password);
        m_password2 = findViewById(R.id.password2);


        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "123", Toast.LENGTH_LONG).show();

//                String name = findViewById(R.id.name).toString();
                String email = findViewById(R.id.username).toString();
                String password = findViewById(R.id.password).toString().trim();
                String confirmPassword = findViewById(R.id.password2).toString().trim();

                if(password!=confirmPassword) {
                    m_password.setError("Password does not match");
                    return;
                }

                if(password.length() < 6) {
                    m_password.setError("Password must be greater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Register the user in FireBase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}