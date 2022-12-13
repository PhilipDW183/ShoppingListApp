package com.example.myshoppinglist;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.Utils.DatabaseHandler;

public class AddNewItemFloat extends DialogFragment {

    public static final String TAG = "ActionDialog";

    private EditText newItemText;
    private Button newItemSaveButton;
    private Button newItemCancelButton;
    private DatabaseHandler db;

    public static AddNewItem newInstance(){
        return new AddNewItem();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);
        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        dismiss();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newItemText = getView().findViewById(R.id.newItemText);
        newItemSaveButton = getView().findViewById(R.id.newItemSave);
        newItemCancelButton = getView().findViewById(R.id.newItemCancel);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newItemCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        // changes whether updating a task or not
        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String item = bundle.getString("item");
            newItemText.setText(item);
            if(item.length()>0){
                newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            }
        }
        newItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals("")) {
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newItemSaveButton.setEnabled(true);
                    newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String text = newItemText.getText().toString();
                if(finalIsUpdate){
                    db.updateItem(bundle.getInt("id"), text);
                }
                else{
                    ShoppingModel item = new ShoppingModel();
                    item.setItem(text);
                    item.setStatus(0);
                    db.insertItem(item);
                }
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }

}
