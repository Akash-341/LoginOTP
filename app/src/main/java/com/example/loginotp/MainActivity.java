package com.example.loginotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText edtmobile;
    Button btngetotp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtmobile = findViewById(R.id.edtmobile);
        btngetotp = findViewById(R.id.btngetotp);
         progressBar = findViewById(R.id.progressbar);

        btngetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtmobile.getText().toString().trim().isEmpty()) {
                    if ((edtmobile.getText().toString().trim()).length() == 10) {
                        progressBar.setVisibility(view.VISIBLE);
                        btngetotp.setVisibility(view.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + edtmobile.getText().toString(), 60,
                                TimeUnit.SECONDS, MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        btngetotp.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        btngetotp.setVisibility(View.INVISIBLE);
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        btngetotp.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(getApplicationContext(),Verify.class);
                                        intent.putExtra("mobile", edtmobile.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);
                                    }
                                });


                    } else {
                        Toast.makeText(MainActivity.this, "Please enter correct no", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}