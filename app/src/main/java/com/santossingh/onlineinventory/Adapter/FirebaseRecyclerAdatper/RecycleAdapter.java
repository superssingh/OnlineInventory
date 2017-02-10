package com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santossingh.onlineinventory.Models.Inventory;
import com.santossingh.onlineinventory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshsingh on 10/02/17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder>{

    private List<Inventory> inventories =new ArrayList<Inventory>();
    private Inventory currentData;
    private View rcView;
    private GetDataFromAdapter callback;
    DatabaseReference databaseReference;

    public RecycleAdapter( GetDataFromAdapter callback) {
        this.callback = callback;
    }

    // create View object and pass it on Holder class constructor
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        rcView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new Holder(rcView, callback);
    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Inventory current = inventories.get(position);
        holder.product.setText(current.getItem());
        holder.qty.setText(current.getQuantity());
        holder.price.setText(current.getPrice());
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView product, qty, price;
        private GetDataFromAdapter callback;
        public Holder(View itemView, GetDataFromAdapter callback) {
            super(itemView);
            this.callback=callback;
            itemView.setOnClickListener(this);
            product=(TextView) itemView.findViewById(R.id.R_product);
            qty=(TextView)itemView.findViewById(R.id.R_qty);
            price=(TextView)itemView.findViewById(R.id.R_price);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            currentData = inventories.get(position);
            callback.onCurrentMovie(currentData);
        }
    }

    public interface GetDataFromAdapter {
        void onCurrentMovie(Inventory currentData);
    }

    public void addInventoryList(List<Inventory> inventoryList){
        inventories =inventoryList;
        notifyDataSetChanged();
    }

    public void addDataSnapshot(DatabaseReference databaseReference){
        this.databaseReference=databaseReference;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Inventory inventory=ds.getValue(Inventory.class);
                    inventories.add(inventory);
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Opration Canceled: ","Database Error "+databaseError.getMessage());
            }
        });

    }
}
