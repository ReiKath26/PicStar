package com.example.picstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResultCode;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

public class Login extends AppCompatActivity {

    TextView regist;

    private HuaweiIdAuthParams authParams;
    private HuaweiIdAuthService service;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText et_email, et_password;
    private Button btn_log, btn_huaweii;

    private static final int  HUAWEI_AUTH_CODE= 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        regist = findViewById(R.id.register_btn);
        et_email = findViewById(R.id.email_et);
        et_password = findViewById(R.id.password_et);
        progressBar = findViewById(R.id.progress_bar);
        btn_log = findViewById(R.id.login_account);
        btn_huaweii = findViewById(R.id.login_huawei);

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
            }
        });

        btn_huaweii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huaweiilogin();
            }
        });


        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void huaweiilogin() {
        authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams();
        service = HuaweiIdAuthManager.getService(Login.this, authParams);

        com.huawei.hmf.tasks.Task<AuthHuaweiId> task = service.silentSignIn();

        task.addOnSuccessListener((authHuaweiId) -> {
            String displayName = authHuaweiId.getDisplayName();
            Toast.makeText(Login.this, "Silent Sign in successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Home_loggedin.class);
            intent.putExtra("Flag", "Huawei");
            intent.putExtra("Username", displayName);
            startActivity(intent);
        });

        task.addOnFailureListener((e) ->{
            startActivityForResult(service.getSignInIntent(), HUAWEI_AUTH_CODE);
        });
    }

    private void loginAccount() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(email.isEmpty())
        {
            et_email.setError("Email must be filled");
            et_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_email.setError("Email must be a valid email");
            et_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            et_password.setError("Password must be filled");
            et_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(Login.this, Home_loggedin.class);
                    intent.putExtra("Flag", "Email");
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(Login.this, "Login failed! Please check your credentials!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}