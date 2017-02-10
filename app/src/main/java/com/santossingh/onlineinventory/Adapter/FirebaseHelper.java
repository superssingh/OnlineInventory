package com.santossingh.onlineinventory.Adapter;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.santossingh.onlineinventory.Models.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshsingh on 07/02/17.
 */

public class FirebaseHelper {

    DatabaseReference databaseReference;
    Boolean saved=null;
    List<Inventory> inventories=new ArrayList<Inventory>();

    public FirebaseHelper(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public Boolean save(Inventory inventory){
        if (inventory==null){
            saved=false;
        }else{
            try{
                databaseReference.child("inventory").push().setValue(inventory);
                saved=true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        inventories.clear();
        for (DataSnapshot ds: dataSnapshot.getChildren()){
            Inventory inventory=dataSnapshot.getValue(Inventory.class);
            inventories.add(inventory);
        }
    }

    public List<Inventory> retrieve(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                inventories.clear();
                Inventory inventory=dataSnapshot.getValue(Inventory.class);
                inventories.add(inventory);
                Log.i("Help",inventory.getItem());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                fetchData(dataSnapshot);
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
        return inventories;
    }
}
