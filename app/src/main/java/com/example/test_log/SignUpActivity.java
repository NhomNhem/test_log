package com.example.test_log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edt_signUpEmail, edt_signUpPassword;
    private Button btn_signUp;
    private TextView login_redirect_TV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        edt_signUpEmail = findViewById(R.id.signup_email);
        edt_signUpPassword = findViewById(R.id.signup_password);
        btn_signUp = findViewById(R.id.signup_button);
        login_redirect_TV = findViewById(R.id.loginRedirectText);


        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_signUpEmail.getText().toString().trim();
                String pass = edt_signUpPassword.getText().toString().trim();

                if (user.isEmpty())
                    edt_signUpEmail.setError("Email cannot be empty");
                if (pass.isEmpty())
                    edt_signUpPassword.setError("Password cannot be empty");
                else {
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this,"Sign Up succesful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            }else {
                                Toast.makeText(SignUpActivity.this,"Sign Up Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


        login_redirect_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });

    }
}