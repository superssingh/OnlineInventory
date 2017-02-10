package com.santossingh.onlineinventory.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santossingh.onlineinventory.Models.Inventory;
import com.santossingh.onlineinventory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshsingh on 09/02/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Inventory> inventoryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView product,qty,price;

        public MyViewHolder(View view) {
            super(view);
            product = (TextView) view.findViewById(R.id.R_product);
            qty= (TextView) view.findViewById(R.id.R_qty);
            price = (TextView) view.findViewById(R.id.R_price);
        }
    }

    public MyAdapter(List<Inventory> inventoryList){
        this.inventoryList=inventoryList;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        Inventory inventory = inventoryList.get(position);
        holder.product.setText(inventory.getItem());
        holder.qty.setText(inventory.getQuantity());
        holder.price.setText(inventory.getPrice());
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public void updateList(ArrayList<Inventory> inventoryList) {
        this.inventoryList=inventoryList;
    }



}