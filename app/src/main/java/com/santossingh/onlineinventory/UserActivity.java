package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper.UserRecycleAdapter;
import com.santossingh.onlineinventory.Models.Sellers;

public class UserActivity extends AppCompatActivity implements UserRecycleAdapter.GetDataFromAdapter {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference sellersReference;
    Sellers sellers;
    UserRecycleAdapter userRecycleAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseDatabase=FirebaseDatabase.getInstance();
        sellersReference = FirebaseDatabase.getInstance().getReference().child("sellers");
        sellers = new Sellers();
        userRecycleAdapter = new UserRecycleAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.user_recycleview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userRecycleAdapter);
    }

    @Override
    public void onCurrentUser(Sellers seller) {
        sellers = seller;
        removeData(seller);
    }

    private void removeData(Sellers sellers) {
        Toast.makeText(this, sellers.getMobile(), Toast.LENGTH_LONG).show();
        sellersReference.child(sellers.getKey()).removeValue();
        this.finish();

    }

}