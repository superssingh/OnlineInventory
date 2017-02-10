package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Models.Inventory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, sellersReference;
    List<Inventory> inventoryList;
    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Inventory,RootViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("inventory");

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Inventory, RootViewHolder>(
                Inventory.class,R.layout.list_item,RootViewHolder.class,databaseReference
                ) {
            @Override
            protected void populateViewHolder(RootViewHolder viewHolder, Inventory model, int position) {
                viewHolder.product.setText(model.getItem());
                viewHolder.price.setText(model.getPrice());
                viewHolder.qty.setText(model.getQuantity());
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class RootViewHolder extends RecyclerView.ViewHolder {
        TextView product,qty,price;
        public RootViewHolder(View itemView) {
            super(itemView);
            product=(TextView) itemView.findViewById(R.id.R_product);
            qty= (TextView) itemView.findViewById(R.id.R_qty);
            price = (TextView) itemView.findViewById(R.id.R_price);
        }
    }

}
