package com.santossingh.onlineinventory.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santossingh.onlineinventory.Models.Admins;
import com.santossingh.onlineinventory.R;
import com.santossingh.onlineinventory.ShowActivity;
import com.santossingh.onlineinventory.UserActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.admin_add_product) Button addProduct;
    @BindView(R.id.admin_add_user)
    Button add_user;
    @BindView(R.id.admin_update_user) Button updateUser;
    @BindView(R.id.admin_show) Button showProduct;
    @BindView(R.id.add_admin)
    Button addAdmin;
    @BindView(R.id.change_Password)
    Button changePassword;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference adminDatabase;
    List<Admins> adminsList;
    Admins adminsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        adminDatabase = firebaseDatabase.getReference().child("admin");

        adminDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Admins admins = dataSnapshot.getValue(Admins.class);
                admins.setKey(dataSnapshot.getKey());
                adminsData = admins;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Admins admins = dataSnapshot.getValue(Admins.class);
                for (Admins item : adminsList) {
                    if (item.equals(key)) {
                        item.setValues(admins);
                        break;
                    }
                }
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

        showProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

//        addAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addAdminDialogBox();
//            }
//        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAdminDialogBox(adminsData);
            }
        });
    }

    public void addAdminDialogBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        View view = getLayoutInflater().inflate(R.layout.add_admin, null);

        final EditText name = (EditText) view.findViewById(R.id.adminName);
        final EditText mobile = (EditText) view.findViewById(R.id.adminMobile);
        final EditText password = (EditText) view.findViewById(R.id.adminPassword);
        final Button add = (Button) view.findViewById(R.id.Add_Admin);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty() || !mobile.getText().toString().isEmpty() || !password.getText().toString().isEmpty()) {
                    Admins updates = new Admins(
                            name.getText().toString(),
                            mobile.getText().toString(),
                            password.getText().toString()
                    );
                    adminDatabase.push().setValue(updates);
                    Toast.makeText(AdminActivity.this, "Addmin Added.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Please fill required fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateAdminDialogBox(final Admins admins) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        View view = getLayoutInflater().inflate(R.layout.update_admin, null);

        final EditText name = (EditText) view.findViewById(R.id.adminName);
        final EditText mobile = (EditText) view.findViewById(R.id.adminMobile);
        final EditText password = (EditText) view.findViewById(R.id.adminPassword);
        final Button upadte = (Button) view.findViewById(R.id.update_Admin);

        final String key = admins.getKey();

        name.setText(admins.getName());
        mobile.setText(admins.getMobile());
        password.setText(admins.getPassword());
        upadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!name.getText().toString().isEmpty() || !mobile.getText().toString().isEmpty() || !password.getText().toString().isEmpty()) {

                    Admins updates = new Admins(
                            name.getText().toString(),
                            mobile.getText().toString(),
                            password.getText().toString()
                    );

                    adminDatabase.child(key).setValue(updates);
                    Toast.makeText(AdminActivity.this, "Password Changed.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Please fill required fields.", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
