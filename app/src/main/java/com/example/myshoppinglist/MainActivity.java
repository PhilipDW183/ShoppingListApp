package com.example.myshoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myshoppinglist.Adapter.ShoppingAdapter;
import com.example.myshoppinglist.Model.ShoppingModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsRecyclerView;
    private ShoppingAdapter itemsAdapter;

    private List<ShoppingModel> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide the top most bar
        getSupportActionBar().hide();

        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemsAdapter = new ShoppingAdapter(this);
        itemsRecyclerView.setAdapter(itemsAdapter);
    }
}