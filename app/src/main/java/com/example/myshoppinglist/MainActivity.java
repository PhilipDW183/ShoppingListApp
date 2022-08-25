package com.example.myshoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.myshoppinglist.Adapter.ShoppingAdapter;
import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView itemsRecyclerView;
    private ShoppingAdapter itemsAdapter;

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

        itemsAdapter = new ShoppingAdapter(this);
        itemsRecyclerView.setAdapter(itemsAdapter);

        ShoppingModel item = new ShoppingModel();
        item.setItem("This is a test item");
        item.setStatus(0);
        item.setId(1);

        itemList.add(item);
        itemList.add(item);
        itemList.add(item);
        itemList.add(item);
        itemList.add(item);

        itemsAdapter.setItems(itemList);

    }


    @Override
    public void handleDialogClose(DialogInterface dialog){
        itemList = db.getAllItems();
        // The recently added item will be at the top
        Collections.reverse(itemList);
        itemsAdapter.setItems(itemList);
        // This will update the recycler view
        itemsAdapter.notifyDataSetChanged();
    }

}