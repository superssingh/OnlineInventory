package com.santossingh.onlineinventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellersReference=firebaseDatabase.getReference().child("sellers");

        alert = new AlertDialog.Builder(this);


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

        alertbox();

    }


    private void alertbox(){
        final EditText edittext = new EditText(getApplicationContext());
        final EditText edittext1 = new EditText(getApplicationContext());
        alert.setMessage("Book Product");
        alert.setTitle("Order");

        alert.setView(edittext);
        alert.setView(edittext1);

        alert.setPositiveButton("Book Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                Editable YouEditTextValue = edittext.getText();
                //OR
//                String YouEditTextValue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();

    }

}
