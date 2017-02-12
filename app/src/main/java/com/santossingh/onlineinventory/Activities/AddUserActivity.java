package com.santossingh.onlineinventory.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Models.Sellers;
import com.santossingh.onlineinventory.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.addUser)
    Button add;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userMobile)
    EditText mobile;
    @BindView(R.id.userPassword)
    EditText pwd;

    private DatabaseReference sellersReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        sellersReference = FirebaseDatabase.getInstance().getReference().child("sellers");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {

        if (userName.getText().toString() == "" || mobile.getText().toString() == "" || pwd.getText().toString() == "") {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_LONG).show();
        } else {
            Sellers add = new Sellers(
                    userName.getText().toString(),
                    pwd.getText().toString(),
                    mobile.getText().toString());
            sellersReference.push().setValue(add);
            clearUI();
            Toast.makeText(getApplicationContext(), "Added Record", Toast.LENGTH_LONG).show();
        }
    }

    private void updateProduct() {
    }

    private void clearUI() {
        userName.setText("");
        mobile.setText("");
        pwd.setText("");
    }
}
