package com.santossingh.onlineinventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper.RecycleAdapter;
import com.santossingh.onlineinventory.Models.Inventory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity implements RecycleAdapter.GetDataFromAdapter{

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, sellersReference;
    LinearLayoutManager linearLayoutManager;
    RecycleAdapter recycleAdapter;

    Inventory inventory;
    List<Inventory> inventoryList;

//    FirebaseRecyclerAdapter<Inventory,RootViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("inventory");
        recycleAdapter=new RecycleAdapter(this);
        inventory=new Inventory();
        inventoryList=new ArrayList<Inventory>();
        recycleAdapter.addDataSnapshot(databaseReference);

//        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Inventory, RootViewHolder>(
//                Inventory.class,
//                R.layout.list_item,
//                RootViewHolder.class,
//                databaseReference
//                ) {
//            @Override
//            protected void populateViewHolder(RootViewHolder viewHolder, Inventory model, int position)  {
//                viewHolder.product.setText(model.getItem());
//                viewHolder.price.setText(model.getPrice());
//                viewHolder.qty.setText(model.getQuantity());
//            }
//        };


        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recycleAdapter);
//        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

//    // Add @Keep in RootViewHolder class
//    @Keep
//    public static class RootViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView product,qty,price;
//        public RootViewHolder(View itemView) {
//            super(itemView);
//            product=(TextView) itemView.findViewById(R.id.R_product);
//            qty= (TextView) itemView.findViewById(R.id.R_qty);
//            price = (TextView) itemView.findViewById(R.id.R_price);
//        }
//
//        @Override
//        public void onClick(View view) {
//            int position=getAdapterPosition();
//
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCurrentMovie(Inventory currentData) {
        Toast.makeText(getApplicationContext(),currentData.getItem(),Toast.LENGTH_LONG).show();
    }
}
