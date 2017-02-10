package com.santossingh.onlineinventory;

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

public class UpdateActivity extends AppCompatActivity {

    @BindView(R.id.update)
    Button update;
    @BindView(R.id.productName)
    EditText product;
    @BindView(R.id.productCategory) EditText category;
    @BindView(R.id.quantity) EditText quantity;
    @BindView(R.id.price) EditText price;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, sellersReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellersReference=firebaseDatabase.getReference().child("sellers");
        databaseReference=firebaseDatabase.getReference().child("inventory");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });
    }

    private void updateProduct(){
        clearUI();
    }

    private void clearUI(){
        product.setText("");
        category.setText("");
        quantity.setText("");
        price.setText("");
    }
}
