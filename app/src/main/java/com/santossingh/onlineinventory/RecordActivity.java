package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Models.Inventory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordActivity extends AppCompatActivity {

    @BindView(R.id.add) Button add;
    @BindView(R.id.productName) EditText product;
    @BindView(R.id.productCategory) EditText category;
    @BindView(R.id.quantity) EditText quantity;
    @BindView(R.id.price) EditText price;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, sellersReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellersReference=firebaseDatabase.getReference().child("sellers");
        databaseReference=firebaseDatabase.getReference().child("inventory");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

    }

    private void addProduct(){
        Inventory add = new Inventory(
                product.getText().toString(),
                quantity.getText().toString(),
                price.getText().toString(),null);
        databaseReference.push().setValue(add);
        clearUI();
        Toast.makeText(getApplicationContext(),"Added Record",Toast.LENGTH_LONG).show();
    }

    private void updateProduct(){
    }

    private void clearUI(){
        product.setText("");
        quantity.setText("");
        price.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
