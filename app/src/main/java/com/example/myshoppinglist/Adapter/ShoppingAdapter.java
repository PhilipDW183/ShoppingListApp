package com.example.myshoppinglist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoppinglist.AddNewItem;
import com.example.myshoppinglist.MainActivity;
import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.R;
import com.example.myshoppinglist.Utils.DatabaseHandler;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {


    private List<ShoppingModel> shoppingList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ShoppingAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);
    };

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ShoppingModel item = shoppingList.get(position);
        holder.item.setText(item.getItem());
        holder.item.setChecked(toBoolean(item.getStatus()));
        holder.item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else{
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount(){
        return shoppingList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setItems(List<ShoppingModel> itemList) {
        this.shoppingList = itemList;
        // ensure that the recycler view is updated
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void editItem(int position){
        ShoppingModel item = shoppingList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("item", item.getItem());
        AddNewItem fragment = new AddNewItem();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewItem.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;
        ImageButton itemDelete;
        ImageButton itemEdit;

        ViewHolder(View view){
            super(view);
            item = view.findViewById(R.id.itemCheckbox);
            itemDelete = view.findViewById(R.id.itemDeleteButton);
            itemEdit = view.findViewById(R.id.itemEditButton);
        }
    }

}
