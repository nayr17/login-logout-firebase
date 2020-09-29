package com.example.loginandcreateaccountwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView showUser;
    private Button btnLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        showUser =(TextView)findViewById(R.id.textViewUser);
        btnLogOut =(Button)findViewById(R.id.btnLogoutUser);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        showUser.setText("Hi "+ user.getEmail());


        btnLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if(v == btnLogOut){
           firebaseAuth.signOut();
           finish();
           startActivity(new Intent(this, Login.class));
       }
    }
}