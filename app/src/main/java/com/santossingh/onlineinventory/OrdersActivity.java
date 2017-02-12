package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper.OrdersRecycleAdapter;
import com.santossingh.onlineinventory.Models.Orders;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersActivity extends AppCompatActivity implements OrdersRecycleAdapter.GetDataFromAdapter {

    @BindView(R.id.orders_recycleView)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    OrdersRecycleAdapter recycleAdapter;
    DatabaseReference inventoryRef, ordersRef;
    AlertDialog dialog;
    List<Orders> ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        inventoryRef = FirebaseDatabase.getInstance().getReference().child("inventory");
        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders");
        ordersList = new ArrayList<>();

        ordersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Orders orders = dataSnapshot.getValue(Orders.class);
                orders.setKey(dataSnapshot.getKey());
                ordersList.add(0, orders);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (ordersList != null) {
                    ordersList.clear();
                }
                Orders orders = dataSnapshot.getValue(Orders.class);
                orders.setKey(dataSnapshot.getKey());
                ordersList.add(0, orders);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Orders orders = dataSnapshot.getValue(Orders.class);
                orders.setKey(dataSnapshot.getKey());
                ordersList.add(0, orders);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
