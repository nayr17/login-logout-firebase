package com.example.loginandcreateaccountwithdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private Button btnLogin;
    private TextView viewCreateUser;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }
        email =(EditText)findViewById(R.id.userEmail);
        password =(EditText)findViewById(R.id.userPass);
        btnLogin =(Button)findViewById(R.id.btnLoginUser);
        viewCreateUser =(TextView)findViewById(R.id.textViewCreateuser);
        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        viewCreateUser.setOnClickListener(this);
    }

    private void UserLogin(){
        String getUserEmail = email.getText().toString().trim();
        String getUserPass =password.getText().toString().trim();

        if(TextUtils.isEmpty(getUserEmail)){
            //if username is empty
            Toast.makeText(this, "email is empty!", Toast.LENGTH_SHORT).show();
            //break statement
            return;
        }
        if (TextUtils.isEmpty(getUserPass)){
            //if password is empty
            Toast.makeText(this, "password is empty!", Toast.LENGTH_SHORT).show();
            //break statement
            return;
        }
        //if user input is not empty

        //show progress bar
        progressDialog.setMessage("Logging in....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(getUserEmail,getUserPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                }
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            UserLogin();
        }
        if(v == viewCreateUser){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}