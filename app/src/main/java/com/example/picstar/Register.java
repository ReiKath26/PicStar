package com.example.picstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView lgn_btn;

    private ProgressBar progressBar;
    private EditText et_name, et_email, et_pass, et_confirm;
    private Button btn_regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        lgn_btn = findViewById(R.id.login_btn);

        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        progressBar = findViewById(R.id.regis_progressBar);
        et_name = findViewById(R.id.editTextName);
        et_email = findViewById(R.id.editTextEmail);
        et_pass = findViewById(R.id.editTextPassword);
        et_confirm = findViewById(R.id.editTextCheck);
        btn_regis = findViewById(R.id.btn_register);

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        String name = et_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        String confirm = et_confirm.getText().toString().trim();
        String status = "Free";

        if(name.isEmpty() || name.length() < 5 )
        {
            et_name.setError("Name needs to be 5 or more character");
            et_name.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            et_email.setError("Email should be filled");
            et_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_email.setError("Email must be a valid email address");
            et_email.requestFocus();
            return;
        }

        if(pass.isEmpty() || pass.length() < 8)
        {
            et_pass.setError("Password must be 8 character or more");
            et_pass.requestFocus();
            return;
        }

        if(!confirm.equals(pass))
        {
            et_confirm.setError("Password doesn't match");
            et_confirm.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(name, email, status);

                    FirebaseDatabase.getInstance("https://picstar-db-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Register.this, "User has been registered", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);

                            }

                            else
                            {
                                Toast.makeText(Register.this, "Register failed! Try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }

                else
                {
                    Toast.makeText(Register.this, "Register failed! Try again later", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}