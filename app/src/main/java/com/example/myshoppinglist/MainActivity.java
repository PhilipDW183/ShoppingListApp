package com.example.myshoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.myshoppinglist.Adapter.ShoppingAdapter;
import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView itemsRecyclerView;
    private ShoppingAdapter itemsAdapter;
    private FloatingActionButton fab;

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

        fab = findViewById(R.id.addItem);

        itemList = db.getAllItems();
        Collections.reverse(itemList);
//        Collections.sort(itemList, (ShoppingModel a1, ShoppingModel a2) -> a1.getStatus()-a2.getStatus());
        itemsAdapter.setItems(itemList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewItem.newInstance().show(getSupportFragmentManager(), AddNewItem.TAG);
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

}