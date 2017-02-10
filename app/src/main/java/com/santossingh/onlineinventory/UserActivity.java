package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Adapter.MyAdapter;
import com.santossingh.onlineinventory.Models.Inventory;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private List<Inventory> inventoryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, sellersReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("inventory");

        recyclerView = (RecyclerView) findViewById(R.id.user_recycleview);
        mAdapter = new MyAdapter(inventoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Inventory inventory=data.getValue(Inventory.class);
                    Log.i("Data",inventory.getItem());
                    Log.i("Data",inventory.getPrice());
                    inventoryList.add(inventory);
                    Log.i("List",inventoryList.toString());

                }

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

        recyclerView.setAdapter(mAdapter);
    }



}