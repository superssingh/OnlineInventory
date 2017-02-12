package com.santossingh.onlineinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.santossingh.onlineinventory.Adapter.FirebaseRecyclerAdatper.RecycleAdapter;
import com.santossingh.onlineinventory.Models.Inventory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity implements RecycleAdapter.GetDataFromAdapter {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecycleAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        recycleAdapter = new RecycleAdapter(ShowActivity.this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCurrentMovie(Inventory currentData) {
        Intent intent = new Intent(this, UpdateActivity.class)
                .putExtra("KEY", currentData.getKey())
                .putExtra("PRODUCT", currentData.getItem())
                .putExtra("QTY", currentData.getQuantity())
                .putExtra("PRICE", currentData.getPrice());
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

}