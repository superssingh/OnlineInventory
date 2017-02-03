package com.santossingh.onlineinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.Admin) Button admin;
    @BindView(R.id.Seller) Button seller;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, sellersReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellersReference=firebaseDatabase.getReference().child("sellers");
        databaseReference=firebaseDatabase.getReference().child("inventory");

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SellerActivity.class)
                        .putExtra("seller",username.getText());
                startActivity(intent);
            }
        });
    }
}
