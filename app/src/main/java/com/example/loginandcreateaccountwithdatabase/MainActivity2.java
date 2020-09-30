package com.example.loginandcreateaccountwithdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView showUser;
    private Button btnLogOut;
    private DatabaseReference databaseReference;
    private EditText getUserfullname, getUseraddress;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        showUser =(TextView)findViewById(R.id.textViewUser);
        btnLogOut =(Button)findViewById(R.id.btnLogoutUser);
        getUserfullname =(EditText)findViewById(R.id.userFullname);
        getUseraddress =(EditText)findViewById(R.id.userAddress);
        btnSave =(Button)findViewById(R.id.btnSaveInfo);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        showUser.setText("Hi "+ user.getEmail());

        btnLogOut.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    private void saveUserInfo(){
        String name = getUserfullname.getText().toString().trim();
        String address = getUseraddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,address);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation); //get unique id
        Toast.makeText(this, "Information Save...", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
       if(v == btnLogOut){
           firebaseAuth.signOut();
           finish();
           startActivity(new Intent(this, Login.class));
       }
       if(v == btnSave){
           saveUserInfo();
       }
    }
}