package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper.OrdersRecycleAdapter;
import com.santossingh.onlineinventory.Models.Orders;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersActivity extends AppCompatActivity implements OrdersRecycleAdapter.GetDataFromAdapter {

    @BindView(R.id.orders_recycleView)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    OrdersRecycleAdapter recycleAdapter;
    DatabaseReference inventoryRef, ordersRef;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        inventoryRef = FirebaseDatabase.getInstance().getReference().child("inventory");
        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders");

        recycleAdapter = new OrdersRecycleAdapter(OrdersActivity.this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public void onCurrentMovie(Orders currentData) {
        Toast.makeText(this, currentData.getBuyer(), Toast.LENGTH_LONG).show();
    }
}
