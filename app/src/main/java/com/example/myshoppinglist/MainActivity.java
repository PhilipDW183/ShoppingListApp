package com.example.myshoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myshoppinglist.Adapter.ShoppingAdapter;
import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView itemsRecyclerView;
    private ShoppingAdapter itemsAdapter;
    private FloatingActionButton addItemButton;
    private AppCompatImageButton cleanItemsButton;
    private FloatingActionButton helpButton;
    private ShoppingModel item;

    private List<ShoppingModel> itemList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide the top most bar
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        itemList = new ArrayList<>();

        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemsAdapter = new ShoppingAdapter(db, this);
        itemsRecyclerView.setAdapter(itemsAdapter);

        addItemButton = findViewById(R.id.addItem);
        cleanItemsButton = findViewById(R.id.clearTickedItems);
        helpButton = findViewById(R.id.onboardQuestion);

        itemList = db.getAllItems();
        Collections.reverse(itemList);
//        Collections.sort(itemList, (ShoppingModel a1, ShoppingModel a2) -> a1.getStatus()-a2.getStatus());
        itemsAdapter.setItems(itemList);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewItem.newInstance().show(getSupportFragmentManager(), AddNewItem.TAG);
            }
        });

        cleanItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearClickedItems();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHelp();
            }
        });
    }


    @Override
    public void handleDialogClose(DialogInterface dialog){
        itemList = db.getAllItems();
        // The recently added item will be at the top
        Collections.reverse(itemList);
//        Collections.sort(itemList, (ShoppingModel a1, ShoppingModel a2) -> a1.getStatus()-a2.getStatus());
        itemsAdapter.setItems(itemList);
        // This will update the recycler view
        itemsAdapter.notifyDataSetChanged();
    }


    public void clearClickedItems() {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.create();
        ad.setCancelable(true);
        ad.setTitle("Remove ticked items");
        ad.setMessage("Are you sure you want to remove all ticked items");

        ad.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemList = db.getAllItems();
                        for (int j = 0; j < itemList.size(); j ++) {
                            item = itemList.get(j);
                            if (item.getStatus() == 1){
                                db.deleteItem(item.getId());
                            }
                        }
                        handleDialogClose(dialogInterface);
                    }
                });

        ad.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        ad.show();

    }

    private void openHelp() {
        Intent intent = new Intent(MainActivity.this, OnboardActivity.class);
        startActivity(intent);

        finish();
    }

}