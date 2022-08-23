package com.example.myshoppinglist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoppinglist.MainActivity;
import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.R;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {


    private List<ShoppingModel> ShoppingList;
    private MainActivity activity;

    public ShoppingAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);
    };

    public void onBindViewHolder(ViewHolder holder, int position){
        ShoppingModel item = ShoppingList.get(position);
        holder.item.setText(item.getItem());
        holder.item.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount(){
        return ShoppingList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;
        Button itemDelete;

        ViewHolder(View view){
            super(view);
            item = view.findViewById(R.id.itemCheckbox);
            itemDelete = view.findViewById(R.id.itemDeleteButton);
        }
    }

}
