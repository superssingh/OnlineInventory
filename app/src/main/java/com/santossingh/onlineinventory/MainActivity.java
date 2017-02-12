package com.santossingh.onlineinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Activities.AdminActivity;
import com.santossingh.onlineinventory.Models.Admins;
import com.santossingh.onlineinventory.Models.Sellers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santossingh.onlineinventory.R.id.Admin;
import static com.santossingh.onlineinventory.R.id.username;

public class MainActivity extends AppCompatActivity {
    @BindView(username)
    EditText Musername;
    @BindView(R.id.password)
    EditText Mpassword;
    @BindView(Admin)
    Button admin;
    @BindView(R.id.Seller) Button seller;
    List<Sellers> sellerses;
    Admins admins;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference adminRef, sellerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellerRef = firebaseDatabase.getReference().child("sellers");
        adminRef = firebaseDatabase.getReference().child("admin");
        sellerses = new ArrayList<>();
        admins = new Admins();
        sellerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Sellers sellers = dataSnapshot.getValue(Sellers.class);
                sellers.setKey(dataSnapshot.getKey());
                sellerses.add(sellers);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        adminRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Admins admin = dataSnapshot.getValue(Admins.class);
                admin.setKey(dataSnapshot.getKey());
                admins = admin;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_Admin(admins);
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_User(sellerses);
            }
        });
    }


    public void login_Admin(final Admins admins) {
        if (!Musername.getText().toString().isEmpty() || !Mpassword.getText().toString().isEmpty()) {

            if (Musername.getText().toString().equals(admins.getMobile()) && Mpassword.getText().toString().equals(admins.getPassword())) {
                Toast.makeText(MainActivity.this, "Welcome Admin.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Sorry, wrong mobile number or password.", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(MainActivity.this, "Please fill required fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void login_User(final List<Sellers> sellerses) {

        if (!Musername.getText().toString().isEmpty() || !Mpassword.getText().toString().isEmpty()) {
            for (Sellers seller : sellerses) {
                if (Musername.getText().toString().equals(seller.getMobile()) && Mpassword.getText().toString().equals(seller.getPassword())) {
                    Toast.makeText(MainActivity.this, "Welcome Mr. " + seller.getUsername(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, SellerActivity.class)
                            .putExtra("USERNAME", seller.getUsername())
                            .putExtra("MOBILE", seller.getMobile());
                    startActivity(intent);
                }
            }

        } else {
            Toast.makeText(MainActivity.this, "Please fill required fields.", Toast.LENGTH_LONG).show();
        }
    }

}
