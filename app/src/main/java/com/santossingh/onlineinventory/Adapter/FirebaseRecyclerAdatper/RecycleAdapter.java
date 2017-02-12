package com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Models.Inventory;
import com.santossingh.onlineinventory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshsingh on 10/02/17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder>{

    Dialog dialog;
    private List<Inventory> inventories;
    private Inventory currentData;
    private View rcView;
    private GetDataFromAdapter callback;
    private DatabaseReference inventoryRef;
    private ChildEventListener childEventListener;

    public RecycleAdapter(GetDataFromAdapter callback) {
        this.callback = callback;
        inventories = new ArrayList<>();
        inventoryRef = FirebaseDatabase.getInstance().getReference().child("inventory");

        inventoryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                inventory.setKey(dataSnapshot.getKey());
                inventories.add(0, inventory);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Inventory inventory = dataSnapshot.getValue(Inventory.class);

                for (Inventory item : inventories) {
                    if (item.equals(key)) {
                        item.setValues(inventory);
                        break;
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (Inventory item : inventories) {
                    if (item.equals(key)) {
                        inventories.remove(item);
                        break;
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


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

    public void addDataSnapshot(DatabaseReference databaseReference) {
        inventoryRef = databaseReference;
    }

    private void removeData(Inventory inventory, int position) {
        inventoryRef.child(inventory.getKey()).removeValue();
        inventories.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, inventories.size());
    }

    private void updateDailog(final Context context, final Inventory inventory) {

        dialog = new Dialog(context);
        dialog.setTitle("Update Product");
        dialog.setContentView(R.layout.update_dialog);

        final EditText product = (EditText) dialog.findViewById(R.id.proName);
        final EditText qty = (EditText) dialog.findViewById(R.id.proQty);
        final EditText price = (EditText) dialog.findViewById(R.id.proPrice);
        Button button = (Button) dialog.findViewById(R.id.update);

        product.setText(inventory.getItem());
        qty.setText(inventory.getQuantity());
        price.setText(inventory.getPrice());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inventory inventory1 = new Inventory();
                inventory1.setItem(product.getText().toString());
                inventory1.setQuantity(qty.getText().toString());
                inventory1.setPrice(price.getText().toString());
                inventoryRef.child(inventory.getKey()).setValue(inventory1);
                notifyDataSetChanged();
                Toast.makeText(context, "Data Updated", Toast.LENGTH_LONG).show();

            }
        });
        dialog.show();

    }

    public interface GetDataFromAdapter {
        void onCurrentMovie(Inventory currentData);
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

}
