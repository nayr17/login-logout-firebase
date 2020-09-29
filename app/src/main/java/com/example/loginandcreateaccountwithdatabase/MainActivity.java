package com.example.loginandcreateaccountwithdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private Button btnReg;
    private TextView viewLogin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance(); //initialize object to begin registering account into firebase

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }

        email =(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        viewLogin =(TextView)findViewById(R.id.textViewLogin);
        progressDialog = new ProgressDialog(this);

        btnReg = (Button)findViewById(R.id.btnRegUser);

        btnReg.setOnClickListener(this);
        viewLogin.setOnClickListener(this);


    }

    private void registerUser(){
        String getUseremail = email.getText().toString().trim();
        String getUserpass = password.getText().toString().trim();

        if(TextUtils.isEmpty(getUseremail)){
            //if username is empty
            Toast.makeText(this, "email is empty!", Toast.LENGTH_SHORT).show();
            //break statement
            return;
        }
        if (TextUtils.isEmpty(getUserpass)){
            //if password is empty
            Toast.makeText(this, "password is empty!", Toast.LENGTH_SHORT).show();
            //break statement
            return;
        }
        //if user input is not empty

        //show progress bar
        progressDialog.setMessage("Registering User....");
        progressDialog.show();

        // this listener executes this method on the registration
        firebaseAuth.createUserWithEmailAndPassword(getUseremail,getUserpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "could not register! please try again",
                            Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == btnReg){
            registerUser();
        }
        if(v == viewLogin){
            startActivity(new Intent(this, Login.class));
        }

    }
}