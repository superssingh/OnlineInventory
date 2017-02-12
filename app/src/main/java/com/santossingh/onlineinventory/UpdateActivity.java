package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Models.Inventory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateActivity extends AppCompatActivity {

    @BindView(R.id.update)
    Button update;
    @BindView(R.id.productName)
    EditText product;
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

        final Bundle bundle = getIntent().getExtras();

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellersReference=firebaseDatabase.getReference().child("sellers");
        databaseReference=firebaseDatabase.getReference().child("inventory");

        final String key = bundle.getString("KEY");
        product.setText(bundle.getString("PRODUCT"));
        quantity.setText(bundle.getString("QTY"));
        price.setText(bundle.getString("PRICE"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct(key);
            }
        });
    }

    private void updateProduct(String key) {
        Inventory inventory = new Inventory();
        inventory.setItem(product.getText().toString());
        inventory.setQuantity(quantity.getText().toString());
        inventory.setPrice(price.getText().toString());
        databaseReference.child(key).setValue(inventory);
        clearUI();
        this.finish();
    }

    private void clearUI(){
        product.setText("");
        quantity.setText("");
        price.setText("");
    }
}
